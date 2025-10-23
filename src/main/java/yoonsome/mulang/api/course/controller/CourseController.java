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
import yoonsome.mulang.api.course.dto.CourseListRequest;
import yoonsome.mulang.api.course.dto.CourseListResponse;
import yoonsome.mulang.api.course.service.DisplayingCourseService;
import yoonsome.mulang.api.review.ReviewResponse;
import yoonsome.mulang.domain.category.entity.Category;
import yoonsome.mulang.domain.category.service.CategoryService;
import yoonsome.mulang.domain.course.service.CourseService;
import yoonsome.mulang.domain.language.service.LanguageService;
import yoonsome.mulang.domain.review.service.ReviewService;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class CourseController {
    private final LanguageService languageService;
    private final CategoryService categoryService;
    private final DisplayingCourseService displayingCourseService;
    private final CourseService courseService;
    private final ReviewService reviewService;

    @GetMapping("course")
    public String getCourseList(@ModelAttribute CourseListRequest request, HttpSession session, Model model) {
        //languageId 세션 복원/저장
        if (request.getLanguageId() == null) {
            Long sessionLanguageId = (Long) session.getAttribute("languageId");
            request.setLanguageId(sessionLanguageId != null ? sessionLanguageId : 1L);
        } else {
            session.setAttribute("languageId", request.getLanguageId());
        }

        //sort 세션 복원/저장
        if (request.getSort() == null) {
            String sessionSort = (String) session.getAttribute("sort");
            request.setSort(sessionSort != null ? sessionSort : "rating");
        } else {
            session.setAttribute("sort", request.getSort());
        }
        /*언어*/
        String languageName = languageService.getLanguageNameById(request.getLanguageId());
        model.addAttribute("languageName", languageName);

        /*카테고리*/
        List<Category> categories = categoryService.getCategoryListByLanguageId(request);
        model.addAttribute("categories", categories);
        //System.out.println("@categories: " + categories);

        /*강좌*/
        System.out.println("@request:"+request);
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<CourseListResponse> courses = displayingCourseService.getCourseList(request, pageable);
        model.addAttribute("courses", courses.getContent());
        System.out.println("@course:"+courses.getContent());

        /*페이지*/
        model.addAttribute("totalPages", courses.getTotalPages());

        return "course/courseList";
    }
    /*
    @GetMapping("course")
    public Page<CourseListResponse> getCourseList(@ModelAttribute CourseListRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        return courseService.getCourseListByLanguageAndCategory(request.getLanguageId(), request.getCategoryId(), pageable);
    }*/
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
        Page<ReviewResponse> reviewResponse = reviewService.getReviewsByCourseId(id, pageable);
        model.addAttribute("reviews", reviewResponse.getContent());
        System.out.println("@review:"+reviewResponse.getContent());*/

        return "course/courseDetail";
    }
    /*리뷰*/
    @GetMapping("/courseDetail/{id}/reviews")
    @ResponseBody
    public Page<ReviewResponse> getCourseReviews(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return reviewService.getReviewsByCourseId(id, pageable);
    }

}
