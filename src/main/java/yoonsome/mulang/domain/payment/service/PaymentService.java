package yoonsome.mulang.domain.payment.service;

import org.springframework.data.domain.Page;
import yoonsome.mulang.api.admin.payment.dto.PaymentSearchRequest;
import yoonsome.mulang.api.payments.dto.*;
import yoonsome.mulang.domain.course.entity.Course;
import yoonsome.mulang.domain.payment.entity.Payment;
import yoonsome.mulang.domain.user.entity.User;

import java.util.List;

/**
 * 결제 서비스 인터페이스
 * 순수 결제 로직만 담당
 */
public interface PaymentService {

    /** 결제 준비 (Payment 엔티티 생성) */
    Payment createPendingPayment(User user, Course course, String orderId);

    /** 결제 승인 (토스 API 호출 + Payment 업데이트) */
    Payment confirmPayment(PaymentConfirmRequest request);

    /** 결제 실패 처리 */
    Payment markAsFailed(String orderId, String failureCode, String failureMessage);

    /** 결제 단건 조회 */
    Payment getPaymentById(Long paymentId);

    /** orderId로 결제 조회 */
    Payment getPaymentByOrderId(String orderId);

    /** 결제 검색 (관리자용) */
    Page<PaymentDetailResponse> searchPayments(PaymentSearchRequest request);

    /** 특정 강좌에 대한 결제 내역 조회 */
    List<PaymentDetailResponse> getPaymentsByCourseId(Long courseId);

    /** 특정 사용자의 결제 내역 조회 */
    List<PaymentDetailResponse> getPaymentsByUserId(Long userId);
}
