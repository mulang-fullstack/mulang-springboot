package yoonsome.mulang.mypage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("mypage")
@Controller
public class RealpayController {
    @GetMapping("realpay")
    public String realpay(Model model) {
        return "mypage/realpay";
    }
    @GetMapping("paysuccess")
    public String paysuccess(Model model) {
        return "mypage/paysuccess";
    }

}
