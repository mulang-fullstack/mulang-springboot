package yoonsome.mulang.domain.payment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yoonsome.mulang.api.payments.dto.*;
import yoonsome.mulang.domain.course.entity.Course;
import yoonsome.mulang.domain.course.repository.CourseRepository;
import yoonsome.mulang.domain.coursefavorite.repository.CourseFavoriteRepository;
import yoonsome.mulang.domain.enrollment.service.EnrollmentService;
import yoonsome.mulang.domain.payment.entity.Payment;
import yoonsome.mulang.domain.user.entity.User;
import yoonsome.mulang.domain.user.repository.UserRepository;
import yoonsome.mulang.infra.file.service.S3FileService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * 결제 파사드 서비스
 * 결제 전체 흐름 조율 (결제 + 수강신청)
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentFacadeService {

    private final PaymentService paymentService;
    private final EnrollmentService enrollmentService;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final S3FileService s3FileService;
    private final CourseFavoriteRepository courseFavoriteRepository;

    /**
     * 1. 결제 페이지 준비
     */
    @Transactional
    public PaymentPageResponse preparePaymentPage(Long courseId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("강좌를 찾을 수 없습니다."));

        // 중복 구매 체크
        if (enrollmentService.hasEnrollment(userId, courseId)) {
            throw new IllegalStateException("이미 구매한 강좌입니다.");
        }

        // 주문번호 생성 및 Payment 임시 저장
        String orderId = "ORDER_" + UUID.randomUUID();
        paymentService.createPendingPayment(user, course, orderId);

        // 썸네일 URL 생성 (Service에서 처리)
        String thumbnailUrl = null;
        if (course.getFile() != null) {
            thumbnailUrl = s3FileService.getPublicUrl(course.getFile().getId());
        }

        return PaymentPageResponse.builder()
                .orderId(orderId)
                .username(user.getUsername())
                .amount(course.getPrice())
                .courseId(course.getId())
                .courseTitle(course.getTitle())
                .thumbnailUrl(thumbnailUrl)
                .teacherNickname(course.getTeacher().getUser().getNickname())
                .lectureCount(course.getLectureCount())
                .averageRating(course.getAverageRating())
                .reviewCount(course.getReviewCount())
                .build();
    }

    /**
     * 2. 결제 승인 + 수강신청
     */
    @Transactional
    public PaymentSuccessResponse confirmPayment(PaymentConfirmRequest request) {
        // 결제 승인
        Payment payment = paymentService.confirmPayment(request);

        // 수강신청 생성
        enrollmentService.createEnrollment(payment);

        // payment 엔티티에서 유저 아이디와 강좌 아이디를 가지고 찜 서비스에 있는 메서드를 활용해서 각 파라미로 넣고 찜 해제
        if(courseFavoriteRepository.
                existsByStudentIdAndCourseId(payment.getUser().getId(), payment.getCourse().getId())) {
            courseFavoriteRepository.
                    deleteByStudentIdAndCourseId(payment.getUser().getId(), payment.getCourse().getId());
        }
        return PaymentSuccessResponse.builder()
                .orderId(payment.getOrderId())
                .amount(payment.getAmount())
                .status("COMPLETED")
                .paymentMethod(payment.getPaymentMethod().name())
                .paymentMethodDetail(payment.getPaymentMethodDetail())
                .approvedAt(formatDateTime(payment.getApprovedAt()))
                .courseTitle(payment.getCourse().getTitle())
                .username(payment.getUser().getUsername())
                .email(payment.getUser().getEmail())
                .build();
    }

    /**
     * 3. 결제 실패 처리
     */
    @Transactional
    public PaymentFailResponse handlePaymentFailure(
            String orderId, String code, String message) {

        Payment payment = paymentService.markAsFailed(orderId, code, message);

        return PaymentFailResponse.builder()
                .orderId(orderId)
                .errorCode(code)
                .errorMessage(message)
                .failureReason(convertErrorMessage(code))
                .courseId(payment.getCourse().getId())
                .courseTitle(payment.getCourse().getTitle())
                .amount(payment.getAmount())
                .build();
    }

    // ==================== Private Methods ====================

    /**
     * LocalDateTime → String 포맷팅
     */
    private String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(formatter);
    }

    /**
     * LocalDateTime → 날짜만 포맷팅
     */
    private String formatDate(LocalDateTime dateTime) {
        if (dateTime == null) return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return dateTime.format(formatter);
    }

    /**
     * 에러 코드 → 사용자 친화적 메시지 변환
     */
    private String convertErrorMessage(String errorCode) {
        if (errorCode == null) return "알 수 없는 오류가 발생했습니다.";

        return switch (errorCode) {
            case "INVALID_CARD_NUMBER" -> "유효하지 않은 카드번호입니다.";
            case "INSUFFICIENT_FUNDS" -> "잔액이 부족합니다.";
            case "CARD_EXPIRED" -> "카드 유효기간이 만료되었습니다.";
            case "INVALID_CVC" -> "CVC 번호가 올바르지 않습니다.";
            case "EXCEED_MAX_CARD_INSTALLMENT_PLAN" -> "설정 가능한 최대 할부 개월수를 초과했습니다.";
            case "INVALID_CARD_INSTALLMENT_PLAN" -> "유효하지 않은 할부 개월수입니다.";
            case "NOT_SUPPORTED_INSTALLMENT_PLAN_CARD" -> "할부가 지원되지 않는 카드입니다.";
            case "NOT_SUPPORTED_MONTHLY_INSTALLMENT_PLAN" -> "할부가 지원되지 않는 카드입니다.";
            case "EXCEED_MAX_DAILY_PAYMENT_COUNT" -> "하루 결제 한도를 초과했습니다.";
            case "NOT_AVAILABLE_PAYMENT" -> "결제가 불가능한 카드입니다.";
            case "INCORRECT_BASIC_AUTH_FORMAT" -> "인증 정보가 올바르지 않습니다.";
            case "INVALID_API_KEY" -> "API 키가 유효하지 않습니다.";
            case "INVALID_REQUEST" -> "잘못된 요청입니다.";
            case "NOT_FOUND_PAYMENT" -> "결제 정보를 찾을 수 없습니다.";
            case "ALREADY_PROCESSED_PAYMENT" -> "이미 처리된 결제입니다.";
            case "PROVIDER_ERROR" -> "일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요.";
            default -> "결제 처리 중 오류가 발생했습니다.";
        };
    }
}
