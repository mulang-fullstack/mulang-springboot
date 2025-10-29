package yoonsome.mulang.domain.course.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import yoonsome.mulang.domain.course.entity.Course;
import yoonsome.mulang.domain.course.entity.StatusType;
import java.time.LocalDateTime;
import yoonsome.mulang.domain.teacher.entity.Teacher;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {
    //Page<Course> findByLanguage_Id(Long languageId);
    //Page<Course> findByLanguage_IdAndCategory_Id(Long languageId, Long categoryId, Pageable pageable);
    //Page<Course> findByLanguageIdAndKeyword(Long languageId, String keyword, Pageable pageable);
    @Query("""
        SELECT c
        FROM Course c
        LEFT JOIN c.teacher t
        LEFT JOIN t.user u
        WHERE (:languageId IS NULL OR c.language.id = :languageId)
          AND (:categoryId IS NULL OR c.category.id = :categoryId)
          AND (:keyword IS NULL OR :keyword = ''
               OR c.title LIKE CONCAT('%', :keyword, '%')
               OR (t IS NOT NULL AND u IS NOT NULL AND u.nickname LIKE CONCAT('%', :keyword, '%')))
          AND (:keyword IS NULL OR c.title LIKE %:keyword%)
          AND (:status IS NULL OR c.status = :status)
          AND (:startDate IS NULL OR c.createdAt >= :startDate)
          AND (:endDate IS NULL OR c.createdAt <= :endDate)
    """)
    Page<Course> findByLanguageIdAndCategoryIdAndKeywordAndStatusAndCreatedAt(
            @Param("languageId") Long languageId,
            @Param("categoryId") Long categoryId,
            @Param("keyword") String keyword,
            @Param("status") StatusType status,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime  endDate,
            Pageable pageable
    );

    @Query("SELECT c FROM Course c " +
            "WHERE c.teacher = :teacher " +
            "AND c.status IN :statuses " +
            "ORDER BY c.createdAt DESC")
    Page<Course> findByTeacherAndStatusIn(
            @Param("teacher") Teacher teacher,
            @Param("statuses") List<StatusType> statuses,
            Pageable pageable);

    @Query("SELECT c FROM Course c " +
            "WHERE (:languageId = 0 OR c.language.id = :languageId)" +
            "AND c.status = 'PUBLIC' " +
            "AND c.averageRating >= 4 " +
            "ORDER BY c.reviewCount DESC")
    List<Course> findTop4ByLanguageIdAndStatusAndAverageRating(@Param("languageId") Long languageId, Pageable pageable);

    List<Course> findAllByOrderByCreatedAtDesc(Pageable pageable);
}