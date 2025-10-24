package yoonsome.mulang.api.teacher.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import yoonsome.mulang.api.teacher.dto.CourseUploadRequest;
import yoonsome.mulang.api.teacher.dto.TeacherProfileResponse;
import yoonsome.mulang.api.teacher.service.TeacherMypageService;
import yoonsome.mulang.domain.category.entity.Category;
import yoonsome.mulang.domain.category.service.CategoryService;
import yoonsome.mulang.domain.language.entity.Language;
import yoonsome.mulang.domain.language.service.LanguageService;
import yoonsome.mulang.infra.security.CustomUserDetails;
import java.io.IOException;
import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/teacher/mypage/classes")
public class CourseUploadController {

    private final TeacherMypageService teacherMypageService;
    private final CategoryService categoryService;
    private final LanguageService languageService;

    /** [GET] 새 강좌 업로드 폼 페이지 */
    @GetMapping("/new")
    public String showCourseUploadForm(@AuthenticationPrincipal CustomUserDetails loginUser, Model model) {

        TeacherProfileResponse teacherProfileResponse = teacherMypageService.getTeacherProfileResponse(loginUser.getUser().getId());

        List<Category> categories = categoryService.getAllCategory();
        List<Language> languages = languageService.getAllLanguages();

        model.addAttribute("teacher", teacherProfileResponse);
        model.addAttribute("categories", categories);
        model.addAttribute("languages", languages);
        model.addAttribute("courseForm", new CourseUploadRequest());

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
}
