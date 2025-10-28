package yoonsome.mulang.api.student.dto;

import lombok.Data;
import yoonsome.mulang.domain.course.entity.Course;
import yoonsome.mulang.domain.qna.entity.CourseQuestion;
import yoonsome.mulang.domain.review.entity.CourseReview;
import yoonsome.mulang.domain.user.entity.User;

import java.time.LocalDateTime;

@Data
public class ReviewResponse {
    private Long id;
    private Course course;
    private User student;
    private Integer rating;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ReviewResponse from(CourseReview review) {
        ReviewResponse response = new ReviewResponse();
        response.setId(review.getId());
        response.setCourse(review.getCourse());
        response.setStudent(review.getStudent());
        response.setRating(review.getRating());
        response.setContent(review.getContent());
        response.setCreatedAt(review.getCreatedAt());
        response.setUpdatedAt(review.getUpdatedAt());
        return response;


    }
}
