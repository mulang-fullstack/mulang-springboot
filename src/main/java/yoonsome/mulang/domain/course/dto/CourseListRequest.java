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
    private LocalDateTime startDate= LocalDate.now()
            .withDayOfMonth(1)
            .atStartOfDay();;  // 가입 범위(시작)

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endDate = LocalDate.now()
            .atTime(23, 59, 59);
    private String sort;
    private int page;
    private int size;

}
