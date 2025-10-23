package yoonsome.mulang.domain.review.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import yoonsome.mulang.api.review.ReviewResponse;

public interface ReviewService {
    /*리뷰 리스트 조회*/
    Page<ReviewResponse> getReviewsByCourseId(Long courseId, Pageable pageable);
    /*평균 별점*/
    double getAverageRatingByCourseId(Long courseId);
    /*리뷰 수*/
    int countReviewByCourseId(Long courseId);
}
