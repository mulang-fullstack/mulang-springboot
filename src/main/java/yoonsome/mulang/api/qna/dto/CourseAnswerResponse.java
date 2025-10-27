package yoonsome.mulang.api.qna.dto;

import lombok.Builder;
import lombok.Data;
import java.sql.Timestamp;

@Data
@Builder
public class CourseAnswerResponse {

    private Long id;
    private String content;
    private String teacherName;
    private Timestamp createdAt;

}
