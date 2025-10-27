package yoonsome.mulang.api.qna.dto;

import lombok.Builder;
import lombok.Data;



@Data
@Builder
public class CourseQuestionRequest {

    private Long courseId;     // 질문 대상 강좌 ID
    private String title;      // 제목
    private String content;    // 내용
}
