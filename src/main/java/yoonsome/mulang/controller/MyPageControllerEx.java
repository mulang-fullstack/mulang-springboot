package yoonsome.mulang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("mypage")
@Controller
public class MyPageControllerEx {

    // 기본 마이페이지 화면
        @GetMapping("personal")
    public String personal() {
        return "mypage/personal";
    }
    @GetMapping("save")
    public String save() {
        return "mypage/save";
    }
    @GetMapping("payment")
    public String payment() {
        return "mypage/payment";
    }
    @GetMapping("studyroom")
    public String studyroom() {
        return "mypage/studyroom";
    }
}
