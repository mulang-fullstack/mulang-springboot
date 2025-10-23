package yoonsome.mulang.api.teacher.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import yoonsome.mulang.api.teacher.dto.CourseUploadRequest;
import yoonsome.mulang.api.teacher.service.TeacherMypageService;

import yoonsome.mulang.domain.course.service.CourseService;
import yoonsome.mulang.domain.teacher.entity.Teacher;
import yoonsome.mulang.domain.user.entity.User;
import yoonsome.mulang.infra.file.service.FileService;
import yoonsome.mulang.infra.security.CustomUserDetails;

import java.io.IOException;
import java.util.List;

/**
 * ============================================================
 * [CourseUploadController]
 * ------------------------------------------------------------
 * 담당 기능
 *  - 교사 마이페이지 내 강좌 관련 기능 관리
 *  - 강좌 등록, 강좌 수정, VOD 업로드, 강좌 삭제 등
 *
 * 접근 제한
 *  - /teacher/mypage/** 경로 전체는 TeacherAuthInterceptor에 의해
 *    로그인 및 교사 권한(TEACHER) 검증이 이미 수행됨.
 *
 * 주요 매핑
 *  - GET  /teacher/mypage/classes/new    → 강좌 등록 페이지 이동
 *  - POST /teacher/mypage/classes        → 새 강좌 등록
 *  - DELETE /teacher/mypage/classes/{id} → 강좌 삭제
 * ============================================================
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/teacher/mypage/classes")
public class CourseUploadController {

    private final CourseService courseService;
    private final FileService fileService;
    private final TeacherMypageService teacherMypageService;

    /** [GET] 새 강좌 업로드 폼 페이지 */
    @GetMapping("/new")
    public String showCourseUploadForm(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        User loginUser = userDetails.getUser();
        Teacher teacher = teacherMypageService.getTeacherProfile(loginUser.getId());
        model.addAttribute("teacher", teacher);
        return "teacher/classUpload";
    }

    /** [POST] 새 강좌 등록 처리 */
    @PostMapping
    public String createCourse(
            @ModelAttribute CourseUploadRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) throws IOException {
        teacherMypageService.createCourse(userDetails.getUser().getId(), request);
        return "redirect:/teacher/mypage/classes/edit";
    }

    /** [DELETE] 강좌 삭제 처리 */
    @DeleteMapping("/{courseId}")
    @ResponseBody
    public ResponseEntity<Void> deleteCourse(@PathVariable Long courseId) {
        courseService.deleteCourse(courseId);
        return ResponseEntity.ok().build();
    }
}
