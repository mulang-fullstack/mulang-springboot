package yoonsome.mulang.teacher.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import yoonsome.mulang.course.entity.Course;
import yoonsome.mulang.course.service.CourseService;
import yoonsome.mulang.global.security.CustomUserDetails;
import yoonsome.mulang.teacher.entity.Teacher;
import yoonsome.mulang.teacher.service.TeacherService;
import yoonsome.mulang.user.entity.User;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/teacher/mypage")
public class TeacherMypageController {

    private final TeacherService teacherService;
    @Autowired
    private final CourseService courseService;

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
        User loginUser = userDetails.getUser();
        Teacher teacher = teacherService.getTeacherProfile(loginUser.getId());
        model.addAttribute("teacher", teacher);
        return "teacher/edit";
    }

    /** 프로필 업데이트 */
    @PostMapping("/profile/update")
    public String updateProfile(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @ModelAttribute Teacher teacher,
            @RequestParam(value = "photo", required = false) MultipartFile photo
    ) throws IOException {
        User loginUser = userDetails.getUser();
        teacherService.updateTeacherProfile(loginUser.getId(), teacher, photo);
        return "redirect:/teacher/mypage/profile";
    }

    /** 리뷰 관리 */
    @GetMapping("/reviews")
    public String review() {
        return "teacher/review";
    }

    /** 정산 관리 */
    @GetMapping("/settlement")
    public String settlement() {
        return "teacher/settlement";
    }

    /** 강좌 수정 */
    @GetMapping("/classes/update")
    public String classUpdate() {
        return "teacher/classUpdate";
    }

    /** 클래스 목록 */
    @GetMapping("/classes/edit")
    public String classEdit(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        User loginUser = userDetails.getUser();
        Teacher teacher = teacherService.getTeacherProfile(loginUser.getId());
        Long teacherId = teacher.getId();

        List<Course> courseList = courseService.getCoursesByTeacher(teacherId);
        model.addAttribute("courseList", courseList);
        return "teacher/classEdit";
    }

    /** Q&A 관리 */
    @GetMapping("/qna")
    public String qna() {
        return "teacher/QnA";
    }
}
