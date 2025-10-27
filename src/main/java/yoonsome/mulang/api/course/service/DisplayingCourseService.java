package yoonsome.mulang.api.course.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import yoonsome.mulang.api.course.dto.CourseDetailResponse;
import yoonsome.mulang.api.review.ReviewResponse;
import yoonsome.mulang.api.teacher.dto.TeacherProfileResponse;
import yoonsome.mulang.domain.category.entity.Category;
import yoonsome.mulang.domain.course.dto.CourseListRequest;
import yoonsome.mulang.api.course.dto.CourseListResponse;
import java.util.List;

public interface DisplayingCourseService {
    //언어이름
    String getLanguageName(CourseListRequest request, HttpSession session);
    //카테고리 목록
    List<Category> getCategoryList(CourseListRequest request);
    //강좌 리스트 정보 페이지 객체
    Page<CourseListResponse> getCoursePage(CourseListRequest request, HttpSession session);
    //강좌 상세페이지 정보
    CourseDetailResponse getCourseDetail(long id);
    //강좌 상세페이지 리뷰
    Page<ReviewResponse> getReviewPageByCourseId(Long courseId, Pageable pageable);
    //강사 프로필 조회
    TeacherProfileResponse getTeacherProfileResponse(long id);
}
