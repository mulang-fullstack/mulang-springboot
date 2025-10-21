package yoonsome.mulang.student.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/student")
@Controller
public class MyCourseController {
    @GetMapping("course")
    public String course(Model model) {
        return "student/mycourse/course";
    }
    @GetMapping("player")
    public String player(Model model) {
        return "student/mycourse/player";
    }

}
