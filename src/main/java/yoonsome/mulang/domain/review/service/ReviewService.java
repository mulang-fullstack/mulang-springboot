package yoonsome.mulang.domain.review.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import yoonsome.mulang.api.review.ReviewResponse;
import yoonsome.mulang.domain.review.entity.CourseReview;

public interface ReviewService {
    /*리뷰 리스트 조회*/
    //Page<ReviewResponse> getReviewsByCourseId(Long courseId, Pageable pageable);
    Page<CourseReview> getReviewsByCourseId(Long courseId, Pageable pageable);
    /*리뷰 등록*/
    void saveReview(CourseReview review);
    /*리뷰 수정*/
    void editReview(CourseReview review);
    /*리뷰 삭제*/
    void deleteReview(Long reviewId);
    /*평균 별점*/
    double getAverageRatingByCourseId(Long courseId);
    /*리뷰 수*/
    int countReviewByCourseId(Long courseId);
    /*리뷰 존재 여부*/
    boolean existReview(Long userId, Long courseId);

}
