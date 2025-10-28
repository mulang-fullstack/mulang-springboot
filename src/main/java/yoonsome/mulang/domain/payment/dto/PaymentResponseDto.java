package yoonsome.mulang.domain.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import yoonsome.mulang.domain.payment.entity.Payment;

import java.time.format.DateTimeFormatter;

/**
 * 결제 응답 DTO
 * Payment → DTO 변환 메서드 포함
 */
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

    /**
     * Payment 엔티티 → PaymentResponseDto 변환
     */
    public static PaymentResponseDto from(Payment payment) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return PaymentResponseDto.builder()
                .paymentId(payment.getId())
                .orderId(payment.getOrderId())
                .paymentKey(payment.getPaymentKey())
                .amount(payment.getAmount())
                .status(payment.getStatus() != null ? payment.getStatus().name() : "")
                .orderName(payment.getCourse() != null ? payment.getCourse().getTitle() : "")
                .approvedAt(payment.getApprovedAt() != null ? payment.getApprovedAt().format(fmt) : "")
                .paymentMethod(payment.getPaymentMethod() != null ? payment.getPaymentMethod().name() : "")
                .paymentMethodDetail(payment.getPaymentMethodDetail() != null ? payment.getPaymentMethodDetail() : "")
                .build();
    }
}
