package yoonsome.mulang.domain.payment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import yoonsome.mulang.domain.course.entity.Course;
import yoonsome.mulang.domain.course.repository.CourseRepository;
import yoonsome.mulang.domain.payment.dto.PaymentConfirmDto;
import yoonsome.mulang.domain.payment.dto.PaymentRequestDto;
import yoonsome.mulang.domain.payment.dto.PaymentResponseDto;
import yoonsome.mulang.domain.payment.service.PaymentService;

@Controller
@RequestMapping("/payments")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    private final PaymentService paymentService;
    private final CourseRepository courseRepository;

    /**
     * 결제 페이지 표시
     * GET /payments/{courseId}
     */
    @GetMapping("/{courseId}")
    public String showPaymentPage(@PathVariable Long courseId, Model model) {
        System.out.println("응애?");
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("강좌를 찾을 수 없습니다."));

        model.addAttribute("course", course);
        model.addAttribute("clientKey", "test_gck_docs_Ovk5rk1EwkEbP0W43n07xlzm");

        return "payment/payment"; // payment.jsp
    }

    /**
     * 결제 준비 (주문 생성)
     * POST /payments/prepare
     */
    @PostMapping("/prepare")
    @ResponseBody
    public ResponseEntity<PaymentResponseDto> preparePayment(
            @RequestBody PaymentRequestDto requestDto,
            @RequestParam Long userId) { // TODO: 실제로는 SecurityContext에서 가져오기

        try {
            PaymentResponseDto response = paymentService.preparePayment(userId, requestDto);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("결제 준비 실패: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 결제 승인
     * POST /payments/confirm
     */
    @PostMapping("/confirm")
    @ResponseBody
    public ResponseEntity<PaymentResponseDto> confirmPayment(
            @RequestBody PaymentConfirmDto confirmDto) {

        try {
            PaymentResponseDto response = paymentService.confirmPayment(confirmDto);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("결제 승인 실패: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 결제 성공 페이지
     * GET /payments/success
     */
    @GetMapping("/success")
    public String paymentSuccess(
            @RequestParam String paymentKey,
            @RequestParam String orderId,
            @RequestParam Integer amount,
            Model model) {

        try {
            // 결제 승인 처리
            PaymentConfirmDto confirmDto = new PaymentConfirmDto(paymentKey, orderId, amount);
            PaymentResponseDto response = paymentService.confirmPayment(confirmDto);

            model.addAttribute("payment", response);
            return "payment/paySuccess"; // paySuccess.jsp

        } catch (Exception e) {
            log.error("결제 승인 실패: {}", e.getMessage());
            model.addAttribute("errorMessage", e.getMessage());
            return "payment/payFail"; // payFail.jsp
        }
    }

    /**
     * 결제 실패 페이지
     * GET /payments/fail
     */
    @GetMapping("/fail")
    public String paymentFail(
            @RequestParam String code,
            @RequestParam String message,
            @RequestParam String orderId,
            Model model) {

        // 실패 정보 저장
        paymentService.handlePaymentFailure(orderId, code, message);

        model.addAttribute("errorCode", code);
        model.addAttribute("errorMessage", message);
        model.addAttribute("orderId", orderId);

        return "payfail"; // payFail.jsp
    }

    /**
     * 결제 정보 조회
     * GET /payments/info/{paymentId}
     */
    @GetMapping("/info/{paymentId}")
    @ResponseBody
    public ResponseEntity<PaymentResponseDto> getPaymentInfo(@PathVariable Long paymentId) {
        try {
            PaymentResponseDto response = paymentService.getPayment(paymentId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("결제 정보 조회 실패: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
