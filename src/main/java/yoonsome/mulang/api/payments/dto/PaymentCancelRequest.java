package yoonsome.mulang.api.payments.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 결제 취소 요청
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentCancelRequest {
    private String orderId;
}