package yoonsome.mulang.domain.course.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import yoonsome.mulang.api.course.dto.CourseDetailResponse;
import yoonsome.mulang.api.course.dto.CourseListRequest;
import yoonsome.mulang.api.course.dto.CourseListResponse;
import yoonsome.mulang.domain.course.entity.Course;
import yoonsome.mulang.domain.course.entity.StatusType;
import yoonsome.mulang.domain.course.repository.CourseRepository;
import yoonsome.mulang.api.lecture.dto.LectureResponse;
import yoonsome.mulang.domain.lecture.entity.Lecture;
import yoonsome.mulang.domain.lecture.service.LectureService;
import yoonsome.mulang.api.review.ReviewResponse;
import yoonsome.mulang.domain.review.service.ReviewService;
import yoonsome.mulang.infra.file.entity.File;
import yoonsome.mulang.infra.file.service.FileService;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import static org.springframework.data.jpa.domain.AbstractAuditable_.createdDate;

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

    @Override
    public List<Course> getCourseList(Long languageId, Long categoryId, String keyword, StatusType status, Long teacherId, LocalDate createdDate, LocalDate startedDate, LocalDate endedDate){
        return courseRepository.findByLanguageIdAndCategoryIdAndKeywordAndStatusAndTeacherIdAndCreatedDate(languageId, categoryId, keyword, status, teacherId, createdDate, startedDate, endedDate);
    }

    @Override
    public Course getCourse(long id){
        Optional<Course> optCourse = courseRepository.findById(id);
        if (optCourse.isPresent()) {
           return optCourse.get();
        }else return null;
    }
    /*
    @Override
    public Page<CourseListResponse> getCourseList(CourseListRequest request, Pageable pageable) {
        Long languageId = request.getLanguageId();
        Long categoryId = request.getCategoryId();
        String keyword = request.getKeyword();

        // 1. Course 엔티티 조회
        Page<Course> courses = courseRepository.findByLanguageIdAndCategoryIdAndKeyword(languageId, categoryId, keyword, pageable);
        System.out.println("#courses: " + courses);


    }

    @Override
    public CourseDetailResponse getCourseDetail(long id) {
        Optional<Course> optCourse = courseRepository.findById(id);

    }
    */



    @Override
    public Course registerCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public void modifyCourse(Course course) {
        Optional<Course> optCourse = courseRepository.findById(course.getId());
        if (optCourse.isPresent()) {
            Course originCourse = optCourse.get();
            originCourse.setPrice(course.getPrice());//수정 항목 나중에 양진석가모니 완성하면 추가할거임
        }
    }

    @Override
    public void deleteCourse(long id) {
        courseRepository.deleteById(id);
    }
    /**
     * 강좌를 저장하고, 전달받은 리스트를 이용해 여러 개의 강의를 생성
     */

    @Override
    public void createCourseWithLectures(
            Course course,
            List<String> lectureTitles,
            List<MultipartFile> lectureVideos,
            MultipartFile thumbnail
    ) throws IOException {
        if (thumbnail != null && !thumbnail.isEmpty()) {
            File savedThumbnail = fileService.createFile(thumbnail);
            course.setThumbnail(savedThumbnail.getUrl());
        }

        Course savedCourse = courseRepository.save(course);

        if (lectureTitles == null || lectureTitles.isEmpty()) {
            return;
        }
        for (int i = 0; i < lectureTitles.size(); i++) {
            String title = lectureTitles.get(i);
            String content = lectureTitles.get(i);

            MultipartFile video = null;
            if (lectureVideos != null && lectureVideos.size() > i) {
                video = lectureVideos.get(i);
            }
            lectureService.createLectureWithFile(title, content, savedCourse, video);
        }
    }
    @Override
    public List<Course> getCoursesByTeacher(Long teacherId) {
        return courseRepository.findByTeacherId(teacherId);
    }

}
