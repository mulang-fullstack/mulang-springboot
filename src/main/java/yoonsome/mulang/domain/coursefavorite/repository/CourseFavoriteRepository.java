package yoonsome.mulang.domain.coursefavorite.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import yoonsome.mulang.domain.coursefavorite.entity.CourseFavorite;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseFavoriteRepository extends JpaRepository<CourseFavorite, Long> {

    // 조회
    @Query("""
        SELECT cf
        FROM CourseFavorite cf
        JOIN FETCH cf.course c
        LEFT JOIN FETCH c.teacher t
        LEFT JOIN FETCH t.user
        WHERE cf.student.id = :studentId
        ORDER BY cf.createdAt DESC
    """)
    List<CourseFavorite> findByStudentIdWithCourse(@Param("studentId") Long studentId);


    // 조회 (페이지네이션 - 5개씩)
    @Query("""
        SELECT cf
        FROM CourseFavorite cf
        JOIN FETCH cf.course c
        LEFT JOIN FETCH c.teacher t
        LEFT JOIN FETCH t.user
        WHERE cf.student.id = :studentId
        ORDER BY cf.createdAt DESC
    """)
    Page<CourseFavorite> findByStudentIdWithCourse(
            @Param("studentId") Long studentId,
            Pageable pageable
    );

    // 확인
    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);

    //  삭제
    @Transactional
    void deleteByStudentIdAndCourseId(Long studentId, Long courseId);
}