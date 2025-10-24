package yoonsome.mulang.domain.payment.entity;

public enum PaymentStatus {
    APPLIED("신청"),
    CANCELLED("취소"),
    PAID("결제완료"),
    COMPLETED("수강완료");

    private final String description;

    PaymentStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
