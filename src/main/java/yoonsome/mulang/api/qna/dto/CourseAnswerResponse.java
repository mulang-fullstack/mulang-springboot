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
    private boolean deletable; // 삭제 가능 여부
    private boolean editable; // 수정 가능 여부

}
