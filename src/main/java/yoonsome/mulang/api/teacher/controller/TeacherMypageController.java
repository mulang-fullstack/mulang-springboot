package yoonsome.mulang.api.teacher.controller;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import yoonsome.mulang.api.teacher.dto.TeacherProfileUpdateRequest;
import yoonsome.mulang.api.teacher.service.TeacherMypageService;
import yoonsome.mulang.domain.course.entity.Course;
import yoonsome.mulang.domain.course.service.CourseService;
import yoonsome.mulang.domain.language.entity.Language;
import yoonsome.mulang.domain.language.repository.LanguageRepository;
import yoonsome.mulang.infra.security.CustomUserDetails;
import yoonsome.mulang.domain.teacher.entity.Teacher;
import yoonsome.mulang.domain.user.entity.User;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/teacher/mypage")
public class TeacherMypageController {

    private final TeacherMypageService teacherService;
    private final CourseService courseService;
    private final LanguageRepository languageRepository;

    /** 프로필 보기 */
    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        User loginUser = userDetails.getUser();
        Teacher teacher = teacherService.getTeacherProfile(loginUser.getId());
        model.addAttribute("teacher", teacher);
        return "teacher/profile";
    }

    /** 프로필 수정 폼 */
    @GetMapping("/profile/edit")
    public String profileEdit(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        Long userId = userDetails.getUser().getId();
        Teacher teacher = teacherService.getTeacherProfile(userId);

        List<Language> languages = languageRepository.findAll();

        model.addAttribute("teacher", teacher);
        model.addAttribute("languages", languages);
        return "teacher/edit";
    }

    /** 프로필 업데이트 */
    @PostMapping("/profile/update")
    public String updateProfile(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @ModelAttribute TeacherProfileUpdateRequest request
    ) throws IOException {
        teacherService.updateTeacherProfile(userDetails.getUser().getId(), request);
        return "redirect:/teacher/mypage/profile";
    }


    /** 정산 관리 */
    @GetMapping("/settlement")
    public String settlement(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        User loginUser = userDetails.getUser();
        Teacher teacher = teacherService.getTeacherProfile(loginUser.getId());
        model.addAttribute("teacher", teacher);
        return "teacher/settlement";
    }

    /** 강좌 수정 */
    @GetMapping("/classes/update")
    public String courseUpdatePage() {
        return "teacher/classUpdate";
    }

    /** 클래스 목록 */
    @GetMapping("/classes/edit")
    public String courseEditPage(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        User loginUser = userDetails.getUser();
        Teacher teacher = teacherService.getTeacherProfile(loginUser.getId());
        Long teacherId = teacher.getId();

        List<Course> courseList = courseService.getCoursesByTeacher(teacherId);
        model.addAttribute("teacher", teacher);
        model.addAttribute("courseList", courseList);
        return "teacher/classEdit";
    }
}
