package yoonsome.mulang.domain.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import yoonsome.mulang.api.review.ReviewResponse;
import yoonsome.mulang.domain.review.entity.CourseReview;
import yoonsome.mulang.domain.review.repository.ReviewRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private final ReviewRepository reviewRepository;

    @Override
    public Page<CourseReview> getReviewsByCourseId(Long courseId, Pageable pageable) {
        return reviewRepository.findByCourseId(courseId, pageable);
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
}
