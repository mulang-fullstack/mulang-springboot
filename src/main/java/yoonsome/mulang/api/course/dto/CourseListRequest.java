package yoonsome.mulang.api.course.dto;

import lombok.Data;

@Data
public class CourseListRequest {
    private Long languageId;
    private Long categoryId;
    private String keyword;
    private String sort;
    private int page = 0;
    private int size = 4;
}
