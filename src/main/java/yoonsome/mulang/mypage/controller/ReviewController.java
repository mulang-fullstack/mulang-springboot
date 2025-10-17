package yoonsome.mulang.mypage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("mypage")
@Controller
public class ReviewController {
    @GetMapping("review")
    public String review(Model model) {
        return "mypage/review/review";
    }

}
