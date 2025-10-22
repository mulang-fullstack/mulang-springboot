package yoonsome.mulang.domain.course.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import yoonsome.mulang.domain.course.entity.Course;

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
        WHERE c.language.id = :languageId
          AND (:categoryId IS NULL OR c.category.id = :categoryId)
          AND (:keyword IS NULL OR :keyword = ''
               OR c.title LIKE CONCAT('%', :keyword, '%')
               OR (t IS NOT NULL AND u IS NOT NULL AND u.username LIKE CONCAT('%', :keyword, '%')))
    """)
    Page<Course> findByLanguageIdAndCategoryIdAndKeyword(
            @Param("languageId") Long languageId,
            @Param("categoryId") Long categoryId,
            @Param("keyword") String keyword,
            Pageable pageable);
    List<Course> findByTeacherId(Long teacherId);
}