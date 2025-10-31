package yoonsome.mulang.api.course.service;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import yoonsome.mulang.api.course.dto.CourseDetailResponse;
import yoonsome.mulang.api.payments.dto.PaymentDetailResponse;
import yoonsome.mulang.api.review.ReviewResponse;
import yoonsome.mulang.api.teacher.dto.TeacherProfileResponse;
import yoonsome.mulang.api.teacher.service.TeacherMypageService;
import yoonsome.mulang.domain.category.entity.Category;
import yoonsome.mulang.domain.category.service.CategoryService;
import yoonsome.mulang.domain.course.dto.CourseListRequest;
import yoonsome.mulang.api.course.dto.CourseListResponse;
import yoonsome.mulang.api.course.dto.CourseLectureResponse;
import yoonsome.mulang.domain.course.entity.Course;
import yoonsome.mulang.domain.course.entity.StatusType;
import yoonsome.mulang.domain.course.service.CourseService;
import yoonsome.mulang.domain.coursefavorite.entity.CourseFavorite;
import yoonsome.mulang.domain.coursefavorite.service.CourseFavoriteService;
import yoonsome.mulang.domain.enrollment.service.EnrollmentService;
import yoonsome.mulang.domain.language.service.LanguageService;
import yoonsome.mulang.domain.lecture.entity.Lecture;
import yoonsome.mulang.domain.lecture.service.LectureService;
import yoonsome.mulang.domain.payment.service.PaymentService;
import yoonsome.mulang.domain.review.entity.CourseReview;
import yoonsome.mulang.domain.review.service.ReviewService;
import yoonsome.mulang.infra.file.service.S3FileService;

import java.util.*;

@Transactional
@RequiredArgsConstructor
@Service
public class DisplayingCourseServiceImpl implements DisplayingCourseService {
    private final LanguageService languageService;
    private final CategoryService categoryService;
    private final CourseService courseService;
    private final LectureService lectureService;
    private final ReviewService reviewService;
    private final TeacherMypageService teacherMypageService;
    private final CourseFavoriteService courseFavoriteService;
    private final S3FileService s3FileService;
    private final PaymentService paymentService;
    private final EnrollmentService enrollmentService;

    //언어 이름
    //LanguageId null일 경우에 세션에서 마지막 사용한 값으로 설정, 세션값 null일 경우에 초기값 1로 설정
    @Override
    public String getLanguageName(CourseListRequest request, HttpSession session){
        resolveLanguageId(request, session);
        return languageService.getNameById(request.getLanguageId());
    }

    //카테고리 리스트
    @Override
    public List<Category> getCategoryList(CourseListRequest request){
        return categoryService.getCategoryListByLanguageId(request.getLanguageId());
    }

    //강좌 리스트 정보 페이지 객체
    @Override
    public Page<CourseListResponse> getCoursePage(Long userId, CourseListRequest request, HttpSession session) {
        //** request dto에 변수값 담아서 강좌 페이지 객체 가져오기 **//
        //languageId 세션 복원/저장
        resolveLanguageId(request, session);

        //sort 세션 복원/저장
        if (request.getSortBy() == null) {
            String sessionSortby = (String) session.getAttribute("sortBy");
            request.setSortBy(sessionSortby != null ? sessionSortby : "averageRating");
        } else {
            session.setAttribute("sortBy", request.getSortBy());
        }

        //status: 공개
        request.setStatus(StatusType.PUBLIC);
        //한 페이지에 가져올 강좌 수 4개
        request.setSize(4);
        //정렬
        //request.setSortBy("averageRating");
        System.out.println("#displaying request: "+ request);
        Page<Course> coursePage = courseService.getCourseList(request);

        //**CourseListResponse DTO 리스트 생성**//
        List<CourseListResponse> dtoList = new ArrayList<>();

        // 로그인 했을 시 찜 여부 정보 가져오기
        // 다회쿼리 방지 해당 user 찜한 set, 결제 set 모두 가져옴
        Set<Long> favoriteCourseIds = Collections.emptySet();
        Set<Long> paymentCourseIds = Collections.emptySet();
        if(userId != null){
            favoriteCourseIds = new HashSet<>(courseFavoriteService.getCourseIdsByUserId(userId));
            paymentCourseIds = new HashSet<>(paymentService.getCourseIdsByUserId(userId));
        }
        for (Course course : coursePage) {
            /* 리뷰 정보 가져오기
            double averageRating = reviewService.getAverageRatingByCourseId(course.getId());
            int reviewCount = reviewService.countReviewByCourseId(course.getId());
            course.setAverageRating(averageRating);
            course.setReviewCount(reviewCount);*/

            // DTO 생성
            CourseListResponse dto = CourseListResponse.builder()
                    .id(course.getId())
                    .thumbnail(s3FileService.getPublicUrl(course.getFile().getId()))
                    .title(course.getTitle())
                    .subtitle(course.getSubtitle())
                    .teacherName(getTeacherName(course))
                    .averageRating(course.getAverageRating())
                    .reviewCount(course.getReviewCount())
                    .price(course.getPrice())
                    .createdAt(course.getCreatedAt())
                    .favorited(favoriteCourseIds.contains(course.getId()))
                    .paid(paymentCourseIds.contains(course.getId()))
                    .build();
            dtoList.add(dto);
        }

        //** Pageable 생성 **//
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());

