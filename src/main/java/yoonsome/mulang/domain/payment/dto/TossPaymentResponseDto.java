package yoonsome.mulang.domain.payment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TossPaymentResponseDto {
    private String paymentKey;
    private String orderId;
    private String orderName;
    private String method;
    private Integer totalAmount;
    private String status;
    private String requestedAt;
    private String approvedAt;

    // 카드 정보
    private CardInfo card;

    // 가상계좌 정보
    private VirtualAccountInfo virtualAccount;

    // 실패 정보
    private String failure;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CardInfo {
        private String company;
        private String number;
        private String installmentPlanMonths;
        private String approveNo;
        private String cardType;
        private String ownerType;
        private String acquirerCode;
        private String issuerCode;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class VirtualAccountInfo {
        private String accountNumber;
        private String bankCode;
        private String customerName;
        private String dueDate;
        private String refundStatus;
        private Boolean expired;
        private String settlementStatus;
    }
}
