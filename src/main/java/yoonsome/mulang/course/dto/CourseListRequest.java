package yoonsome.mulang.course.dto;

import lombok.Data;

@Data
public class CourseListRequest {
    private Long languageId =1L;
    private Long categoryId = 1L;
    //private String keyword;
    //private String sort;
    private int page = 0;
    private int size = 4;
}
