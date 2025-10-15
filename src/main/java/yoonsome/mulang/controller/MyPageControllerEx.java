package yoonsome.mulang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("mypage")
@Controller
public class MyPageControllerEx {
    @GetMapping("personal")
    public String personal(){
            return "mypage/personal";
        }
    @GetMapping("save")
    public String save(){
        return "mypage/save";
    }
    @GetMapping("pay")
    public String pay(){
        return "mypage/pay";
    }
    @GetMapping("course")
    public String coruse(){
        return "mypage/course";
    }
     @GetMapping("review")
    public String review(){
        return "mypage/review";
    }
    @GetMapping("edit")
    public String edit(){
        return "mypage/edit";
    }
}
