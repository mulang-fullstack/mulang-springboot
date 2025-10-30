package yoonsome.mulang.api.payments.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import yoonsome.mulang.api.payments.dto.*;
import yoonsome.mulang.domain.payment.service.PaymentFacadeService;
import yoonsome.mulang.infra.security.CustomUserDetails;

/**
 * 결제 컨트롤러
 */
@Controller
@RequestMapping("/payments")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    private final PaymentFacadeService paymentFacadeService;

    @Value("${toss.payments.client-key}")
    private String tossClientKey;

    /**
     * 1. 결제 페이지 진입
     * GET /payments/{courseId}
     */
    @GetMapping("/{courseId}")
    public String showPaymentPage(
            @PathVariable Long courseId,
            @AuthenticationPrincipal CustomUserDetails user,
            Model model) {

        try {
            PaymentPageResponse response =
                    paymentFacadeService.preparePaymentPage(courseId, user.getUser().getId());

            model.addAttribute("payment", response);
            model.addAttribute("clientKey", tossClientKey);

            return "payment/payment";

        } catch (IllegalStateException e) {
            // 이미 구매한 강좌
            model.addAttribute("errorMessage", e.getMessage());
            return "redirect:/course/courseDetail?id=" + courseId;

        } catch (Exception e) {
            log.error("결제 페이지 로드 실패: {}", e.getMessage());
            model.addAttribute("errorMessage", "결제 페이지를 불러올 수 없습니다.");
            return "redirect:/course/courseDetail?id=" + courseId;
        }
    }

    /**
     * 2. 결제 승인 (API)
     * POST /payments/confirm
     */
    @PostMapping("/confirm")
    @ResponseBody
    public ResponseEntity<PaymentSuccessResponse> confirmPayment(
            @RequestBody PaymentConfirmRequest request) {

        try {
            PaymentSuccessResponse response =
                    paymentFacadeService.confirmPayment(request);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("결제 승인 실패: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 3. 결제 성공 페이지
     * GET /payments/success
     */
    @GetMapping("/success")
    public String paymentSuccess(
            @RequestParam String paymentKey,
            @RequestParam String orderId,
            @RequestParam Integer amount,
            Model model) {

        try {
            PaymentConfirmRequest request =
                    new PaymentConfirmRequest(paymentKey, orderId, amount);

            PaymentSuccessResponse response =
                    paymentFacadeService.confirmPayment(request);

            model.addAttribute("payment", response);
            return "payment/paySuccess";

        } catch (Exception e) {
            log.error("결제 처리 실패: {}", e.getMessage());
            model.addAttribute("errorMessage", e.getMessage());
            return "payment/payFail";
        }
    }

    /**
     * 4. 결제 실패 페이지
     * GET /payments/fail
     */
    @GetMapping("/fail")
    public String paymentFail(
            @RequestParam String code,
            @RequestParam String message,
            @RequestParam String orderId,
            Model model) {

        PaymentFailResponse response =
                paymentFacadeService.handlePaymentFailure(orderId, code, message);

        model.addAttribute("failure", response);
        return "payment/payFail";
    }
}