        //** PageImpl로 다시 Page 객체 생성 **//
        return new PageImpl<>(dtoList, pageable, coursePage.getTotalElements());
    }

    //강좌 상세페이지 정보(강의소개, 커리큘럼)
    @Override
    public CourseDetailResponse getCourseDetail(Long userId, long id) {
        Course course = courseService.getCourse(id);
        CourseDetailResponse dto = CourseDetailResponse.builder()
                .id(course.getId())
                .title(course.getTitle())
                .thumbnail(s3FileService.getPublicUrl(course.getFile().getId()))
                .subtitle(course.getSubtitle())
                .content(course.getHtmlContent())
                .teacherName(getTeacherName(course))
                .lectureCount(course.getLectureCount())
                .averageRating(course.getAverageRating())
                .reviewCount(course.getReviewCount())
                .price(course.getPrice())
                .lectures(getLectureList(course.getId()))
                .favorited(existsCourseFavorite(userId, course.getId()))
                .paid(enrollmentService.hasEnrollment(userId, course.getId()))
                .build();
        return dto;
    }
    public boolean existsCourseFavorite(Long userId, long courseId) {
        return courseFavoriteService.existsCourseFavorite(userId, courseId);
    }

    //강좌 상세페이지 리뷰 정보(리뷰)
    @Override
    public Page<ReviewResponse> getReviewPageByCourseId(Long courseId, Pageable pageable){
        Page<CourseReview> reviews = reviewService.getReviewsByCourseId(courseId, pageable);
        List<ReviewResponse> reviewResponses = new ArrayList<>();
        for (CourseReview courseReview : reviews.getContent()) {
            ReviewResponse reviewResponse = new ReviewResponse(
                    courseReview.getId(),
                    courseReview.getStudent().getId(),
                    courseReview.getStudent().getNickname(),
                    courseReview.getRating(),
                    courseReview.getContent(),
                    courseReview.getCreatedAt(),
                    courseReview.getUpdatedAt(),
                    s3FileService.getPublicUrl(courseReview.getStudent().getFile().getId())
            );
            reviewResponses.add(reviewResponse);
        }
        return new PageImpl<>(reviewResponses, pageable, reviews.getTotalElements());
    }

    //강사 프로필 정보
    @Override
    public TeacherProfileResponse getTeacherProfileResponse(long id){
        Course course = courseService.getCourse(id);
        Long userId = course.getTeacher().getUser().getId();
        return teacherMypageService.getTeacherProfileResponse(userId);
    }

    //찜 기능
    @Override
    public void addOrRemoveFavorite(Long studentId, Long courseId){
        courseFavoriteService.addOrRemoveCourseFavorite(studentId, courseId);
    }

    //LanguageId null일 경우에 세션에서 마지막 사용한 값으로 설정, 세션값 null일 경우에 초기값 1로 설정
    private void resolveLanguageId(CourseListRequest request, HttpSession session){
        //languageId 세션 복원/저장
        if (request.getLanguageId() == null) {
            Long sessionLanguageId = (Long) session.getAttribute("languageId");
            request.setLanguageId(sessionLanguageId != null ? sessionLanguageId : 1L);
        } else {
            session.setAttribute("languageId", request.getLanguageId());
        }
    }

    //해당 강좌의 강사 이름
    private String getTeacherName(Course course) {
        String teacherName = course.getTeacher().getUser().getNickname();
        return teacherName;
    }

    //해당 강좌의 강의 리스트 정보
    private List<CourseLectureResponse> getLectureList(Long courseId) {
        List<Lecture> lectureList = lectureService.getLecturesByCourseId(courseId);
        List<CourseLectureResponse> lectures = new ArrayList<>();
        for (Lecture lecture : lectureList) {
            CourseLectureResponse dto = new CourseLectureResponse(
                    lecture.getId(),
                    lecture.getTitle(),
                    lecture.getLength()
            );
            lectures.add(dto);
        }
        return lectures;
    }
}
