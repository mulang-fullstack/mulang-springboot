package yoonsome.mulang.domain.payment.entity;

import jakarta.persistence.*;
import lombok.*;
import yoonsome.mulang.domain.course.entity.Course;
import yoonsome.mulang.domain.user.entity.User;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(name = "order_id", nullable = false, unique = true, length = 100)
    private String orderId; // 주문 고유 ID (UUID 또는 자체 생성)

    @Column(name = "payment_key", length = 200)
    private String paymentKey; // 토스 페이먼츠에서 발급하는 결제 키

    @Column(nullable = false)
    private Integer amount; // 결제 금액

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PaymentStatus status; // PENDING, COMPLETED, FAILED, CANCELLED, REFUNDED

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", length = 20)
    private PaymentMethod paymentMethod; // CARD, VIRTUAL_ACCOUNT, TRANSFER, MOBILE_PHONE, CULTURE_GIFT_CARD, BOOK_CULTURE_GIFT_CARD, GAME_CULTURE_GIFT_CARD

    @Column(name = "payment_method_detail", length = 100)
    private String paymentMethodDetail; // 카드사명, 은행명 등

    @Column(name = "approved_at")
    private LocalDateTime approvedAt; // 결제 승인 시각

    @Column(name = "requested_at", nullable = false)
    private LocalDateTime requestedAt; // 결제 요청 시각

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "failure_code", length = 50)
    private String failureCode; // 실패 코드

    @Column(name = "failure_message", length = 500)
    private String failureMessage; // 실패 메시지

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.requestedAt = LocalDateTime.now();
        this.status = PaymentStatus.PENDING;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
