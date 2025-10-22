package yoonsome.mulang.api.admin.content;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/content")
public class AdminCourseController {
    @GetMapping("/course")
    public String course(Model model) {
        model.addAttribute("activeMenu","content");
        model.addAttribute("activeSubmenu","course");
        return "admin/content/course";
    }
}
