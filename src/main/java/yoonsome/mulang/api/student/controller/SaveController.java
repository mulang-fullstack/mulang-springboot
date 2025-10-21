package yoonsome.mulang.api.student.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/student")
@Controller
public class SaveController {

    @GetMapping("save")
    public String save(Model model) {
        return "student/like/save";
    }
}
