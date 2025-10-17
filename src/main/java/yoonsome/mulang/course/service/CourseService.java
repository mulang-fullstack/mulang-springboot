package yoonsome.mulang.course.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import yoonsome.mulang.course.entity.Course;

public interface CourseService {
    Page<Course> getCourseListByLanguage(Long languageId, Pageable pageable);
    Page<Course> getCourseListByCategory(Long categoryId, Pageable pageable);
    Course getCourseDetail(long id);
    Course registerCourse(Course course);
    void modifyCourse(Course course);
    void deleteCourse(long id);
}
