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
import yoonsome.mulang.domain.course.repository.CourseRepository;
import yoonsome.mulang.api.lecture.dto.LectureResponse;
import yoonsome.mulang.domain.lecture.entity.Lecture;
import yoonsome.mulang.domain.lecture.service.LectureService;
import yoonsome.mulang.api.review.ReviewResponse;
import yoonsome.mulang.domain.review.service.ReviewService;
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
    @Override
    public Page<CourseListResponse> getCourseList(CourseListRequest request, Pageable pageable) {
        Long languageId = request.getLanguageId();
        Long categoryId = request.getCategoryId();
        String keyword = request.getKeyword();

        // 1. Course 엔티티 조회
        Page<Course> courses = courseRepository.findByLanguageIdAndCategoryIdAndKeyword(languageId, categoryId, keyword, pageable);
        System.out.println("#courses: " + courses);

        // 2. DTO 리스트 생성
        List<CourseListResponse> dtoList = new ArrayList<>();

        for (Course course : courses.getContent()) {
            // 리뷰 정보 가져오기
            //double averageRating = reviewService.getAverageRatingByCourseId(course.getId());
            //int reviewCount = reviewService.countReviewByCourseId(course.getId());
            double averageRating = 2.5;
            int reviewCount = 1000;

            //강사 정보 가져오기
            //String teacherName = getTeacherName(course);
            //String teacherName = course.getTeacher().getUser().getUsername();
            //System.out.println("#teacherName: " + teacherName);
            //String teacherName = "예시 홍길동 선생님";

            // DTO 생성
            CourseListResponse dto = new CourseListResponse(
                    course.getId(),
                    course.getThumbnail(),
                    course.getTitle(),
                    course.getSubtitle(),
                    getTeacherName(course),
                    averageRating,
                    reviewCount,
                    course.getPrice()
            );
            dtoList.add(dto);
        }
        // 3. PageImpl로 다시 Page 객체 생성
        return new PageImpl<>(dtoList, pageable, courses.getTotalElements());
    }

    @Override
    public CourseDetailResponse getCourseDetail(long id) {
        Optional<Course> optCourse = courseRepository.findById(id);
        if (optCourse.isPresent()) {
            Course course = optCourse.get();
            CourseDetailResponse dto = CourseDetailResponse.builder()
                    .id(course.getId())
                    .title(course.getTitle())
                    .thumbnail(course.getThumbnail())
                    .subtitle(course.getSubtitle())
                    .content(course.getContent())
                    .teacherName(getTeacherName(course))
                    .applyStartedAt(course.getApplyStartedAt())
                    .applyEndedAt(course.getApplyEndedAt())
                    .startedAt(course.getStartedAt())
                    .endedAt(course.getEndedAt())
                    .lectureCount(course.getLectureCount())
                    .price(course.getPrice())
                    .lectures(getLectureList(course.getId()))
                    .build();
            return dto;
        }else return null;
    }
    */

    private String getTeacherName(Course course) {
        String teacherName = course.getTeacher().getUser().getNickname();
        return teacherName;
    }
    private List<LectureResponse> getLectureList(Long courseId) {
        List<Lecture> lectureList = lectureService.getLecturesByCourseId(courseId);
        List<LectureResponse> lectures = new ArrayList<>();
        for (Lecture lecture : lectureList) {
            LectureResponse dto = new LectureResponse(
                    lecture.getId(),
                    lecture.getTitle(),
                    lecture.getLength()
            );
            lectures.add(dto);
        }
        return lectures;
        //return null;
    }
    private Page<ReviewResponse> getReviewList(Long courseId, Pageable pageable) {
        Page<ReviewResponse> reviews = reviewService.getReviewsByCourseId(courseId, pageable);
        return null;
    }

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
