package yoonsome.mulang.domain.enrollment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import yoonsome.mulang.domain.enrollment.entity.Enrollment;
import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    Optional<Enrollment> findByUserIdAndCourseId(Long userId, Long courseId);
    List<Enrollment> findByUserId(Long userId);
    List<Enrollment> findByCourseId(Long courseId);
    boolean existsByUserIdAndCourseId(Long userId, Long courseId);

    // ⭐ 전체 조회
    @Query("""
        SELECT DISTINCT e
        FROM Enrollment e
        JOIN FETCH e.course c
        JOIN FETCH c.language l
        LEFT JOIN FETCH c.teacher t
        LEFT JOIN FETCH t.user u
        WHERE e.user.id = :userId
        ORDER BY e.enrolledAt DESC
    """)
    List<Enrollment> findByUserIdWithCourse(@Param("userId") Long userId);

    // ⭐ 언어별 조회
    @Query("""
        SELECT DISTINCT e
        FROM Enrollment e
        JOIN FETCH e.course c
        JOIN FETCH c.language l
        LEFT JOIN FETCH c.teacher t
        LEFT JOIN FETCH t.user u
        WHERE e.user.id = :userId
        AND l.id = :languageId
        ORDER BY e.enrolledAt DESC
    """)
    List<Enrollment> findByUserIdAndLanguage(
            @Param("userId") Long userId,
            @Param("languageId") Long languageId
    );

    // ⭐ 검색
    @Query("""
        SELECT DISTINCT e
        FROM Enrollment e
        JOIN FETCH e.course c
        JOIN FETCH c.language l
        LEFT JOIN FETCH c.teacher t
        LEFT JOIN FETCH t.user u
        WHERE e.user.id = :userId
        AND (c.title LIKE CONCAT('%', :keyword, '%') 
             OR c.subtitle LIKE CONCAT('%', :keyword, '%'))
        ORDER BY e.enrolledAt DESC
    """)
    List<Enrollment> findByUserIdAndKeyword(
            @Param("userId") Long userId,
            @Param("keyword") String keyword
    );

    // ⭐ 검색 + 언어
    @Query("""
        SELECT DISTINCT e
        FROM Enrollment e
        JOIN FETCH e.course c
        JOIN FETCH c.language l
        LEFT JOIN FETCH c.teacher t
        LEFT JOIN FETCH t.user u
        WHERE e.user.id = :userId
        AND l.id = :languageId
        AND (c.title LIKE CONCAT('%', :keyword, '%') 
             OR c.subtitle LIKE CONCAT('%', :keyword, '%'))
        ORDER BY e.enrolledAt DESC
    """)
    List<Enrollment> findByUserIdAndLanguageAndKeyword(
            @Param("userId") Long userId,
            @Param("languageId") Long languageId,
            @Param("keyword") String keyword
    );
}
