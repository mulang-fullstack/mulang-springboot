package yoonsome.mulang.api.ai.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;
import yoonsome.mulang.api.ai.dto.QuestionDTO;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GptService {

    private final OpenAiChatModel chatModel;

    /**
     * AI로 문제 생성
     */
    public List<QuestionDTO> generateQuestions(String lang, String level) {
        log.info("문제 생성 시작: lang={}, level={}", lang, level);

        String targetDesc = switch (lang.toUpperCase()) {
            case "ENGLISH" -> "영어";
            case "JAPANESE" -> "일본어";
            case "CHINESE" -> "중국어";
            default -> "외국어";
        };

        String levelDesc = switch (level.toUpperCase()) {
            case "A1" -> "가장 기초 수준으로, 짧은 문장을 이해할 수 있는 단계";
            case "A2" -> "기초 수준으로, 일상적인 문장과 어휘를 이해할 수 있는 단계";
            case "B1" -> "중급 초반 수준으로, 복합 문장과 시제를 구분할 수 있는 단계";
            case "B2" -> "중급 후반 수준으로, 문맥 추론과 자연스러운 표현을 이해할 수 있는 단계";
            default -> "학습자 수준에 맞는 단계";
        };

        String promptText = """
            당신은 한국인을 대상으로 외국어 이해력을 평가하는 언어 시험 문제 제작 전문가입니다.
            평가 대상 언어: %s
            난이도: %s (%s)

            [출제 지침]
            1. 모든 문제와 선택지는 반드시 **한국어로 작성**해야 합니다.
            2. 문제에는 반드시 **하나 이상의 외국어 예시 문장**이 포함되어야 합니다.
               - 예시 문장은 반드시 %s로 작성하세요.
               - 문장은 따옴표("...")로 감싸서 명확히 표시해야 합니다.
               예: "다음 %s 문장의 뜻으로 가장 알맞은 것은? '私は学生です。'"
            3. 문제의 지문과 보기 설명은 모두 한국어로 작성하되, 외국어 문장은 항상 포함되어야 합니다.
            4. 각 문제는 외국어 문장의 의미, 문법, 혹은 어휘 이해를 평가해야 합니다.
            5. 문제 유형은 아래 3가지 중 2가지 이상을 섞어 구성하세요.
               - [번역형]: 외국어 문장의 올바른 의미를 고르기
               - [문장완성형]: 문맥상 알맞은 단어를 고르기
               - [의미판단형]: 두 문장의 의미가 같은지 묻기
            6. 각 문항은 다음 조건을 따라야 합니다.
               - 문항 수: 10개
               - 보기 수: 4개
               - 정답은 정확히 하나만 존재해야 합니다.
            7. 문장은 자연스럽고 실제 생활에서 자주 쓰이는 예문을 사용하세요.
            8. 'pencil', 'apple'처럼 단어 맞추기 수준의 초저품질 문제는 절대 출제하지 마세요.
            9. 각 문제의 난이도는 %s 수준으로 설정하세요.
            10. 주어진 문장의 의미와 일치하지 **않는** 선택지를 하나 포함하도록 하세요.
                반드시 문제문에 "일치하지 않는 것은?"이 포함되어 있으면 정답은 오답 선택지여야 합니다.
                반대로 "일치하는 것은?"이면 정답은 정확히 의미가 같은 문장입니다.
                문제문과 정답이 논리적으로 일관되도록 하세요.
            11. 반드시 문제는 정확히 10개만 생성하세요.
                출력 JSON 배열의 원소 개수는 반드시 10개여야 하며, 10개보다 많거나 적으면 안 됩니다.
            12. [출제 검증 규칙]
                - 생성된 문제와 정답의 논리 일관성을 반드시 검증하세요.
                - 외국어 문장에 등장하는 단어, 의미, 문법적 관계가 정답 선택지와 정확히 일치해야 합니다.
                - 정답이 실제로 외국어 문장의 의미를 정확히 반영하는지 스스로 다시 확인한 뒤 출력하세요.
                - "중국어 문장에서 '英语(영어)'가 있으면 정답은 반드시 영어와 관련된 선택지여야 합니다."
                - 의미 해석 오류, 단어 오역, 언어 불일치가 있으면 해당 문항은 생성하지 마세요.
                - 각 문제는 내부적으로 검증된 문항만 JSON에 포함되도록 하세요.
            
            [출력 형식]
            JSON 배열 형태로만 출력하세요. 다른 설명이나 텍스트 없이 순수 JSON만 출력하세요.
            [
              {
                "question": "문제 지문 (한국어로 작성)",
                "choices": ["선택지1", "선택지2", "선택지3", "선택지4"],
                "answer": "정답 (선택지 중 하나)",
                "difficulty": "%s",
                "targetLang": "%s"
              }
            ]
            """.formatted(targetDesc, level, levelDesc, targetDesc, targetDesc, level, level, lang);

        try {
            // Prompt 생성 및 API 호출
            Prompt prompt = new Prompt(promptText);
            ChatResponse response = chatModel.call(prompt);
            String json = response.getResult().getOutput().getContent();

            log.debug("GPT 응답: {}", json);

            // JSON 파싱
            ObjectMapper mapper = new ObjectMapper();

            // JSON 정제 (```json ``` 제거)
            json = cleanJsonResponse(json);

            List<QuestionDTO> parsed = mapper.readValue(json, new TypeReference<List<QuestionDTO>>() {});

            // 유효성 검증 및 필터링
            List<QuestionDTO> validQuestions = parsed.stream()
                    .filter(q -> q.getQuestion() != null && !q.getQuestion().isBlank())
                    .filter(q -> q.getChoices() != null && q.getChoices().size() == 4)
                    .filter(q -> q.getAnswer() != null && q.getChoices().contains(q.getAnswer()))
                    .map(q -> {
                        q.setDifficulty(level);
                        q.setTargetLang(lang);
                        return q;
                    })
                    .collect(Collectors.toList());

            log.info("문제 생성 완료: {}개", validQuestions.size());

            if (validQuestions.size() < 10) {
                log.warn("생성된 문제가 10개 미만입니다: {}개", validQuestions.size());
            }

            return validQuestions;

        } catch (Exception e) {
            log.error("GPT 응답 파싱 실패", e);
            throw new RuntimeException("문제 생성에 실패했습니다: " + e.getMessage(), e);
        }
    }

    /**
     * JSON 응답 정제 (마크다운 코드 블록 제거)
     */
    private String cleanJsonResponse(String json) {
        if (json == null) {
            return "[]";
        }

        // ```json ... ``` 제거
        json = json.replaceAll("```json\\s*", "");
        json = json.replaceAll("```\\s*$", "");
        json = json.trim();

        return json;
    }
}