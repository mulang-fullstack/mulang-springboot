package yoonsome.mulang.domain.course.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

import static yoonsome.mulang.domain.course.entity.StatusType.PUBLIC;

@Transactional
@RequiredArgsConstructor
@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private final CourseRepository courseRepository;

    /*
    admin, course 강좌 정보 페이지 객체 가져오기
    request dto: languageId, categoryId, keyword, status, startDate, endDate, page, size, sortBy, sortDirection
    */
    @Override
    public Page<Course> getCourseList(CourseListRequest request){
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), Sort.by(
                Sort.Direction.fromString(request.getSortDirection()),
                request.getSortBy()
        ));
        return courseRepository.findByLanguageIdAndCategoryIdAndKeywordAndStatusAndCreatedAt(
                request.getLanguageId(),
                request.getCategoryId(),
                request.getKeyword(),
                request.getStatus(),
                request.getStartDate(),
                request.getEndDate(),
                pageable
        );
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

    @Override
    public Page<Course> getTeacherCoursePage(Teacher teacher, List<StatusType> statuses, Pageable pageable) {
        return courseRepository.findByTeacherAndStatusIn(teacher, statuses, pageable);
    }

    @Override
    public List<Course> getCourseRankingList(long languageId) {
        return courseRepository.findByLanguage_IdAndStatus(languageId, PUBLIC);
    }
}
