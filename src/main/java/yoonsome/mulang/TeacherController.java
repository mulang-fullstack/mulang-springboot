package yoonsome.mulang;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TeacherController {
    @GetMapping("classEdit.do")
    public String mypage(){
        return "teacherMypage/classEdit";
    }
    @GetMapping("profileEdit.do")
    public String profileEdit(){
        return "teacherMypage/profileEdit";
    }
    @GetMapping("review.do")
    public String review(){
        return "teacherMypage/review";
    }
    @GetMapping("settlement.do")
    public String settlement(){
        return "teacherMypage/settlement";
    }
    @GetMapping("profile.do")
    public String profile(){
        return "teacherMypage/profile";
    }
    @GetMapping("classUpload.do")
    public String classUpload(){
        return "teacherClass/classUpload";
    }
}
