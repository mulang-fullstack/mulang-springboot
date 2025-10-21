package yoonsome.mulang.api.course.dto;

import lombok.Data;

@Data
public class CourseListRequest {
    private Long languageId=1L;
    private Long categoryId;
    private String keyword;
    private String sort = "rating";
    private int page = 0;
    private int size = 4;
}
