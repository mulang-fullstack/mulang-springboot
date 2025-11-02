package yoonsome.mulang.api.student.dto;

import lombok.Data;
import yoonsome.mulang.domain.course.entity.Course;
import yoonsome.mulang.domain.review.entity.CourseReview;
import yoonsome.mulang.domain.user.entity.User;

@Data
public class ReviewRequest {
    private Long courseId;
    private Integer rating;
    private String content;

    // ⭐ 이 메서드가 있어야 합니다!
    public CourseReview toEntity(Long userId) {
        CourseReview review = new CourseReview();

        User user = new User();
        user.setId(userId);
        review.setStudent(user);

        Course course = new Course();
        course.setId(this.courseId);
        review.setCourse(course);

        review.setRating(this.rating);
        review.setContent(this.content);

        return review;
    }
}
