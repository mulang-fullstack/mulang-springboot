package yoonsome.mulang.api.qna.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CourseAnswerRequest {

    private Long questionId;   // 질문 ID
    private String content;    // 답변 내용
}
