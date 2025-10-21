package yoonsome.mulang.domain.course.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import yoonsome.mulang.domain.course.entity.Course;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course,Long> {
    Page<Course> findByLanguage_Id(Long languageId, Pageable pageable);
    Page<Course> findByCategory_Id(Long categoryId, Pageable pageable);
    Page<Course> findByLanguage_IdAndCategory_Id(Long languageId, Long categoryId, Pageable pageable);
    /*
    @Query("""
        SELECT new com.example.dto.CourseListResponse(
            c.id, c.title, t.name, COUNT(r.id), COALESCE(AVG(r.rating),0)
        )
        FROM Course c
        JOIN c.teacher t
        LEFT JOIN c.reviews r
        WHERE (:languageId IS NULL OR c.language.id = :languageId)
          AND (:categoryId IS NULL OR c.category.id = :categoryId)
          AND (:keyword IS NULL OR c.title LIKE %:keyword%)
        GROUP BY c.id, t.name
    """)
    Page<CourseListResponse> findWithTeacherAndReviewByLanguageAndCategory
            (Long languageId, Long categoryId, Pageable pageable);*/
    //Page<Course> findByTitleOrTeacher(String title, String teacher, Pageable pageable);
    List<Course> findByTeacherId(Long teacherId);
}