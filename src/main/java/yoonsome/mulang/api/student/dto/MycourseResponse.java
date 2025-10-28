package yoonsome.mulang.api.student.dto;

import lombok.Data;
import yoonsome.mulang.domain.course.entity.Course;
import yoonsome.mulang.domain.enrollment.entity.Enrollment;
import yoonsome.mulang.domain.user.entity.User;

import java.time.LocalDateTime;

@Data
public class MycourseResponse {
    private Long id;
    private User user;
    private Course course;
    private Integer progress;
    private LocalDateTime enrolledAt;

    // Enrollment -> DTO 변환 메서드
    public static MycourseResponse from(Enrollment enrollment) {
        MycourseResponse response = new MycourseResponse();
        response.setId(enrollment.getId());
        response.setUser(enrollment.getUser());
        response.setCourse(enrollment.getCourse());
        response.setProgress(enrollment.getProgress());
        response.setEnrolledAt(enrollment.getEnrolledAt());
        return response;
    }
}
