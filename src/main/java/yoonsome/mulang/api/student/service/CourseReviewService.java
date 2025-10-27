package yoonsome.mulang.api.student.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yoonsome.mulang.domain.course.entity.Course;
import yoonsome.mulang.domain.course.repository.CourseRepository;
import yoonsome.mulang.domain.review.entity.CourseReview;
import yoonsome.mulang.domain.review.repository.ReviewRepository;
import yoonsome.mulang.domain.user.entity.User;
import yoonsome.mulang.infra.security.CustomUserDetails;

import java.util.List;

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
    public CourseReview updateReview(User user, Long courseId, Integer rating, String content) {
        CourseReview review = reviewRepository.findByStudentIdAndCourseId(user.getId(), courseId)
                .orElseThrow(() -> new IllegalArgumentException("수정할 리뷰가 없습니다."));

        review.setRating(rating);
        review.setContent(content);

        return reviewRepository.save(review);

    }
    public boolean existReview(User user, Long CourseId){
        return reviewRepository.existsByStudentIdAndCourseId(user.getId(), CourseId);
    }

    public List<CourseReview> MyReview(User user, String sort) {
        if("오래된순".equals(sort)){
            return reviewRepository.findByStudentIdOrderByCreatedAtAsc(user.getId());
        }
        //사스가 제파센세 디폴트는 최신순
        return reviewRepository.findByStudentIdOrderByCreatedAtDesc(user.getId());

    }
}
