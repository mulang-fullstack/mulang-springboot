package yoonsome.mulang.domain.review.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import yoonsome.mulang.domain.review.entity.CourseReview;

public interface ReviewRepository extends JpaRepository<CourseReview, Long> {
    Page<CourseReview> findByCourseId(Long courseId, Pageable pageable);
}
