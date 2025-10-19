package yoonsome.mulang.teacher.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/teacher/mypage")
public class TeacherMypageController {

    /** 프로필 보기 */
    @GetMapping("/profile")
    public String profile() {
        return "teacherMypage/profile";
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
    public String classEdit() {
        return "teacherMypage/classEdit";
    }

}
