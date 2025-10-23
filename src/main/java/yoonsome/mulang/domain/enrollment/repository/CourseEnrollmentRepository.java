package yoonsome.mulang.domain.enrollment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import yoonsome.mulang.domain.enrollment.entity.CourseEnrollment;

import java.util.List;

@Repository
public interface CourseEnrollmentRepository extends JpaRepository<CourseEnrollment, Long> {

    // 학생이 듣는 강좌 조회 (결제완료 + 수강완료)
    @Query("""
        SELECT ce
        FROM CourseEnrollment ce
        JOIN FETCH ce.course c
        LEFT JOIN FETCH c.teacher t
        LEFT JOIN FETCH t.user
        WHERE ce.student.id = :studentId
        AND ce.enrollmentStatus IN ('PAID', 'COMPLETED')
        ORDER BY ce.appliedAt DESC
    """)
    List<CourseEnrollment> findMyCoursesWithDetails(@Param("studentId") Long studentId);
}
