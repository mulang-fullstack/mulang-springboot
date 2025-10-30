package yoonsome.mulang.api.payments.dto;

import lombok.Builder;
import lombok.Data;

/**
 * 결제 실패 응답
 * GET /payments/fail
 */
@Data
@Builder
public class PaymentFailResponse {
    private String orderId;
    private String errorCode;        // 토스 에러 코드
    private String errorMessage;     // 에러 메시지
    private String failureReason;    // 실패 사유

    // 강좌 정보 (재시도용)
    private Long courseId;
    private String courseTitle;
    private Integer amount;
}