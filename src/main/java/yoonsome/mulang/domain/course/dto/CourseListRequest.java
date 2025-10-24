package yoonsome.mulang.domain.course.dto;

import lombok.Data;
import yoonsome.mulang.domain.course.entity.StatusType;
import java.time.LocalDate;

@Data
public class CourseListRequest {
    private Long languageId;
    private Long categoryId;
    private String keyword;
    private StatusType status;
    private LocalDate startDate;
    private LocalDate endDate;
    private String sort;
    private int page;
    private int size;
}
