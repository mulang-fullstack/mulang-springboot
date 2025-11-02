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
import java.util.*;

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

    /* 특정 사용자의 결제 강좌ID 조회 */
   @Query("SELECT p.course.id FROM Payment p WHERE p.user.id = :userId AND p.status = 'COMPLETED'")
   Set<Long> findCourseIdsByUserId(Long userId);

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

    /**
     * 특정 기간의 COMPLETED 상태 결제 총액 조회
     */
    @Query("""
        SELECT COALESCE(SUM(p.amount), 0)
        FROM Payment p
        WHERE p.status = 'COMPLETED'
          AND p.createdAt >= :startDate
          AND p.createdAt < :endDate
        """)
    Long sumAmountByPeriod(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    /**
     * 특정 기간의 COMPLETED 상태 결제 건수 조회
     */
    @Query("""
        SELECT COUNT(p)
        FROM Payment p
        WHERE p.status = 'COMPLETED'
          AND p.createdAt >= :startDate
          AND p.createdAt < :endDate
        """)
    Long countByPeriod(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    /**
     * 특정 기간의 REFUNDED 상태 결제 건수 조회
     */
    @Query("""
        SELECT COUNT(p)
        FROM Payment p
        WHERE p.status = 'REFUNDED'
          AND p.createdAt >= :startDate
          AND p.createdAt < :endDate
        """)
    Long countRefundsByPeriod(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    /**
     * 일자별 매출 집계 (COMPLETED만)
     */
    @Query("""
        SELECT 
            DATE(p.createdAt) as date,
            SUM(p.amount) as totalSales,
            COUNT(p) as paymentCount
        FROM Payment p
        WHERE p.status = 'COMPLETED'
          AND p.createdAt >= :startDate
          AND p.createdAt < :endDate
        GROUP BY DATE(p.createdAt)
        ORDER BY DATE(p.createdAt)
        """)
    List<Object[]> getDailySalesData(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    /**
     * 일자별 환불 건수 집계
     */
    @Query("""
        SELECT 
            DATE(p.createdAt) as date,
            COUNT(p) as refundCount
        FROM Payment p
        WHERE p.status = 'REFUNDED'
          AND p.createdAt >= :startDate
          AND p.createdAt < :endDate
        GROUP BY DATE(p.createdAt)
        ORDER BY DATE(p.createdAt)
        """)
    List<Object[]> getDailyRefundData(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    /*커스텀 보안 강좌 접근 권한*/
    /**
     * 커스텀 보안 강좌 접근 권한 - COMPLETED 상태만 체크
     */
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END " +
            "FROM Payment p " +
            "WHERE p.course.id = :courseId " +
            "AND p.user.email = :email " +
            "AND p.status = 'COMPLETED'")
    boolean existsByCourseIdAndStudentEmail(@Param("courseId") Long courseId,
                                            @Param("email") String email);
}
