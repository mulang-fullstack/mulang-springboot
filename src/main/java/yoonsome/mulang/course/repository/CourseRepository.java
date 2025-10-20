package yoonsome.mulang.course.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import yoonsome.mulang.course.entity.Course;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course,Long> {
    Page<Course> findByLanguageId(Long languageId, Pageable pageable);
    Page<Course> findByCategoryId(Long categoryId, Pageable pageable);
    //Page<Course> findByTitleOrTeacher(String title, String teacher, Pageable pageable);
    List<Course> findByTeacherId(Long teacherId);
}