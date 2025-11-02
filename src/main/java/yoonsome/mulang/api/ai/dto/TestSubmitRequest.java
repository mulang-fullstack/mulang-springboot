package yoonsome.mulang.api.ai.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * 답안 제출 요청 DTO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestSubmitRequest {
    private Long testId;
    private List<UserAnswer> answers;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserAnswer {
        private Integer questionNumber;  // 문제 번호 (1~10)
        private String answer;           // 사용자 답변
    }
}