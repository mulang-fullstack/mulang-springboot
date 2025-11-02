package yoonsome.mulang.api.payments.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 토스 페이먼츠 API 응답
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TossPaymentResponse {
    private String paymentKey;
    private String orderId;
    private String orderName;
    private String method;           // 결제 수단
    private Integer totalAmount;
    private String status;
    private String requestedAt;
    private String approvedAt;

    // 카드 정보
    private CardInfo card;

    // 가상계좌 정보
    private VirtualAccountInfo virtualAccount;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CardInfo {
        private String company;      // 카드사
        private String number;       // 카드번호 (마스킹)
        private String installmentPlanMonths; // 할부 개월
        private String cardType;     // 카드 타입
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class VirtualAccountInfo {
        private String accountNumber;
        private String bankCode;
        private String customerName;
        private String dueDate;
    }
}