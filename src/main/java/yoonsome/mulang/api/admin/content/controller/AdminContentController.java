package yoonsome.mulang.api.admin.content.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import yoonsome.mulang.api.admin.content.dto.AdminCourseResponse;
import yoonsome.mulang.api.admin.content.service.AdminContentService;
import yoonsome.mulang.domain.course.dto.CourseListRequest;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin/content")
@RequiredArgsConstructor
public class AdminContentController {

    private final AdminContentService adminContentService;

    /**
     * 강좌 관리 페이지 (초기 로드 - 동기 방식)
     */
    @GetMapping("/course")
    public String course(@ModelAttribute CourseListRequest request, Model model) {

        // DTO 내용 확인용 출력
        System.out.println("============ CourseListRequest ===========");
        System.out.println(request);

        // 기본값 설정
        if (request.getSize() == 0) {
            request.setSize(10);
        }

        // 강좌 목록 조회
        Page<AdminCourseResponse> coursePage = adminContentService.getCourseList(request);

        // 모델에 데이터 추가
        model.addAttribute("courses", coursePage.getContent());
        model.addAttribute("currentPage", coursePage.getNumber());
        model.addAttribute("totalPages", coursePage.getTotalPages());
        model.addAttribute("totalElements", coursePage.getTotalElements());
        model.addAttribute("search", request);

        //사이드바
        model.addAttribute("activeMenu", "content");
        model.addAttribute("activeSubmenu", "course");

        return "admin/content/course";
    }

    /**
     * 강좌 목록 조회 API (비동기 방식)
     */
    @GetMapping("/course/api")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getCourseListApi(@ModelAttribute CourseListRequest request) {
        // 기본값 설정
        if (request.getSize() == 0) {
            request.setSize(10);
        }
        // DTO 내용 확인용 출력
        System.out.println("============ CourseListRequest ===========");
        System.out.println(request);
        // 강좌 목록 조회
        Page<AdminCourseResponse> coursePage = adminContentService.getCourseList(request);

        // 응답 데이터 구성
        Map<String, Object> response = new HashMap<>();
        response.put("courses", coursePage.getContent());
        response.put("currentPage", coursePage.getNumber());
        response.put("totalPages", coursePage.getTotalPages());
        response.put("totalElements", coursePage.getTotalElements());
        response.put("size", coursePage.getSize());

        return ResponseEntity.ok(response);
    }

    /**
     * 강좌 관리 페이지 (초기 로드 - 동기 방식)
     */
    @GetMapping("/pendingCourse")
    public String pendingCourse(@ModelAttribute CourseListRequest request, Model model) {

        // DTO 내용 확인용 출력
        System.out.println("============ CourseListRequest ===========");
        System.out.println(request);

        // 기본값 설정
        if (request.getSize() == 0) {
            request.setSize(10);
        }

        // 강좌 목록 조회
        Page<AdminCourseResponse> coursePage = adminContentService.getCourseList(request);

        // 모델에 데이터 추가
        model.addAttribute("courses", coursePage.getContent());
        model.addAttribute("currentPage", coursePage.getNumber());
        model.addAttribute("totalPages", coursePage.getTotalPages());
        model.addAttribute("totalElements", coursePage.getTotalElements());
        model.addAttribute("search", request);
        model.addAttribute("activeMenu", "content");
        model.addAttribute("activeSubmenu", "pendingCourse");

        return "admin/content/pendingCourse";
    }
}