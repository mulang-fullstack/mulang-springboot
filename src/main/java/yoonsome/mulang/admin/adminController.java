package yoonsome.mulang.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class adminController {
    @GetMapping("/visitor")
    public String visitor(Model model){
        model.addAttribute("activeMenu","dashboard");
        model.addAttribute("activeSubmenu","visitor");
        return "admin/dashboard/visitor";
    }
    @GetMapping("/sales")
    public String sales(Model model){
        model.addAttribute("activeMenu","dashboard");
        model.addAttribute("activeSubmenu","sales");
        return "admin/dashboard/sales";
    }
}
