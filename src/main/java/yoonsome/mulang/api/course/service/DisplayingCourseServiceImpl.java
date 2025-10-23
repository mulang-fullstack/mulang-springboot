package yoonsome.mulang.api.course.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import yoonsome.mulang.api.course.dto.CourseDetailResponse;
import yoonsome.mulang.api.course.dto.CourseListRequest;
import yoonsome.mulang.api.course.dto.CourseListResponse;
import yoonsome.mulang.api.lecture.dto.LectureResponse;
import yoonsome.mulang.api.review.ReviewResponse;
import yoonsome.mulang.domain.course.entity.Course;
import yoonsome.mulang.domain.course.entity.StatusType;
import yoonsome.mulang.domain.course.service.CourseService;
import yoonsome.mulang.domain.lecture.entity.Lecture;
import yoonsome.mulang.domain.lecture.service.LectureService;
import yoonsome.mulang.domain.review.service.ReviewService;

import java.util.ArrayList;
import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class DisplayingCourseServiceImpl implements DisplayingCourseService {
    private final CourseService courseService;
    private final LectureService lectureService;

    @Override
    public Page<CourseListResponse> getCourseList(CourseListRequest request, Pageable pageable) {
        Long languageId = request.getLanguageId();
        Long categoryId = request.getCategoryId();
        String keyword = request.getKeyword();
        StatusType status = request.getStatus();

        List<Course> courseList = courseService.getCourseList(languageId, categoryId, keyword, status, null, null, null, null);

        // 2. DTO 리스트 생성
        List<CourseListResponse> dtoList = new ArrayList<>();

        for (Course course : courseList) {
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
        return new PageImpl<>(dtoList, pageable, courseList.size());
        //return null;
    }

    @Override
    public CourseDetailResponse getCourseDetail(long id) {
        Course course = courseService.getCourse(id);
        CourseDetailResponse dto = CourseDetailResponse.builder()
                .id(course.getId())
                .title(course.getTitle())
                .thumbnail(course.getThumbnail())
                .subtitle(course.getSubtitle())
                .content(course.getContent())
                .teacherName(getTeacherName(course))
                .lectureCount(course.getLectureCount())
                .price(course.getPrice())
                .lectures(getLectureList(course.getId()))
                .build();
        return dto;
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
    }

    private String getTeacherName(Course course) {
        String teacherName = course.getTeacher().getUser().getNickname();
        return teacherName;
    }
}
