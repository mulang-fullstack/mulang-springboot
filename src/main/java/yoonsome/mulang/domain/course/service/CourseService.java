package yoonsome.mulang.domain.course.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import yoonsome.mulang.domain.course.dto.CourseListRequest;
import yoonsome.mulang.domain.course.entity.Course;
import yoonsome.mulang.domain.course.entity.StatusType;
import yoonsome.mulang.domain.teacher.entity.Teacher;

import java.io.IOException;
import java.util.List;

public interface CourseService {
    /*
    admin, course 강좌 정보 페이지 객체 가져오기
    request dto: languageId, categoryId, keyword, status, startDate, endDate, page, size, sortBy, sortDirection
    */
    Page<Course> getCourseList(CourseListRequest request);

    /*강좌 상세페이지 강좌 상세 정보 가져오기*/
    Course getCourse(long id);

    /*강좌 등록하기*/
    Course registerCourse(Course course);

    /* teacher 강사별 강좌 리스트 가져오기 */
    Page<Course> getTeacherCoursePage(Teacher teacher, List<StatusType> statuses, Pageable pageable);

    /*주간BEST 강의, 실시간 랭킹 가져오기*/
    List<Course> getCourseRankingList(long languageId);

    /*신규 클래스 가져오기*/
    List<Course> getNewCourseList();
}