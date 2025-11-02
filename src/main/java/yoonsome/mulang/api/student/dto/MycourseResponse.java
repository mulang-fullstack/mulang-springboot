package yoonsome.mulang.api.student.dto;

import lombok.Builder;
import lombok.Data;
import yoonsome.mulang.domain.course.entity.Course;
import yoonsome.mulang.domain.enrollment.entity.Enrollment;
import yoonsome.mulang.domain.user.entity.User;

import java.time.LocalDateTime;

@Data
@Builder
public class MycourseResponse {
    private Long id;
    private User user;
    private Course course;
    private Integer progress;
    private LocalDateTime enrolledAt;
    private String thumbnailUrl;


}
