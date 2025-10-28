package yoonsome.mulang.domain.payment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import yoonsome.mulang.domain.payment.entity.Payment;
import yoonsome.mulang.domain.payment.entity.PaymentMethod;
import yoonsome.mulang.domain.payment.entity.PaymentStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 결제(Payment) 엔티티용 Repository
 * 기본 CRUD + 사용자/강좌별 조회 + 동적 검색 기능을 제공한다.
 */
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    /** 주문번호로 단일 결제 조회 */
    Optional<Payment> findByOrderId(String orderId);

    /** 결제 키로 단일 결제 조회 */
    Optional<Payment> findByPaymentKey(String paymentKey);

    /** 특정 사용자 + 결제 상태로 결제 목록 조회 */
    List<Payment> findByUserIdAndStatus(Long userId, PaymentStatus status);

    /** 특정 사용자의 모든 결제 조회 */
    List<Payment> findByUserId(Long userId);

    /** 사용자 + 강좌 조합으로 결제 단건 조회 (중복 결제 방지용) */
    Optional<Payment> findByUserIdAndCourseId(Long userId, Long courseId);

    /** 특정 강좌에 대한 전체 결제 내역 조회 */
    List<Payment> findByCourseId(Long courseId);

    /**
     * 결제 내역 검색 + 필터 + 키워드 + 기간
     *
     * [검색 조건]
     *  - type: 결제 수단(PaymentMethod)
     *  - status: 결제 상태(문자열, PaymentStatus.name())
     *  - startDate ~ endDate: 결제일 범위 (createdAt)
     *  - keyword: 주문번호 / 사용자 이메일 / 닉네임 / 강좌 제목 중 하나 포함
     *
     * [특징]
     *  - 각 파라미터는 null 가능하며, null일 경우 해당 조건은 무시됨
     *  - 부분 검색(LIKE) 및 대소문자 무시 처리
     *  - 결과는 Pageable 기반으로 페이징 처리
     */
    @Query("""
        SELECT p FROM Payment p
        WHERE (:type IS NULL OR p.paymentMethod = :type)
          AND (:status IS NULL OR LOWER(p.status) = LOWER(:status))
          AND (:startDate IS NULL OR p.createdAt >= :startDate)
          AND (:endDate IS NULL OR p.createdAt <= :endDate)
          AND (
               :keyword IS NULL
            OR LOWER(p.user.email) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(p.user.nickname) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(p.course.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
          )
        """)
    Page<Payment> searchPayments(
            @Param("type") PaymentMethod type,
            @Param("status") String status,
            @Param("keyword") String keyword,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable
    );
}
