package yoonsome.mulang.api.payments.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRefundRequest {
    private String orderId;
    private String reason; // 환불 사유
}