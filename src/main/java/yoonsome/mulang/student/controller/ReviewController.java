package yoonsome.mulang.student.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/student")
@Controller
public class ReviewController {
    @GetMapping("review")
    public String review(Model model) {
        return "student/review/review";
    }
    @GetMapping("reviewwrite")
    public String reviewWrite() {
        return "student/review/reviewwrite"; // JSP 경로
    }

}
