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
    public Page<ReviewResponse> getReviewsByCourseId(Long courseId, Pageable pageable) {
        Page<CourseReview> reviews = reviewRepository.findByCourseId(courseId, pageable);
        List<ReviewResponse> reviewResponses = new ArrayList<>();
        for (CourseReview courseReview : reviews.getContent()) {
            ReviewResponse reviewResponse = new ReviewResponse(
                    courseReview.getId(),
                    courseReview.getStudent().getNickname(),
                    courseReview.getRating(),
                    courseReview.getContent(),
                    courseReview.getCreatedAt(),
                    courseReview.getUpdatedAt()
            );
            reviewResponses.add(reviewResponse);
        }
        return new PageImpl<>(reviewResponses, pageable, reviews.getTotalElements());
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
