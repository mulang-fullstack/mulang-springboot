package yoonsome.mulang.domain.payment.service;

import org.springframework.data.domain.Page;
import yoonsome.mulang.api.admin.payment.dto.PaymentSearchRequest;
import yoonsome.mulang.domain.payment.dto.*;
import java.util.List;

/**
 * 결제 서비스 인터페이스
 * 결제 준비, 승인, 실패 처리, 검색, 조회 기능을 정의한다.
 */
public interface PaymentService {

    /** 결제 준비 */
    PaymentResponseDto preparePayment(Long userId, PaymentRequestDto requestDto);

    /** 결제 승인 */
    PaymentResponseDto confirmPayment(PaymentConfirmDto confirmDto);

    /** 결제 실패 처리 */
    void handlePaymentFailure(String orderId, String failureCode, String failureMessage);

    /** 결제 단건 조회 */
    PaymentResponseDto getPayment(Long paymentId);

    /** 결제 검색 (관리자용) */
    Page<PaymentResponseDto> searchPayments(PaymentSearchRequest request);

    /** 특정 강좌에 대한 결제 내역 전체 조회 */
    List<PaymentResponseDto> getPaymentsByCourseId(Long courseId);
}
