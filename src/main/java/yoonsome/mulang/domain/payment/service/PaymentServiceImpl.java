package yoonsome.mulang.domain.payment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import yoonsome.mulang.api.admin.payment.dto.PaymentSearchRequest;
import yoonsome.mulang.api.payments.dto.*;
import yoonsome.mulang.domain.course.entity.Course;
import yoonsome.mulang.domain.payment.entity.*;
import yoonsome.mulang.domain.payment.repository.PaymentRepository;
import yoonsome.mulang.domain.user.entity.User;

import java.nio.charset.StandardCharsets;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 결제 서비스 구현체
 * 순수 결제 로직만 처리
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final RestTemplate restTemplate;

    @Value("${toss.payments.secret-key}")
    private String tossSecretKey;

    private static final String TOSS_PAYMENT_URL = "https://api.tosspayments.com/v1/payments/confirm";

    /**
     * 결제 준비 (Payment 엔티티 생성)
     */
    @Override
    @Transactional
    public Payment createPendingPayment(User user, Course course, String orderId) {
        Payment payment = Payment.builder()
                .user(user)
                .course(course)
                .orderId(orderId)
                .amount(course.getPrice())
                .status(PaymentStatus.PENDING)
                .build();

        Payment saved = paymentRepository.save(payment);
        log.info("✅ 결제 준비 완료 - OrderId: {}, UserId: {}, CourseId: {}",
                orderId, user.getId(), course.getId());

        return saved;
    }

    /**
     * 결제 승인
     */
    @Override
    @Transactional
    public Payment confirmPayment(PaymentConfirmRequest request) {
        try {
            // 1. Payment 조회
            Payment payment = paymentRepository.findByOrderId(request.getOrderId())
                    .orElseThrow(() -> new IllegalArgumentException("주문 정보를 찾을 수 없습니다."));

            // 2. 금액 검증
            if (!payment.getAmount().equals(request.getAmount())) {
                throw new IllegalArgumentException("결제 금액이 일치하지 않습니다.");
            }

            // 3. 토스 API 호출
            TossPaymentResponse tossResponse = callTossApi(request);

            // 4. Payment 업데이트
            payment.setPaymentKey(tossResponse.getPaymentKey());
            payment.setStatus(PaymentStatus.COMPLETED);
            payment.setPaymentMethod(convertToPaymentMethod(tossResponse.getMethod()));
            payment.setApprovedAt(parseApprovedAt(tossResponse.getApprovedAt()));

            // 결제 수단 상세 정보
            if (tossResponse.getCard() != null) {
                payment.setPaymentMethodDetail(tossResponse.getCard().getCompany());
            } else if (tossResponse.getVirtualAccount() != null) {
                payment.setPaymentMethodDetail(tossResponse.getVirtualAccount().getBankCode());
            }

            Payment saved = paymentRepository.save(payment);
            log.info("✅ 결제 승인 완료 - PaymentKey: {}, OrderId: {}",
                    tossResponse.getPaymentKey(), request.getOrderId());

            return saved;

        } catch (Exception e) {
            // 실패 시 Payment 상태 업데이트
            paymentRepository.findByOrderId(request.getOrderId())
                    .ifPresent(p -> {
                        p.setStatus(PaymentStatus.FAILED);
                        p.setFailureMessage(e.getMessage());
                        paymentRepository.save(p);
                    });

            log.error("❌ 결제 승인 실패 - OrderId: {}, Error: {}",
                    request.getOrderId(), e.getMessage());
            throw new RuntimeException("결제 승인 실패: " + e.getMessage(), e);
        }
    }

    /**
     * 결제 실패 처리
     */
    @Override
    @Transactional
    public Payment markAsFailed(String orderId, String failureCode, String failureMessage) {
        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문 정보를 찾을 수 없습니다."));

        payment.setStatus(PaymentStatus.FAILED);
        payment.setFailureCode(failureCode);
        payment.setFailureMessage(failureMessage);

        Payment saved = paymentRepository.save(payment);
        log.info("❌ 결제 실패 처리 - OrderId: {}, Code: {}", orderId, failureCode);

        return saved;
    }

    /**
     * 결제 단건 조회
     */
    @Override
    public Payment getPaymentById(Long paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("결제 정보를 찾을 수 없습니다."));
    }

    /**
     * orderId로 결제 조회
     */
    @Override
    public Payment getPaymentByOrderId(String orderId) {
        return paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문 정보를 찾을 수 없습니다."));
    }

    /**
     * 결제 검색 (관리자용)
     */
    @Override
    public Page<PaymentDetailResponse> searchPayments(PaymentSearchRequest request) {
        Sort sort = Sort.by(
                Sort.Direction.fromString(request.getSortDirection()),
                request.getSortBy()
        );
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), sort);

        Page<Payment> page = paymentRepository.searchPayments(
                request.getType(),
                request.getStatus(),
                request.getKeyword(),
                request.getStartDate(),
                request.getEndDate(),
                pageable
        );

        return page.map(this::toDetailResponse);
    }

    /**
     * 특정 강좌의 결제 내역 조회
     */
    @Override
    public List<PaymentDetailResponse> getPaymentsByCourseId(Long courseId) {
        return paymentRepository.findByCourseId(courseId)
                .stream()
                .map(this::toDetailResponse)
                .collect(Collectors.toList());
    }

    /**
     * 특정 사용자의 결제 내역 조회
     */
    @Override
    public List<PaymentDetailResponse> getPaymentsByUserId(Long userId) {
        return paymentRepository.findByUserId(userId)
                .stream()
                .map(this::toDetailResponse)
                .collect(Collectors.toList());
    }

    // ==================== Private Methods ====================

    /**
     * 토스 API 호출
     */
    private TossPaymentResponse callTossApi(PaymentConfirmRequest dto) {
        try {
            String encodedAuth = Base64.getEncoder()
                    .encodeToString((tossSecretKey + ":").getBytes(StandardCharsets.UTF_8));

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Basic " + encodedAuth);
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<PaymentConfirmRequest> request = new HttpEntity<>(dto, headers);

            ResponseEntity<TossPaymentResponse> response = restTemplate.exchange(
                    TOSS_PAYMENT_URL,
                    HttpMethod.POST,
                    request,
                    TossPaymentResponse.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return response.getBody();
            }

            throw new RuntimeException("토스 API 응답 오류");

        } catch (HttpClientErrorException e) {
            log.error("토스 API 호출 실패: {}", e.getResponseBodyAsString());
            throw new RuntimeException("토스 API 호출 실패: " + e.getResponseBodyAsString());
        }
    }

    /**
     * PaymentMethod 변환
     */
    private PaymentMethod convertToPaymentMethod(String method) {
        if (method == null) return PaymentMethod.CARD;

        return switch (method.toUpperCase()) {
            case "CARD" -> PaymentMethod.CARD;
            case "KAKAOPAY" -> PaymentMethod.KAKAOPAY;
            case "TOSSPAY" -> PaymentMethod.TOSSPAY;
            case "MOBILE_PHONE" -> PaymentMethod.MOBILE_PHONE;
            default -> PaymentMethod.CARD;
        };
    }

    /**
     * 승인일시 파싱
     */
    private LocalDateTime parseApprovedAt(String approvedAt) {
        if (approvedAt == null) {
            return LocalDateTime.now();
        }

        try {
            return ZonedDateTime.parse(
                    approvedAt,
                    DateTimeFormatter.ISO_OFFSET_DATE_TIME
            ).toLocalDateTime();
        } catch (Exception e) {
            log.warn("승인일시 파싱 실패, 현재 시간 사용: {}", approvedAt);
            return LocalDateTime.now();
        }
    }

    /**
     * Payment → PaymentDetailResponse 변환
     */
    private PaymentDetailResponse toDetailResponse(Payment payment) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return PaymentDetailResponse.builder()
                .paymentId(payment.getId())
                .orderId(payment.getOrderId())
                .paymentKey(payment.getPaymentKey())
                .amount(payment.getAmount())
                .status(payment.getStatus().name())
                .user(PaymentDetailResponse.UserSummary.builder()
                        .id(payment.getUser().getId())
                        .username(payment.getUser().getUsername())
                        .email(payment.getUser().getEmail())
                        .nickname(payment.getUser().getNickname())
                        .build())
                .course(PaymentDetailResponse.CourseSummary.builder()
                        .id(payment.getCourse().getId())
                        .title(payment.getCourse().getTitle())
                        .teacherName(payment.getCourse().getTeacher().getUser().getUsername())
                        .price(payment.getCourse().getPrice())
                        .build())
                .paymentMethod(payment.getPaymentMethod() != null ?
                        payment.getPaymentMethod().name() : null)
                .paymentMethodDetail(payment.getPaymentMethodDetail())
                .approvedAt(payment.getApprovedAt() != null ?
                        payment.getApprovedAt().format(formatter) : null)
                .failureMessage(payment.getFailureMessage())
                .build();
    }
}
