package yoonsome.mulang.api.payments.dto;

import lombok.Builder;
import lombok.Data;

/**
 * 결제 승인 응답
 */
@Data
@Builder
public class PaymentSuccessResponse {
    // 결제 정보
    private String orderId;
    private Integer amount;
    private String status;           // "COMPLETED"
    // 결제 수단
    private String paymentMethod;        // "카드", "가상계좌" 등
    private String paymentMethodDetail;  // "신한카드", "KB국민은행" 등
    // 시간 정보
    private String approvedAt;       // "2025-01-15 14:30:00"
    // 강좌 정보
    private String courseTitle;
    // 구매자 정보
    private String username;
    private String email;
}
