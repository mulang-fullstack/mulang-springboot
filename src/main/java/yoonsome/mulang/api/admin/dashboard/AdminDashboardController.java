package yoonsome.mulang.api.admin.dashboard;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/dashboard")
public class AdminDashboardController {
    /**
     * 방문자 통계 페이지
     * @return JSP, 메뉴 상태 정보 (activeMenu / activeSubmenu )
     */
    @GetMapping("/visitor")
    public String visitor(Model model){
        model.addAttribute("activeMenu","dashboard");
        model.addAttribute("activeSubmenu","visitor");
        return "admin/dashboard/visitor";
    }
    /**
     * 매출 통계 페이지
     */
    @GetMapping("/sales")
    public String sales(Model model){
        model.addAttribute("activeMenu","dashboard");
        model.addAttribute("activeSubmenu","sales");
        return "admin/dashboard/sales";
    }
}
