package yoonsome.mulang.teacher.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//@Controller
public class TeacherMypageController {
    @GetMapping("classEdit")
    public String classmain(){
        return "teacherMypage/classEdit";
    }
    @GetMapping("profileEdit")
    public String profileEdit(){
        return "teacherMypage/profileEdit";
    }
    @GetMapping("review")
    public String review(){
        return "teacherMypage/review";
    }
    @GetMapping("settlement")
    public String settlement(){
        return "teacherMypage/settlement";
    }
    /*@GetMapping("profile")
    public String profile(){
        return "teacherMypage/profile";
    }*/
    @GetMapping("classUpload")
    public String classUpload(){
        return "teacherClass/classUpload";
    }
    @GetMapping("classUpdate")
    public String classUpdate(){
        return "teacherClass/classUpdate";
    }
    @GetMapping("QnA")
    public String QnA(){
        return "teacherMypage/QnA";
    }
}
