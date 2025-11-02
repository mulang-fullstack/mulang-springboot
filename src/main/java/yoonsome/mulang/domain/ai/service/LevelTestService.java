package yoonsome.mulang.domain.ai.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yoonsome.mulang.api.ai.dto.*;
import yoonsome.mulang.api.ai.service.GptService;
import yoonsome.mulang.domain.ai.entity.LevelTest;
import yoonsome.mulang.domain.ai.repository.LevelTestRepository;
import yoonsome.mulang.domain.user.entity.User;
import yoonsome.mulang.domain.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class LevelTestService {

    private final GptService gptService;
    private final LevelTestRepository levelTestRepository;
    private final UserRepository userRepository;

    // 테스트 제한 기간 (일)
    private static final int TEST_RESTRICTION_DAYS = 30;

    // 세션에 문제 저장 (실제로는 Redis 권장)
    private final Map<Long, List<QuestionDTO>> testQuestionsCache = new HashMap<>();

    /**
     * 테스트 시작 가능 여부 확인
     */
    public TestAvailabilityResponse checkTestAvailability(Long userId, String language) {
        // 해당 언어의 가장 최근 완료된 테스트 조회
        LevelTest.Language lang = LevelTest.Language.valueOf(language.toUpperCase());

        LevelTest lastTest = levelTestRepository
                .findTopByUserIdAndLanguageAndCompletedAtIsNotNullOrderByCompletedAtDesc(userId, lang)
                .orElse(null);

        if (lastTest == null) {
            // 처음 테스트하는 경우
            return TestAvailabilityResponse.builder()
                    .available(true)
                    .message("테스트를 시작할 수 있습니다.")
                    .build();
        }

        LocalDateTime lastTestDate = lastTest.getCompletedAt();
        LocalDateTime now = LocalDateTime.now();
        long daysSinceLastTest = ChronoUnit.DAYS.between(lastTestDate, now);

        if (daysSinceLastTest < TEST_RESTRICTION_DAYS) {
            // 아직 30일이 안 지남
            long daysRemaining = TEST_RESTRICTION_DAYS - daysSinceLastTest;

            return TestAvailabilityResponse.builder()
                    .available(false)
                    .message(String.format("테스트는 한 달에 한 번만 가능합니다."))
                    .lastTestDate(lastTestDate)
                    .daysRemaining(daysRemaining)
                    .nextAvailableDate(lastTestDate.plusDays(TEST_RESTRICTION_DAYS))
                    .build();
        }

        // 30일 이상 지남
        return TestAvailabilityResponse.builder()
                .available(true)
                .message("테스트를 시작할 수 있습니다.")
                .lastTestDate(lastTestDate)
                .build();
    }

    /**
     * 테스트 시작 - 문제 생성 및 저장
     */
    @Transactional
    public TestStartResponse startTest(Long userId, TestStartRequest request) {
        log.info("테스트 시작: userId={}, language={}", userId, request.getLanguage());

        // 1. 테스트 가능 여부 확인
        TestAvailabilityResponse availability = checkTestAvailability(userId, request.getLanguage());
        if (!availability.isAvailable()) {
            throw new IllegalStateException(
                    String.format("테스트는 %d일 후에 가능합니다. (남은 기간: %d일)",
                            availability.getDaysRemaining(),
                            availability.getDaysRemaining())
            );
        }

        // 2. 사용자 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        // 3. 언어 및 레벨 검증
        LevelTest.Language language = LevelTest.Language.valueOf(request.getLanguage().toUpperCase());
        String level = request.getLevel() != null ? request.getLevel().toUpperCase() : "A1";
        LevelTest.TestLevel testLevel = LevelTest.TestLevel.valueOf(level);

        // 4. AI로 문제 생성
        List<QuestionDTO> questions = gptService.generateQuestions(
                language.name(),
                testLevel.name()
        );

        if (questions == null || questions.size() != 10) {
            throw new RuntimeException("문제 생성에 실패했습니다. 다시 시도해주세요.");
        }

        // 5. LevelTest 엔티티 생성
        LevelTest levelTest = new LevelTest();
        levelTest.setUser(user);
        levelTest.setLanguage(language);
        levelTest.setTestLevel(testLevel);
        levelTest.setTotalQuestions(questions.size());
        levelTest.setStartedAt(LocalDateTime.now());

        levelTest = levelTestRepository.save(levelTest);

        // 6. 문제 캐싱 (testId를 키로 사용)
        testQuestionsCache.put(levelTest.getId(), questions);

        log.info("테스트 생성 완료: testId={}", levelTest.getId());

        return new TestStartResponse(
                levelTest.getId(),
                language.name(),
                testLevel.name(),
                questions,
                questions.size()
        );
    }

    /**
     * 답안 제출 및 채점
     */
    @Transactional
    public TestResultResponse submitTest(TestSubmitRequest request) {
        log.info("답안 제출: testId={}", request.getTestId());

        // 1. 테스트 조회
        LevelTest levelTest = levelTestRepository.findByIdAndCompletedAtIsNull(request.getTestId())
                .orElseThrow(() -> new RuntimeException("진행 중인 테스트를 찾을 수 없습니다."));

        // 2. 캐시에서 문제 가져오기
        List<QuestionDTO> questions = testQuestionsCache.get(request.getTestId());
        if (questions == null || questions.isEmpty()) {
            throw new RuntimeException("테스트 문제를 찾을 수 없습니다. 다시 시도해주세요.");
        }

        // 3. 채점
        int correctCount = 0;
        List<TestResultResponse.QuestionResult> questionResults = new ArrayList<>();

        for (int i = 0; i < questions.size(); i++) {
            QuestionDTO question = questions.get(i);
            String userAnswer = getUserAnswer(request.getAnswers(), i + 1);
            boolean isCorrect = question.getAnswer().equals(userAnswer);

            if (isCorrect) {
                correctCount++;
            }

            questionResults.add(new TestResultResponse.QuestionResult(
                    i + 1,
                    question.getQuestion(),
                    userAnswer,
                    question.getAnswer(),
                    isCorrect
            ));
        }

        // 4. 결과 저장
        levelTest.setCorrectAnswers(correctCount);
        levelTest.calculateScore();
        levelTest.determineResultLevel();
        levelTest.setCompletedAt(LocalDateTime.now());

        // 5. AI 피드백 생성
        String feedback = generateFeedback(levelTest);
        levelTest.setFeedback(feedback);

        levelTestRepository.save(levelTest);

        // 6. 캐시 삭제
        testQuestionsCache.remove(request.getTestId());

        log.info("채점 완료: testId={}, score={}, resultLevel={}",
                levelTest.getId(), levelTest.getScore(), levelTest.getResultLevel());

        return TestResultResponse.from(levelTest, questionResults);
    }

    /**
     * 사용자 답변 찾기
     */
    private String getUserAnswer(List<TestSubmitRequest.UserAnswer> answers, int questionNumber) {
        return answers.stream()
                .filter(a -> a.getQuestionNumber() == questionNumber)
                .findFirst()
                .map(TestSubmitRequest.UserAnswer::getAnswer)
                .orElse("");
    }

    /**
     * AI 피드백 생성
     */
    private String generateFeedback(LevelTest levelTest) {
        String languageKorean = levelTest.getLanguage().getKorean();
        int score = levelTest.getScore();
        String resultLevel = levelTest.getResultLevel().getKorean();

        StringBuilder feedback = new StringBuilder();
        feedback.append(String.format("%s 레벨 테스트 결과, %d점으로 '%s' 수준으로 평가되었습니다.\n\n",
                languageKorean, score, resultLevel));

        switch (levelTest.getResultLevel()) {
            case BASIC:
                feedback.append("▪ 기본 어휘와 간단한 문장 구조를 학습하는 것을 추천드립니다.\n");
                feedback.append("▪ 기초 문법 강좌와 일상 회화 표현을 중점적으로 학습하세요.\n");
                feedback.append("▪ 단어 암기와 기본 문장 패턴 연습이 필요합니다.");
                break;
            case GRAMMAR:
                feedback.append("▪ 문법 구조를 활용한 문장 만들기 연습이 필요합니다.\n");
                feedback.append("▪ 다양한 시제와 문장 형식을 학습하세요.\n");
                feedback.append("▪ 독해와 듣기 연습을 병행하면 더욱 효과적입니다.");
                break;
            case CONVERSATION:
                feedback.append("▪ 자연스러운 대화 표현과 관용구를 학습하세요.\n");
                feedback.append("▪ 실전 회화 연습과 원어민 콘텐츠 활용을 추천드립니다.\n");
                feedback.append("▪ 고급 문법과 다양한 주제의 글쓰기 연습이 도움이 됩니다.");
                break;
        }

        return feedback.toString();
    }

    /**
     * 사용자별 테스트 이력 조회
     */
    @Transactional(readOnly = true)
    public List<LevelTest> getUserTestHistory(Long userId) {
        return levelTestRepository.findByUserIdAndCompletedAtIsNotNullOrderByCompletedAtDesc(userId);
    }

    /**
     * 특정 테스트 결과 조회
     */
    @Transactional(readOnly = true)
    public LevelTest getTestResult(Long testId) {
        return levelTestRepository.findById(testId)
                .orElseThrow(() -> new RuntimeException("테스트 결과를 찾을 수 없습니다."));
    }

    /**
     * 지원 언어 목록
     */
    public List<LanguageOption> getAvailableLanguages() {
        List<LanguageOption> languages = new ArrayList<>();
        languages.add(new LanguageOption("ENGLISH", "영어", "English Level Test"));
        languages.add(new LanguageOption("JAPANESE", "일본어", "Japanese Level Test"));
        languages.add(new LanguageOption("CHINESE", "중국어", "Chinese Level Test"));
        return languages;
    }
}