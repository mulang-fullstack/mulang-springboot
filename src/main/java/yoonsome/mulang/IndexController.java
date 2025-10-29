package yoonsome.mulang;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/index")
    public String index() {
        return "index";
    }
    @GetMapping("tests")
    public String test() {
        return "auth/login";
    }
}
