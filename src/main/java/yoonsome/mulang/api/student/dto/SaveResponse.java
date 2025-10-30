package yoonsome.mulang.api.student.dto;

import lombok.Builder;
import lombok.Data;
import yoonsome.mulang.domain.course.entity.Course;
import yoonsome.mulang.domain.coursefavorite.entity.CourseFavorite;
import yoonsome.mulang.domain.user.entity.User;

import java.time.LocalDateTime;

@Data
@Builder
public class SaveResponse {
    private Long id;
    private Course course;
    private User student;
    private LocalDateTime createdAt;
    private String thumbnailUrl;

}
