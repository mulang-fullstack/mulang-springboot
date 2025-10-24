package yoonsome.mulang.api.course.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import yoonsome.mulang.api.course.dto.CourseDetailResponse;
import yoonsome.mulang.domain.course.dto.CourseListRequest;
import yoonsome.mulang.api.course.dto.CourseListResponse;
import yoonsome.mulang.api.course.service.DisplayingCourseService;
import yoonsome.mulang.api.review.ReviewResponse;
import yoonsome.mulang.domain.category.entity.Category;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class CourseController {

    private final DisplayingCourseService displayingCourseService;

    /*강좌 리스트 목록*/
    @GetMapping("course")
    public String getCourseList(@ModelAttribute CourseListRequest request, HttpSession session, Model model) {
        //언어이름
        String languagename = displayingCourseService.getLanguageName(request, session);
        //특정 언어에 해당하는 카테고리들
        List<Category> categories = displayingCourseService.getCategoryList(request);
        //request 값에 해당하는 강좌 리스트 정보 페이지 객체
        Page<CourseListResponse> courseListResponses = displayingCourseService.getCoursePage(request, session);

        System.out.println("@request: "+request);

        model.addAttribute("languagename", languagename);
        model.addAttribute("categories", categories);
        model.addAttribute("courses", courseListResponses.getContent());
        model.addAttribute("totalPages", courseListResponses.getTotalPages());
        return "course/courseList";
    }

    /*강좌 상세 페이지*/
    @GetMapping("courseDetail")
    public String getCourseDetail(Long id, Model model) {
        /*강의 정보*/
        /*강의 소개*/
        /*커리큘럼(강의 리스트)*/
        CourseDetailResponse courseDetailResponse = displayingCourseService.getCourseDetail(id);
        model.addAttribute("detail", courseDetailResponse);
        System.out.println("@courseDetail:"+courseDetailResponse);

        /*리뷰
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<ReviewResponse> reviewResponse = displayingCourseService.getReviewPageByCourseId(id, pageable);
        model.addAttribute("review", reviewResponse.getContent());
        System.out.println("@review:"+reviewResponse.getContent());*/
        return "course/courseDetail";
    }
    // ✅ 리뷰 비동기 요청용 엔드포인트
    @GetMapping("/courseDetail/reviews")
    @ResponseBody
    public Page<ReviewResponse> getReviews(
            @RequestParam Long courseId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size,
            @RequestParam(defaultValue = "rating") String sort // 정렬 파라미터
    ) {
        Sort sortOption = "latest".equals(sort)
                ? Sort.by("createdAt").descending()
                : Sort.by("rating").descending(); // 기본: rating

        Pageable pageable = PageRequest.of(page, size, sortOption);
        return displayingCourseService.getReviewPageByCourseId(courseId, pageable);
    }
}
