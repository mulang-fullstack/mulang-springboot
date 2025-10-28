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
import yoonsome.mulang.api.teacher.service.TeacherCourseService;
import yoonsome.mulang.api.teacher.service.TeacherMypageService;
import yoonsome.mulang.domain.category.entity.Category;
import yoonsome.mulang.domain.category.service.CategoryService;
import yoonsome.mulang.domain.course.entity.Course;
import yoonsome.mulang.domain.course.service.CourseService;
import yoonsome.mulang.domain.language.entity.Language;
import yoonsome.mulang.domain.language.service.LanguageService;
import yoonsome.mulang.domain.lecture.entity.Lecture;
import yoonsome.mulang.domain.lecture.service.LectureService;
import yoonsome.mulang.infra.security.CustomUserDetails;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/teacher/mypage")
public class TeacherMypageController {


    private final LanguageService languageService;
    private final TeacherMypageService teacherMypageService;
    private final TeacherCourseService teacherCourseService;
    private final CategoryService categoryService;
    private final CourseService courseService;
    private final LectureService lectureService;

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
            @ModelAttribute TeacherProfileUpdateRequest request,
            Model model
    ) {
        try {
            teacherMypageService.updateTeacherProfile(userDetails.getUser().getId(), request);
            return "redirect:/teacher/mypage/profile";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            TeacherProfileResponse teacher = teacherMypageService.getTeacherProfileResponse(userDetails.getUser().getId());
            model.addAttribute("teacher", teacher);
            return "teacher/edit";
        } catch (IOException e) {
            model.addAttribute("errorMessage", "파일 업로드 중 오류가 발생했습니다.");
            return "teacher/edit";
        }
    }

    /** 강좌 수정 폼이동 */
    @GetMapping("/classes/update/{courseId}")
    public String courseUpdatePage(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long courseId,
            Model model) {
        Long userId = userDetails.getUser().getId();
        TeacherCourseResponse course = teacherCourseService.getCourseDetail(userId, courseId);
        TeacherProfileResponse teacherProfileResponse = teacherMypageService.getTeacherProfileResponse(userId);

        List<Language> languages = languageService.getAllLanguages();
        List<Category> categories = categoryService.getAllCategory();

        model.addAttribute("teacher", teacherProfileResponse);
        model.addAttribute("course", course);
        model.addAttribute("lectures", course.getLectures());
        model.addAttribute("languages", languages);
        model.addAttribute("categories", categories);
        return "teacher/classUpdate";
    }

    /** 강좌 수정 */
    @PostMapping("/classes/update")
    public String updateCourse(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @ModelAttribute CourseUpdateRequest request
    ) throws IOException {
        Long userId = userDetails.getUser().getId();
        teacherCourseService.updateCourse(userId, request.getCourseId(), request);
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
        Page<TeacherCourseResponse> coursePage = teacherCourseService.getTeacherCoursePage(userId, pageable);

        model.addAttribute("teacher", teacher);
        model.addAttribute("coursePage", coursePage);
        model.addAttribute("courses", coursePage.getContent());
        model.addAttribute("currentPage", coursePage.getNumber());
        model.addAttribute("totalPages", coursePage.getTotalPages());

        return "teacher/classEdit";
    }

    /** 클래스 삭제  */
    @PostMapping("/delete/{courseId}")
    public String deleteCourse(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long courseId){
        Long userId = userDetails.getUser().getId();
        teacherCourseService.deleteCourse(userId, courseId);
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

    // 비동기 페이징
    @GetMapping("/classes/page")
    @ResponseBody
    public Page<TeacherCourseResponse> getTeacherCoursePageAjax(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PageableDefault(size = 5) Pageable pageable) {

        Long userId = userDetails.getUser().getId();
        return teacherCourseService.getTeacherCoursePage(userId, pageable);
    }
}
