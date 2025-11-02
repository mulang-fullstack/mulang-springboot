package yoonsome.mulang.api.admin.dashboard.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import yoonsome.mulang.api.admin.dashboard.dto.DashboardResponse;
import yoonsome.mulang.api.admin.dashboard.dto.SalesStatisticsResponse;
import yoonsome.mulang.api.admin.dashboard.service.AdminDashboardService;
import yoonsome.mulang.domain.payment.service.PaymentService;

@Controller
@RequestMapping("/admin/dashboard")
@RequiredArgsConstructor
public class AdminDashboardController {

    private final AdminDashboardService dashboardService;
    private final PaymentService paymentService;

    /**
     * 방문자 통계 페이지 (동기 JSP)
     */
    @GetMapping("/visitor")
    public String visitor(Model model) {
        model.addAttribute("activeMenu", "dashboard");
        model.addAttribute("activeSubmenu", "visitor");
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
     * 매출 현황 페이지
     */
    @GetMapping("/sales")
    public String sales(Model model) {
        model.addAttribute("activeMenu", "dashboard");
        model.addAttribute("activeSubmenu", "sales");
        return "admin/dashboard/sales";
    }

    /**
     * 매출 통계 데이터 API
     */
    @GetMapping("/sales/api")
    @ResponseBody
    public ResponseEntity<SalesStatisticsResponse> getSalesStatistics() {
        SalesStatisticsResponse statistics = paymentService.getSalesStatistics();
        return ResponseEntity.ok(statistics);
    }
}
