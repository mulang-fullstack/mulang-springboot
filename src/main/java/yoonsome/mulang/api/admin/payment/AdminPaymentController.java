package yoonsome.mulang.api.admin.payment;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/payment")
public class AdminPaymentController {
    @GetMapping
    public String payment(Model model){
        model.addAttribute("activeMenu","payment");
        model.addAttribute("activeSubmenu","payment");
        return "admin/payment/payment";
    }

    @GetMapping("/refund")
    public String refund(Model model){
        model.addAttribute("activeMenu","payment");
        model.addAttribute("activeSubmenu","refund");
        return "admin/payment/refund";
    }
}
