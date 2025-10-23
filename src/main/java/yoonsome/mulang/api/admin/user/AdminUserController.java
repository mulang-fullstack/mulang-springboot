package yoonsome.mulang.api.admin.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/user")
public class AdminUserController {
    private AdminUserService adminUserService;

    @GetMapping
    public String user(Model model){
        model.addAttribute("activeMenu","user");
        model.addAttribute("activeSubmenu","user");
        return "admin/user/user";
    }

    @GetMapping("/log")
    public String userLog(Model model){
        model.addAttribute("activeMenu","user");
        model.addAttribute("activeSubmenu","userLog");
        return "admin/user/userLog";
    }
}