package yoonsome.mulang.student.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/student")
@Controller
public class QnaController {
    @GetMapping("qna")
    public String qna(Model model) {
        return "student/review/qna";
    }

}
