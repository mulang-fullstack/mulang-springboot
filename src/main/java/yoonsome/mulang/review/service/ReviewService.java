package yoonsome.mulang.review.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import yoonsome.mulang.review.entity.Review;

public interface ReviewService {
    /*리뷰 리스트 조회*/
    Page<Review> getReviewsByCourseId(Long courseId, Pageable pageable);
    /*평균 별점*/
    double getAverageRatingByCourseId(Long courseId);
    /*리뷰 수*/
    int countReviewByCourseId(Long courseId);
}
