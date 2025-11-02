package yoonsome.mulang.api.qna.dto;

import lombok.Builder;
import lombok.Data;
import java.sql.Timestamp;
import java.util.List;


@Data
@Builder
public class CourseQuestionResponse {

    private Long id;
    private String title;
    private String content;
    private String writerName;
    private Timestamp createdAt;
    private List<CourseAnswerResponse> answers;

    private boolean deletable; // 삭제 가능 여부
    private boolean editable; // 수정 가능 여부

}
