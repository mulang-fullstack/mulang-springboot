package yoonsome.mulang.domain.review.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import yoonsome.mulang.domain.course.entity.Course;
import yoonsome.mulang.domain.course.repository.CourseRepository;
import yoonsome.mulang.domain.review.entity.CourseReview;
import yoonsome.mulang.domain.review.repository.ReviewRepository;

import java.time.LocalDateTime;

@Transactional
@RequiredArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private final ReviewRepository reviewRepository;
    private final CourseRepository courseRepository;

    @Override
    public Page<CourseReview> getReviewsByCourseId(Long courseId, Pageable pageable) {
        return reviewRepository.findByCourseId(courseId, pageable);
    }

    @Override
    public void saveReview(CourseReview review) {
        try {
            reviewRepository.save(review);
            updateCourse(review.getCourse().getId());
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("이미 해당 강의에 리뷰를 작성하셨습니다.");
        }
    }

    @Override
    public void editReview(CourseReview review) {
        CourseReview courseReview = reviewRepository.findById(review.getId())
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));
        courseReview.setRating(review.getRating());
        courseReview.setContent(review.getContent());
        courseReview.setUpdatedAt(LocalDateTime.now());
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
    @Override
    public CourseReview findById(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("리뷰를 찾을 수 없습니다. ID: " + reviewId));
    }
}
