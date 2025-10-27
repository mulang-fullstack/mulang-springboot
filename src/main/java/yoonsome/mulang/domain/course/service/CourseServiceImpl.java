package yoonsome.mulang.domain.course.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import yoonsome.mulang.domain.course.dto.CourseListRequest;
import yoonsome.mulang.domain.course.entity.Course;
import yoonsome.mulang.domain.course.entity.StatusType;
import yoonsome.mulang.domain.course.repository.CourseRepository;
import yoonsome.mulang.domain.lecture.service.LectureService;
import yoonsome.mulang.domain.review.service.ReviewService;
import yoonsome.mulang.domain.teacher.entity.Teacher;
import yoonsome.mulang.infra.file.entity.File;
import yoonsome.mulang.infra.file.service.FileService;
import java.io.IOException;
import java.util.*;

@Transactional
@RequiredArgsConstructor
@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private final CourseRepository courseRepository;
    @Autowired
    private final ReviewService reviewService;
    @Autowired
    private LectureService lectureService;
    @Autowired
    private final FileService fileService;

    /*
    admin, course 강좌 정보 페이지 객체 가져오기
    request dto: languageId, categoryId, keyword, status, startedDate, endedDate, sort, page, size
    */
    @Override
    public Page<Course> getCourseList(CourseListRequest request){
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        return courseRepository.findByLanguageIdAndCategoryIdAndKeywordAndStatusAndCreatedDate(
                request.getLanguageId(),
                request.getCategoryId(),
                request.getKeyword(),
                request.getStatus(),
                request.getStartDate(),
                request.getEndDate(),
                pageable
        );
    }
    /* teacher 강사별 강좌 리스트 가져오기 */
    @Override
    public List<Course> getCoursesByTeacher(Long teacherId) {
        return courseRepository.findByTeacherId(teacherId);
    }
    /*강좌 상세페이지 강좌 상세 정보 가져오기*/
    @Override
    public Course getCourse(long id){
        Optional<Course> optCourse = courseRepository.findById(id);
        if (optCourse.isPresent()) {
           return optCourse.get();
        }else return null;
    }
    /*강좌 등록하기*/
    @Override
    public Course registerCourse(Course course) {
        return courseRepository.save(course);
    }
    /*강좌 수정하기*/
    @Override
    public void modifyCourse(Course course) {
        Optional<Course> optCourse = courseRepository.findById(course.getId());
        if (optCourse.isPresent()) {
            Course originCourse = optCourse.get();
            originCourse.setPrice(course.getPrice());//수정 항목 나중에 양진석가모니 완성하면 추가할거임
        }
    }
    /*강좌 삭제하기*/
    @Override
    public void deleteCourse(long id) {
        courseRepository.deleteById(id);
    }

    @Override
    public Page<Course> getTeacherCoursePage(Teacher teacher, List<StatusType> statuses, Pageable pageable) {
        return courseRepository.findByTeacherAndStatusIn(teacher, statuses, pageable);
    }
}
