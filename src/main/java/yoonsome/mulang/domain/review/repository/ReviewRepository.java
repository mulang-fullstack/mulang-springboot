package yoonsome.mulang.domain.review.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import yoonsome.mulang.domain.review.entity.CourseReview;

public interface ReviewRepository extends JpaRepository<CourseReview, Long> {
    /*courseId 해당 강좌의 리뷰 페이지 객체*/
    Page<CourseReview> findByCourseId(Long courseId, Pageable pageable);
    /*courseId 해당 강좌의 평균 별점*/
    @Query("SELECT ROUND(AVG(r.rating), 1) FROM CourseReview r WHERE r.course.id = :courseId")
    Double findRoundedAverageRatingByCourseId(@Param("courseId") Long courseId);
    /*courseId 해당 강좌의 리뷰 수*/
    @Query("SELECT COUNT(r) FROM CourseReview r WHERE r.course.id = :courseId")
    Integer countReviewByCourseId(@Param("courseId") Long courseId);
}
