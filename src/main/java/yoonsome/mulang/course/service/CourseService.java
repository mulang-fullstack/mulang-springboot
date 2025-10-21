package yoonsome.mulang.course.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import yoonsome.mulang.course.dto.CourseListRequest;
import yoonsome.mulang.course.dto.CourseListResponse;
import yoonsome.mulang.course.entity.Course;
import java.io.IOException;
import java.util.List;

public interface CourseService {
    Page<Course> getCourseListByLanguage(Long languageId, Pageable pageable);
    Page<Course> getCourseListByCategory(Long categoryId, Pageable pageable);
    Page<CourseListResponse> getCourseListByLanguageAndCategory(CourseListRequest request, Pageable pageable);
    Course getCourseDetail(long id);
    Course registerCourse(Course course);
    void modifyCourse(Course course);
    void deleteCourse(long id);
    void createCourseWithLectures(Course course, List<String> lectureTitles, List<MultipartFile> lectureVideos,MultipartFile thumbnail)
            throws IOException;
    List<Course> getCoursesByTeacher(Long teacherId);
}
