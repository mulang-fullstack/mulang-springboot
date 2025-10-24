package yoonsome.mulang.domain.course.service;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import yoonsome.mulang.domain.course.dto.CourseListRequest;
import yoonsome.mulang.domain.course.entity.Course;

import java.io.IOException;
import java.util.List;

public interface CourseService {
    Page<Course> getCourseList(CourseListRequest request);
    Course getCourse(long id);
    Course registerCourse(Course course);
    void modifyCourse(Course course);
    void deleteCourse(long id);
    void createCourseWithLectures(Course course, List<String> lectureTitles, List<MultipartFile> lectureVideos,MultipartFile thumbnail)
            throws IOException;
    List<Course> getCoursesByTeacher(Long teacherId);
}