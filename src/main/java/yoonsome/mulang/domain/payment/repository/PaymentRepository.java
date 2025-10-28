package yoonsome.mulang.domain.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yoonsome.mulang.domain.payment.entity.Payment;
import yoonsome.mulang.domain.payment.entity.PaymentStatus;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByOrderId(String orderId);
    Optional<Payment> findByPaymentKey(String paymentKey);
    List<Payment> findByUserIdAndStatus(Long userId, PaymentStatus status);
    List<Payment> findByUserId(Long userId);
    Optional<Payment> findByUserIdAndCourseId(Long userId, Long courseId);
}
