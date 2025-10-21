package yoonsome.mulang.teacher.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@RequestMapping("/teacher/mypage")
public class TeacherMypageController {

    private final TeacherService teacherService;

    /** 프로필 보기 */
    @GetMapping("/profile")
    public String profile(Model model) {

        // User loginUser = (User) session.getAttribute("loginUser");
        // Teacher teacher = teacherService.getTeacherProfile(loginUser.getId());
        // model.addAttribute("teacher", teacher);
        return "teacherMypage/profile";
    }

    /** 프로필 수정 폼 */
    @GetMapping("/profile/edit")
    public String profileEdit() {
        return "teacherMypage/edit";
    }

    /** 프로필 업데이트 */
    @PostMapping("/profile/update")
    public String updateProfile(
            @ModelAttribute Teacher teacher,
            @RequestParam(value = "photo", required = false) MultipartFile photo
    ) throws IOException {

        // User loginUser = (User) session.getAttribute("loginUser");
        // teacherService.updateTeacherProfile(loginUser.getId(), teacher, photo);
        return "redirect:/teacher/mypage/profile";
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

    /** 클래스 목록 */
    @GetMapping("/classes/edit")
    public String classEdit(HttpSession session, Model model) {
        //User loginUser = (User) session.getAttribute("loginUser");
        //Teacher teacher = teacherService.getTeacherProfile(loginUser.getId());
        //Long teacherId = teacher.getId();

        Long teacherId = 1L; // 임시 테스트용 ID
        //List<Course> courseList = courseService.getCoursesByTeacher(teacherId);
        //model.addAttribute("courseList", courseList);

        return "teacherMypage/classEdit";
    }


    /** Q&A 관리 */
    @GetMapping("/qna")
    public String qna() {
        return "teacherMypage/QnA";
    }
}

