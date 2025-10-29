package yoonsome.mulang.domain.progress.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import yoonsome.mulang.domain.progress.entity.Progress;

@Repository
public interface ProgressRepository extends JpaRepository<Progress, Long> {

    // 특정 강좌의 완료한 강의 수
    @Query("""
        SELECT COUNT(p)
        FROM Progress p
        JOIN p.lecture l
        WHERE p.student.id = :userId
        AND l.course.id = :courseId
        AND p.progressStatus = true
    """)
    Long countCompletedLecturesByCourseId(
            @Param("userId") Long userId,
            @Param("courseId") Long courseId
    );
    // 특정 강좌의 전체 강의 수 (추가)
    @Query("""
        SELECT COUNT(p)
        FROM Progress p
        JOIN p.lecture l
        WHERE p.student.id = :userId
        AND l.course.id = :courseId
    """)
    Long countTotalLecturesByCourseId(
            @Param("userId") Long userId,
            @Param("courseId") Long courseId
    );
}