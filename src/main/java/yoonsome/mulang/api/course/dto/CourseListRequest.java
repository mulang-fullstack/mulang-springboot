package yoonsome.mulang.api.course.dto;

import lombok.Data;
import yoonsome.mulang.domain.course.entity.StatusType;

@Data
public class CourseListRequest {
    private Long languageId;
    private Long categoryId;
    private String keyword;
    private String sort;
    private StatusType status = StatusType.PUBLIC;
    private int page = 0;
    private int size = 4;
}
