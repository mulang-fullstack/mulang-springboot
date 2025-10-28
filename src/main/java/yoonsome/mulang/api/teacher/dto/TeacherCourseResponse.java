package yoonsome.mulang.api.teacher.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeacherCourseResponse {

    private Long id;
    private String title;
    private String subtitle;
    private String status;
    private Integer price;
    private String language;
    private String category;
    private String thumbnail;
    private String content;
    private String htmlContent;
    private LocalDateTime createdAt;
    private Integer lectureCount;

    private List<TeacherLectureEditResponse> lectures;
}
