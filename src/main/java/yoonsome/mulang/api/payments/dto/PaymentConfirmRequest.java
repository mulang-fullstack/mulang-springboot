package yoonsome.mulang.api.payments.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 토스 결제 승인 요청
 * POST /payments/confirm
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentConfirmRequest {
    private String paymentKey;
    private String orderId;
    private Integer amount;
}
