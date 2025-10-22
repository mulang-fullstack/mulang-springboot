package yoonsome.mulang.api.admin.system;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/settings")
public class AdminSystemController {
    @GetMapping("/notice")
    public String notice(Model model){
        model.addAttribute("activeMenu","settings");
        model.addAttribute("activeSubmenu","notice");
        return "admin/user/user";
    }

    @GetMapping("/inquiry")
    public String inquiry(Model model){
        model.addAttribute("activeMenu","settings");
        model.addAttribute("activeSubmenu","inquiry");
        return "admin/user/user";
    }
}
