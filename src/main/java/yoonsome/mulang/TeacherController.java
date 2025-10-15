package yoonsome.mulang;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TeacherController {
    @GetMapping("teacherMypage.do")
    public String mypage(){
        return "teacherMypage/teacherMypage";
    }
    @GetMapping("profile.do")
    public String profile(){
        return "teacherMypage/profile";
    }
    @GetMapping("review.do")
    public String review(){
        return "teacherMypage/review";
    }
    @GetMapping("settlement.do")
    public String settlement(){
        return "teacherMypage/settlement";
    }
}
