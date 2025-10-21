package yoonsome.mulang.api.student.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/student")
@Controller
public class RealpayController {
    @GetMapping("realpay")
    public String realpay(Model model) {
        return "student/realpay";
    }
    @GetMapping("paysuccess")
    public String paysuccess(Model model) {
        return "student/paysuccess";
    }

}
