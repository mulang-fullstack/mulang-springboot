package yoonsome.mulang.api.teacher.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import yoonsome.mulang.api.teacher.dto.CourseUpdateRequest;
import yoonsome.mulang.api.teacher.dto.TeacherCourseResponse;
import yoonsome.mulang.api.teacher.dto.TeacherProfileResponse;
import yoonsome.mulang.api.teacher.dto.TeacherProfileUpdateRequest;
import yoonsome.mulang.api.teacher.service.TeacherMypageService;
import yoonsome.mulang.domain.language.entity.Language;
import yoonsome.mulang.domain.language.service.LanguageService;
import yoonsome.mulang.infra.security.CustomUserDetails;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/teacher/mypage")
public class TeacherMypageController {


    private final LanguageService languageService;
    private final TeacherMypageService teacherMypageService;

    /** 프로필 보기 */
    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        Long userId = userDetails.getUser().getId();
        TeacherProfileResponse teacherProfileResponse = teacherMypageService.getTeacherProfileResponse(userId);
        model.addAttribute("teacher", teacherProfileResponse);
        return "teacher/profile";
    }

    /** 프로필 수정 폼 */
    @GetMapping("/profile/edit")
    public String profileEdit(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        Long userId = userDetails.getUser().getId();
        TeacherProfileResponse teacherProfileResponse = teacherMypageService.getTeacherProfileResponse(userId);

        List<Language> languages = languageService.getAllLanguages();

        model.addAttribute("teacher", teacherProfileResponse);
        model.addAttribute("languages", languages);
        return "teacher/edit";
    }

    // 프로필 업데이트
    @PostMapping("/profile/update")
    public String updateProfile(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @ModelAttribute TeacherProfileUpdateRequest request
    ) throws IOException {
        teacherMypageService.updateTeacherProfile(userDetails.getUser().getId(), request);
        return "redirect:/teacher/mypage/profile";
    }

    /** 강좌 수정 폼이동 */
    @GetMapping("/classes/update/{courseId}")
    public String courseUpdatePage(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long courseId,
            Model model) {
        Long userId = userDetails.getUser().getId();
        TeacherCourseResponse course = teacherMypageService.getCourseDetail(userId, courseId);
        TeacherProfileResponse teacherProfileResponse = teacherMypageService.getTeacherProfileResponse(userId);

        model.addAttribute("teacher", teacherProfileResponse);
        model.addAttribute("course", course);
        return "teacher/classUpdate";
    }

    /** 강좌 수정 */
    @PostMapping("/classes/update/{courseId}")
    public String updateCourse(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long courseId,
            @ModelAttribute CourseUpdateRequest request
    )throws IOException {
        Long userId = userDetails.getUser().getId();
        teacherMypageService.updateCourse(userId, courseId, request);
        return "redirect:/teacher/mypage/classes/edit";
    }

    /** 클래스 목록  */
    @GetMapping("/classes/edit")
    public String courseEditPage(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PageableDefault(size = 5) Pageable pageable,
            Model model) {

        Long userId = userDetails.getUser().getId();
        TeacherProfileResponse teacher = teacherMypageService.getTeacherProfileResponse(userId);
        Page<TeacherCourseResponse> coursePage = teacherMypageService.getTeacherCoursePage(userId, pageable);

        model.addAttribute("teacher", teacher);
        model.addAttribute("coursePage", coursePage);
        model.addAttribute("courses", coursePage.getContent());

        return "teacher/classEdit";
    }


    /** 클래스 삭제  */
    @PostMapping("/delete/{courseId}")
    public String deleteCourse(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long courseId){
        Long userId = userDetails.getUser().getId();
        teacherMypageService.deleteCourse(userId, courseId);
        return "redirect:/teacher/mypage/classes/edit";
    }

    // 정산 관리
    @GetMapping("/settlement")
    public String settlement(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        Long userId = userDetails.getUser().getId();
        TeacherProfileResponse teacherProfileResponse = teacherMypageService.getTeacherProfileResponse(userId);
        model.addAttribute("teacher", teacherProfileResponse);
        return "teacher/settlement";
    }



}
