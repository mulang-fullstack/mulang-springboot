package yoonsome.mulang.api.student.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/student")
@Controller
public class PayController {
    @GetMapping("pay")
    public String pay(Model model) {
        return "student/payhistory/pay";
    }

}
