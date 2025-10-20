package yoonsome.mulang.teacher.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import yoonsome.mulang.course.entity.Course;
import yoonsome.mulang.teacher.entity.Teacher;
import yoonsome.mulang.teacher.service.TeacherService;
import yoonsome.mulang.user.entity.User;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/teacher/mypage")
public class TeacherMypageController {

    private final TeacherService teacherService;

    public TeacherMypageController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    /** 프로필 보기 */

    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        User loginUser = (User) session.getAttribute("loginUser");

        if (loginUser == null || !"TEACHER".equals(loginUser.getRole())) {
            return "redirect:/auth/login";
        }
        Teacher teacher = teacherService.getTeacherProfile(loginUser.getId());
        model.addAttribute("teacher", teacher);

        return "teacherMypage/profile";
    }

    /** 프로필 업데이트 */
    @PostMapping("/profile/update")
    public String updateProfile(
            HttpSession session,
            @ModelAttribute Teacher teacher,
            @RequestParam(value = "photo", required = false) MultipartFile photo
    ) throws IOException {
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null || !"TEACHER".equals(loginUser.getRole())) {
            return "redirect:/auth/login";
        }

        teacherService.updateTeacherProfile(loginUser.getId(), teacher, photo);
        return "redirect:/teacher/mypage/profile";
    }


    /** 프로필 수정 */
    @GetMapping("/profile/edit")
    public String profileEdit() {
        return "teacherMypage/profileEdit";
    }

    /** 리뷰 관리 */
    @GetMapping("/reviews")
    public String review() {
        return "teacherMypage/review";
    }

    /** 정산 관리 */
    @GetMapping("/settlement")
    public String settlement() {
        return "teacherMypage/settlement";
    }

    /** 강좌 수정 */
    @GetMapping("/classes/update")
    public String classUpdate() {
        return "teacherMypage/classUpdate";
    }

    /** Q&A 관리 */
    @GetMapping("/qna")
    public String qna() {
        return "teacherMypage/QnA";
    }
    /** 클래스 관리 목록 강좌 */
    @GetMapping("/classes/edit")
    public String classEdit(HttpSession session, Model model, Long courseId) {
        User loginUser = (User) session.getAttribute("loginUser");

        if(loginUser == null || !"TEACHER".equals(loginUser.getRole())){
            return "redirect:/auth/login";
        }

        List<Course> courseList = teacherService.getTeacherCourses(loginUser.getId());
        model.addAttribute("courseList", courseList);

        return "teacherMypage/classEdit";
    }
}
