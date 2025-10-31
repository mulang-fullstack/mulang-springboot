package yoonsome.mulang.api.payments.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRefundResponse {
    private String orderId;
    private String status;
    private Integer amount;
    private String courseTitle;
    private String refundedAt;
    private String message;
}