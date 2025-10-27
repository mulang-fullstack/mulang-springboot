package yoonsome.mulang.domain.course.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import yoonsome.mulang.domain.course.entity.StatusType;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CourseListRequest {
    private Long languageId;
    private Long categoryId;
    private String keyword;
    private StatusType status;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endDate;
    private int page = 0;
    private int size;
    private String sortBy;
    private String sortDirection = "DESC";
}
