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
import yoonsome.mulang.api.payments.dto.PaymentDetailResponse;
import yoonsome.mulang.api.payments.dto.PaymentSuccessResponse;
import yoonsome.mulang.domain.payment.service.PaymentService;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin/payment")
@RequiredArgsConstructor
public class AdminPaymentController {

    private final PaymentService paymentService;

    /**
     * 결제 내역 목록 페이지
     */
    @GetMapping
    public String payment(Model model){
        model.addAttribute("activeMenu","payment");
        model.addAttribute("activeSubmenu","payment");
        return "admin/payment/paymentList";
    }

    /**
     * 결제 내역 목록 조회 (비동기 API)
     */
    @GetMapping("/api")
    @ResponseBody
    public ResponseEntity<?> getPaymentList(PaymentSearchRequest request) {
        // 결제 목록 조회 (페이지 단위)
        Page<PaymentDetailResponse> page = paymentService.searchPayments(request);

        // 응답 데이터 구성
        Map<String, Object> result = new HashMap<>();
        result.put("payments", page.getContent());
        result.put("currentPage", page.getNumber());
        result.put("totalPages", page.getTotalPages());
        result.put("totalElements", page.getTotalElements());
        result.put("size", page.getSize());

        return ResponseEntity.ok(result);
    }
}
