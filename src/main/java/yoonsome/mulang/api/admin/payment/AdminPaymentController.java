package yoonsome.mulang.api.admin.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import yoonsome.mulang.api.admin.payment.dto.PaymentSearchRequest;
import yoonsome.mulang.domain.payment.service.PaymentService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/payment")
@RequiredArgsConstructor
public class AdminPaymentController {

    private final PaymentService paymentService;

    @GetMapping
    public String payment(Model model){
        model.addAttribute("activeMenu","payment");
        model.addAttribute("activeSubmenu","payment");
        return "admin/payment/payment";
    }

    @GetMapping("/api")
    @ResponseBody
    public ResponseEntity<?> getPaymentList(PaymentSearchRequest request){
        List<PaymentSearchRequest> page = paymentService
    }

    @GetMapping("/refund")
    public String refund(Model model){
        model.addAttribute("activeMenu","payment");
        model.addAttribute("activeSubmenu","refund");
        return "admin/payment/refund";
    }
}
