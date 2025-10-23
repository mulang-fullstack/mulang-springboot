package yoonsome.mulang.domain.course.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.web.multipart.MultipartFile;
import yoonsome.mulang.api.course.dto.CourseDetailResponse;
import yoonsome.mulang.api.course.dto.CourseListRequest;
import yoonsome.mulang.api.course.dto.CourseListResponse;
import yoonsome.mulang.domain.course.entity.Course;
import yoonsome.mulang.domain.course.entity.StatusType;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface CourseService {
    //Page<Course> getCourseListByLanguage(Long languageId, Pageable pageable);
    //Page<CourseListResponse> getCourseList(CourseListRequest request, Pageable pageable);
    List<Course> getCourseList(Long languageId, Long categoryId, String keyword, StatusType status, Long teacherId, LocalDate createdDate, LocalDate startedDate, LocalDate endedDate);
    //CourseDetailResponse getCourseDetail(long id);
    Course getCourse(long id);
    Course registerCourse(Course course);
    void modifyCourse(Course course);
    void deleteCourse(long id);
    void createCourseWithLectures(Course course, List<String> lectureTitles, List<MultipartFile> lectureVideos,MultipartFile thumbnail)
            throws IOException;
    List<Course> getCoursesByTeacher(Long teacherId);
}