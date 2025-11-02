package yoonsome.mulang.api.ai.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yoonsome.mulang.domain.ai.entity.LevelTest;

import java.util.List;

/**
 * 테스트 결과 응답 DTO
 */
@Getter
@Setter
@NoArgsConstructor
public class TestResultResponse {
    private Long testId;
    private String language;
    private String languageKorean;
    private String testLevel;
    private Integer totalQuestions;
    private Integer correctAnswers;
    private Integer score;
    private String resultLevel;         // BASIC, GRAMMAR, CONVERSATION
    private String resultLevelKorean;   // 기초, 문법, 회화
    private String resultDescription;
    private String feedback;
    private List<QuestionResult> questionResults;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QuestionResult {
        private Integer questionNumber;
        private String question;
        private String userAnswer;
        private String correctAnswer;
        private Boolean isCorrect;
    }

    /**
     * LevelTest Entity로부터 생성
     */
    public static TestResultResponse from(LevelTest levelTest, List<QuestionResult> questionResults) {
        TestResultResponse response = new TestResultResponse();
        response.setTestId(levelTest.getId());
        response.setLanguage(levelTest.getLanguage().name());
        response.setLanguageKorean(levelTest.getLanguage().getKorean());
        response.setTestLevel(levelTest.getTestLevel().name());
        response.setTotalQuestions(levelTest.getTotalQuestions());
        response.setCorrectAnswers(levelTest.getCorrectAnswers());
        response.setScore(levelTest.getScore());
        response.setResultLevel(levelTest.getResultLevel().name());
        response.setResultLevelKorean(levelTest.getResultLevel().getKorean());
        response.setResultDescription(levelTest.getResultLevel().getDescription());
        response.setFeedback(levelTest.getFeedback());
        response.setQuestionResults(questionResults);
        return response;
    }
}