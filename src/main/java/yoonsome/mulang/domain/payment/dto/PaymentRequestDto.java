package yoonsome.mulang.domain.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequestDto {
    private Long courseId;
    private Integer amount;
    private String orderName;  // 주문명 (예: "Spring Boot 강좌")
}
