package yoonsome.mulang.api.admin.dashboard.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import yoonsome.mulang.api.admin.dashboard.dto.DashboardResponse;
import yoonsome.mulang.api.admin.dashboard.service.AdminDashboardService;

@Controller
@RequestMapping("/admin/dashboard")
@RequiredArgsConstructor
public class AdminDashboardController {

    private final AdminDashboardService dashboardService;

    /**
     * 방문자 통계 페이지 (동기 JSP)
     */
    @GetMapping("/visitor")
    public String visitor(Model model) {
        DashboardResponse stats = dashboardService.getDashboardStats();

        model.addAttribute("activeMenu", "dashboard");
        model.addAttribute("activeSubmenu", "visitor");

        // 통계 데이터 바인딩
        model.addAttribute("stats", stats);
        model.addAttribute("todayLogins", stats.getTodayLogins());
        model.addAttribute("activeSessions", stats.getActiveSessions());
        model.addAttribute("todayNewUsers", stats.getTodayNewUsers());
        model.addAttribute("totalUsers", stats.getTotalUsers());
        model.addAttribute("weeklyLogins", stats.getWeeklyLogins());
        model.addAttribute("weeklyNewUsers", stats.getWeeklyNewUsers());
        model.addAttribute("loginChangeRate", stats.getLoginChangeRate());
        model.addAttribute("signupChangeRate", stats.getSignupChangeRate());

        return "admin/dashboard/visitor";
    }
    /**
     * 방문자 통계 비동기 요청 (Ajax)
     */
    @GetMapping("/visitor/api")
    @ResponseBody
    public ResponseEntity<DashboardResponse> getVisitorStats() {
        DashboardResponse stats = dashboardService.getDashboardStats();
        return ResponseEntity.ok(stats);
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
