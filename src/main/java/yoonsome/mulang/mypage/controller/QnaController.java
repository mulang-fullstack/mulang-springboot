package yoonsome.mulang.mypage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("mypage")
@Controller
public class QnaController {
    @GetMapping("qna")
    public String qna(Model model) {
        return "mypage/review/qna";
    }

}
