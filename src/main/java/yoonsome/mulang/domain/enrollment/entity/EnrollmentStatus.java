package yoonsome.mulang.domain.enrollment.entity;

public enum EnrollmentStatus {
    APPLIED("신청"),
    CANCELLED("취소"),
    PAID("결제완료"),
    COMPLETED("수강완료");

    private final String description;

    EnrollmentStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
