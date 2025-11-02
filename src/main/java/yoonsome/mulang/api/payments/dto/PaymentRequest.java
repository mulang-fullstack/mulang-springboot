package yoonsome.mulang.api.payments.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
    private Long courseId;
    private Integer amount;
    private String orderName;  // 주문명 (예: "Spring Boot 강좌")
}
