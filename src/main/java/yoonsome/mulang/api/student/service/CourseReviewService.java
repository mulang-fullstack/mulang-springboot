package yoonsome.mulang.api.student.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import yoonsome.mulang.domain.course.entity.Course;
import yoonsome.mulang.domain.course.repository.CourseRepository;
import yoonsome.mulang.domain.review.entity.CourseReview;
import yoonsome.mulang.domain.review.repository.ReviewRepository;
import yoonsome.mulang.domain.user.entity.User;
import yoonsome.mulang.infra.security.CustomUserDetails;

@Service
@RequiredArgsConstructor
@Transactional
public class CourseReviewService {

    private final ReviewRepository reviewRepository;
    private final CourseRepository courseRepository;

    public CourseReview createReview(User user, Long courseId, Integer rating, String content) {



        //강좌조회
        Course course = courseRepository.findById(courseId).orElse(null);

        CourseReview courseReview = new CourseReview();
        courseReview.setCourse(course);
        courseReview.setStudent(user);
        courseReview.setRating(rating);
        courseReview.setContent(content);

        return reviewRepository.save(courseReview);


    }
}
