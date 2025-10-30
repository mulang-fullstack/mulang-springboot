package yoonsome.mulang.api.course.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import yoonsome.mulang.api.course.dto.CourseDetailResponse;
import yoonsome.mulang.api.teacher.dto.TeacherProfileResponse;
import yoonsome.mulang.domain.course.dto.CourseListRequest;
import yoonsome.mulang.api.course.dto.CourseListResponse;
import yoonsome.mulang.api.course.service.DisplayingCourseService;
import yoonsome.mulang.api.review.ReviewResponse;
import yoonsome.mulang.domain.category.entity.Category;
import yoonsome.mulang.domain.user.entity.User;
import yoonsome.mulang.infra.security.CustomUserDetails;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/course")
@RequiredArgsConstructor
@Controller
public class CourseController {

    private final DisplayingCourseService displayingCourseService;

    /*강좌 리스트 목록*/
    @GetMapping
    public String getCourseList(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @ModelAttribute CourseListRequest request,
            HttpSession session, Model model) {
        //세션에서 userId 받아오기
        Long userId = null;
        if(userDetails != null){
            User sessionUser = userDetails.getUser(); // Security 세션에 저장된 사용자 엔티티
            userId = sessionUser.getId(); // 로그인한 사용자 ID 접근
        }
        //언어이름
        String languageName = displayingCourseService.getLanguageName(request, session);
        //특정 언어에 해당하는 카테고리들
        List<Category> categories = displayingCourseService.getCategoryList(request);
        //request 값에 해당하는 강좌 리스트 정보 페이지 객체
        Page<CourseListResponse> courseListResponses = displayingCourseService.getCoursePage(userId, request, session);

        //System.out.println("@request: "+request+"@userId: "+userId);

        model.addAttribute("languageName", languageName);
        model.addAttribute("categories", categories);
        model.addAttribute("courses", courseListResponses.getContent());
        model.addAttribute("totalPages", courseListResponses.getTotalPages());
        return "course/courseList";
    }
    /*찜 비동기*/
    @PostMapping("/courseFavorite")
    @ResponseBody
    public ResponseEntity<?> updateCourseFavorite(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam Long courseId) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "로그인이 필요한 기능입니다.", "loginUrl", "/auth/login"));
        }
        if (!userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_STUDENT"))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "학생만 가능한 기능입니다."));
        }
        displayingCourseService.addOrRemoveFavorite(userDetails.getUser().getId(), courseId);
        return ResponseEntity.ok(Map.of("success", true));
    }

    /*강좌 상세 페이지*/
    @GetMapping("/courseDetail")
    public String getCourseDetail(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "rating") String sortBy,
            Model model) {
        Long userId = null;
        if(userDetails != null){
            User sessionUser = userDetails.getUser(); // Security 세션에 저장된 사용자 엔티티
            userId = sessionUser.getId(); // 로그인한 사용자 ID 접근
        }
        /*강의 정보*/
        /*강의 소개*/
        /*커리큘럼(강의 리스트)*/
        CourseDetailResponse courseDetailResponse = displayingCourseService.getCourseDetail(userId, id);
        model.addAttribute("userId", userId);
        model.addAttribute("detail", courseDetailResponse);
        System.out.println("@courseDetail:"+courseDetailResponse);
        model.addAttribute("courseId", id);
        //getReviews(id, page, sortBy, model);

        return "course/courseDetail";
    }

    /* 리뷰 비동기 페이징 정렬 */
    @GetMapping("/courseDetail/reviews")
    @ResponseBody
    public Map<String, Object> getReviews(
            @RequestParam Long courseId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "rating") String sortBy
    ) {
        int size = 6;
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sortBy));
        Page<ReviewResponse> reviewPage = displayingCourseService.getReviewPageByCourseId(courseId, pageable);
        Map<String, Object> response = new HashMap<>();
        response.put("reviews", reviewPage.getContent());
        response.put("currentPage", reviewPage.getNumber());
        response.put("totalPages", reviewPage.getTotalPages());
        response.put("sortBy", sortBy);

        return response;
    }
    /*강사 프로필 보기*/
    @GetMapping("/courseDetail/teacherProfile")
    public String getTeacherProfile(@RequestParam Long id, Model model) {
        TeacherProfileResponse teacherProfileResponse = displayingCourseService.getTeacherProfileResponse(id);
        model.addAttribute("teacher", teacherProfileResponse);
        return "course/teacherProfile";
    }
    /*리뷰 동기 페이징 정렬
    private void getReviews(Long id, int page, String sortBy, Model model){
        int size = 2;
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sortBy));
        Page<ReviewResponse> reviewResponse = displayingCourseService.getReviewPageByCourseId(id, pageable);
        model.addAttribute("reviews", reviewResponse.getContent());
        model.addAttribute("currentPage", reviewResponse.getNumber());
        model.addAttribute("totalPages", reviewResponse.getTotalPages());
        model.addAttribute("sortBy",  sortBy);
        System.out.println("@review:"+reviewResponse.getContent());
        System.out.println("@page:"+page);
        System.out.println("@totalPages:"+ reviewResponse.getTotalPages());
    }*/
}
