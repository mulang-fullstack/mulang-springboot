package yoonsome.mulang.mypage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("mypage")
@Controller
public class MyCourseController {
    @GetMapping("course")
    public String course(Model model) {
        return "mypage/mycourse/course";
    }
    @GetMapping("player")
    public String player(Model model) {
        return "mypage/mycourse/player";
    }

}
