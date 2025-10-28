package yoonsome.mulang.domain.review.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import yoonsome.mulang.domain.course.entity.Course;
import yoonsome.mulang.domain.course.repository.CourseRepository;
import yoonsome.mulang.domain.review.entity.CourseReview;
import yoonsome.mulang.domain.review.repository.ReviewRepository;

@Transactional
@RequiredArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private final ReviewRepository reviewRepository;
    private final CourseRepository courseRepository;

    @Override
    public Page<CourseReview> getReviewsByCourseId(Long courseId, Pageable pageable) {
        updateCourse(courseId);
        return reviewRepository.findByCourseId(courseId, pageable);
    }

    @Override
    public void saveReview(CourseReview review) {
        reviewRepository.save(review);
        updateCourse(review.getCourse().getId());
    }

    @Override
    public void deleteReview(Long reviewId) {
        CourseReview review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰 없음"));
        Long courseId = review.getCourse().getId();
        reviewRepository.delete(review);
        updateCourse(courseId);
    }

    /* 리뷰 등록 삭제 시 course 컬럼 내 평균 별점, 리뷰 수 update*/
    private void updateCourse(Long courseId) {
        Double avg = getAverageRatingByCourseId(courseId);
        Integer count = countReviewByCourseId(courseId);
        Course course = courseRepository.findById(courseId).get();
        course.setAverageRating(avg);
        course.setReviewCount(count);
        courseRepository.save(course);
    }

    @Override
    public double getAverageRatingByCourseId(Long courseId) {
        Double avg = reviewRepository.findRoundedAverageRatingByCourseId(courseId);
        double averageRating = (avg != null) ? avg : 0.0;
        return averageRating;
    }

    @Override
    public int countReviewByCourseId(Long courseId) {
        Integer count = reviewRepository.countReviewByCourseId(courseId);
        int reviewCount = (count != null) ? count : 0;
        return reviewCount;
    }
    @Override
    public boolean existReview(Long userId, Long courseId) {
        return reviewRepository.existsByStudentIdAndCourseId(userId, courseId);
    }
}
