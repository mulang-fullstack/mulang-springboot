package yoonsome.mulang.domain.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import yoonsome.mulang.domain.review.dto.ReviewResponse;
import yoonsome.mulang.domain.review.entity.CourseReview;
import yoonsome.mulang.domain.review.repository.ReviewRepository;

@RequiredArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private final ReviewRepository reviewRepository;

    @Override
    public Page<ReviewResponse> getReviewsByCourseId(Long courseId, Pageable pageable) {
        Page<CourseReview> reviews = reviewRepository.findByCourseId(courseId, pageable);
        return null;
    }

    @Override
    public double getAverageRatingByCourseId(Long courseId) {
        return 0;
    }

    @Override
    public int countReviewByCourseId(Long courseId) {
        return 0;
    }
}
