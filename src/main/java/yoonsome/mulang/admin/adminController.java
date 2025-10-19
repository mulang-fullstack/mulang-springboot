package yoonsome.mulang.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class adminController {
    @GetMapping("/dashboard/visitor")
    public String visitor(Model model){
        model.addAttribute("activeMenu","dashboard");
        model.addAttribute("activeSubmenu","visitor");
        return "admin/dashboard/visitor";
    }

    @GetMapping("/dashboard/sales")
    public String sales(Model model){
        model.addAttribute("activeMenu","dashboard");
        model.addAttribute("activeSubmenu","sales");
        return "admin/dashboard/sales";
    }

    @GetMapping("/userManage/member")
    public String member(Model model){
        model.addAttribute("activeMenu","userManage");
        model.addAttribute("activeSubmenu","member");
        return "admin/userManage/member";
    }

    @GetMapping("/userManage/userLog")
    public String userLog(Model model){
        model.addAttribute("activeMenu","userManage");
        model.addAttribute("activeSubmenu","userLog");
        return "admin/userManage/userLog";
    }

    @GetMapping("/contentManage/courseManage")
    public String courseManage(Model model){
        model.addAttribute("activeMenu","contentManage");
        model.addAttribute("activeSubmenu","courseManage");
        return "admin/contentManage/courseManage";
    }
}
