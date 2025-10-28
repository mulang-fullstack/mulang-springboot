package yoonsome.mulang.api.student.dto;

import lombok.Data;
import yoonsome.mulang.domain.course.entity.Course;
import yoonsome.mulang.domain.coursefavorite.entity.CourseFavorite;
import yoonsome.mulang.domain.user.entity.User;

import java.time.LocalDateTime;

@Data
public class SaveResponse {
    private Long id;
    private Course course;
    private User student;
    private LocalDateTime createdAt;
    // Save Entity -> DTO 변환 메서드
    public static SaveResponse from(CourseFavorite save) {
        SaveResponse response = new SaveResponse();
        response.setId(save.getId());
        response.setCourse(save.getCourse());
        response.setStudent(save.getStudent());
        response.setCreatedAt(save.getCreatedAt());
        return response;
    }
}
