package yoonsome.mulang.domain.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseDto {
    private Long paymentId;
    private String orderId;
    private String paymentKey;
    private Integer amount;
    private String status;
    private String orderName;
    private String approvedAt;
    private String paymentMethod;
    private String paymentMethodDetail;
}
