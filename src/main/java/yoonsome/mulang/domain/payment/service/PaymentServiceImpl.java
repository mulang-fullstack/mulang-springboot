package yoonsome.mulang.domain.payment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import yoonsome.mulang.domain.course.entity.Course;
import yoonsome.mulang.domain.course.repository.CourseRepository;
import yoonsome.mulang.domain.enrollment.entity.Enrollment;
import yoonsome.mulang.domain.enrollment.repository.EnrollmentRepository;
import yoonsome.mulang.domain.payment.dto.*;
import yoonsome.mulang.domain.payment.entity.*;
import yoonsome.mulang.domain.payment.repository.PaymentRepository;
import yoonsome.mulang.domain.user.entity.User;
import yoonsome.mulang.domain.user.repository.UserRepository;

import java.nio.charset.StandardCharsets;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.Base64;

/**
 * 결제 서비스 구현체
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${toss.payments.secret-key}")
    private String tossSecretKey;

    @Value("${toss.payments.client-key}")
    private String tossClientKey;

    private static final String TOSS_PAYMENT_URL = "https://api.tosspayments.com/v1/payments/confirm";

    /**
     * 결제 준비
     */
    @Override
    @Transactional
    public PaymentResponseDto preparePayment(Long userId, PaymentRequestDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        Course course = courseRepository.findById(requestDto.getCourseId())
                .orElseThrow(() -> new IllegalArgumentException("강좌를 찾을 수 없습니다."));

        if (enrollmentRepository.existsByUserIdAndCourseId(userId, requestDto.getCourseId())) {
            throw new IllegalStateException("이미 구매한 강좌입니다.");
        }
        if (!course.getPrice().equals(requestDto.getAmount())) {
            throw new IllegalArgumentException("결제 금액이 일치하지 않습니다.");
        }

        String orderId = "ORDER_" + UUID.randomUUID();
        Payment payment = new Payment();
        payment.setUser(user);
        payment.setCourse(course);
        payment.setOrderId(orderId);
        payment.setAmount(requestDto.getAmount());
        payment.setStatus(PaymentStatus.PENDING);

        Payment savedPayment = paymentRepository.save(payment);
        log.info("결제 준비 완료 - OrderId: {}, UserId: {}, CourseId: {}", orderId, userId, requestDto.getCourseId());

        return PaymentResponseDto.builder()
                .paymentId(savedPayment.getId())
                .orderId(orderId)
                .amount(requestDto.getAmount())
                .status(PaymentStatus.PENDING.name())
                .orderName(requestDto.getOrderName())
                .build();
    }

    /**
     * 결제 승인
     */
    @Override
    @Transactional
    public PaymentResponseDto confirmPayment(PaymentConfirmDto confirmDto) {
        try {
            Payment payment = paymentRepository.findByOrderId(confirmDto.getOrderId())
                    .orElseThrow(() -> new IllegalArgumentException("주문 정보를 찾을 수 없습니다."));

            if (!payment.getAmount().equals(confirmDto.getAmount())) {
                throw new IllegalArgumentException("결제 금액이 일치하지 않습니다.");
            }

            TossPaymentResponseDto tossResponse = requestTossPaymentConfirm(confirmDto);

            payment.setPaymentKey(tossResponse.getPaymentKey());
            payment.setStatus(PaymentStatus.COMPLETED);
            payment.setPaymentMethod(convertToPaymentMethod(tossResponse.getMethod()));

            if (tossResponse.getApprovedAt() != null) {
                try {
                    LocalDateTime approvedAt = ZonedDateTime.parse(
                            tossResponse.getApprovedAt(), DateTimeFormatter.ISO_OFFSET_DATE_TIME
                    ).toLocalDateTime();
                    payment.setApprovedAt(approvedAt);
                } catch (Exception e) {
                    payment.setApprovedAt(LocalDateTime.now());
                }
            }

            if (tossResponse.getCard() != null) {
                payment.setPaymentMethodDetail(tossResponse.getCard().getCompany());
            } else if (tossResponse.getVirtualAccount() != null) {
                payment.setPaymentMethodDetail(tossResponse.getVirtualAccount().getBankCode());
            }

            paymentRepository.save(payment);
            createEnrollment(payment);

            return PaymentResponseDto.from(payment);
        } catch (Exception e) {
            paymentRepository.findByOrderId(confirmDto.getOrderId())
                    .ifPresent(p -> {
                        p.setStatus(PaymentStatus.FAILED);
                        p.setFailureMessage(e.getMessage());
                        paymentRepository.save(p);
                    });
            throw new RuntimeException("결제 승인 실패: " + e.getMessage(), e);
        }
    }

    /**
     * 결제 실패 처리
     */
    @Override
    @Transactional
    public void handlePaymentFailure(String orderId, String failureCode, String failureMessage) {
        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문 정보를 찾을 수 없습니다."));
        payment.setStatus(PaymentStatus.FAILED);
        payment.setFailureCode(failureCode);
        payment.setFailureMessage(failureMessage);
        paymentRepository.save(payment);
    }

    /**
     * 결제 단건 조회
     */
    @Override
    public PaymentResponseDto getPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("결제 정보를 찾을 수 없습니다."));
        return PaymentResponseDto.from(payment);
    }

    /**
     * 결제 검색 (관리자용)
     */
    @Override
    public Page<PaymentResponseDto> searchPayments(PaymentSearchRequest request) {
        Sort sort = Sort.by(Sort.Direction.fromString(request.getSortDirection()), request.getSortBy());
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), sort);

        Page<Payment> page = paymentRepository.searchPayments(
                request.getType(),
                request.getStatus(),
                request.getKeyword(),
                request.getStartDate(),
                request.getEndDate(),
                pageable
        );

        return page.map(PaymentResponseDto::from);
    }

    /**
     * 특정 강좌의 결제 내역 전체 조회
     */
    @Override
    public List<PaymentResponseDto> getPaymentsByCourseId(Long courseId) {
        return paymentRepository.findByCourseId(courseId)
                .stream()
                .map(PaymentResponseDto::from)
                .collect(Collectors.toList());
    }

    // ============================================================ 내부 메서드 ============================================================

    private PaymentMethod convertToPaymentMethod(String method) {
        if (method == null) return PaymentMethod.CARD;
        switch (method.toUpperCase()) {
            case "CARD": return PaymentMethod.CARD;
            case "VIRTUAL_ACCOUNT": return PaymentMethod.VIRTUAL_ACCOUNT;
            case "TRANSFER": return PaymentMethod.TRANSFER;
            case "MOBILE_PHONE": return PaymentMethod.MOBILE_PHONE;
            case "CULTURE_GIFT_CARD": return PaymentMethod.CULTURE_GIFT_CARD;
            case "BOOK_CULTURE_GIFT_CARD": return PaymentMethod.BOOK_CULTURE_GIFT_CARD;
            case "GAME_CULTURE_GIFT_CARD": return PaymentMethod.GAME_CULTURE_GIFT_CARD;
            default: return PaymentMethod.CARD;
        }
    }

    private TossPaymentResponseDto requestTossPaymentConfirm(PaymentConfirmDto dto) {
        try {
            String encodedAuth = Base64.getEncoder()
                    .encodeToString((tossSecretKey + ":").getBytes(StandardCharsets.UTF_8));

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Basic " + encodedAuth);
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<PaymentConfirmDto> request = new HttpEntity<>(dto, headers);

            ResponseEntity<TossPaymentResponseDto> response = restTemplate.exchange(
                    TOSS_PAYMENT_URL, HttpMethod.POST, request, TossPaymentResponseDto.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null)
                return response.getBody();
            throw new RuntimeException("토스 API 응답 오류");
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("토스 API 호출 실패: " + e.getResponseBodyAsString());
        }
    }

    private void createEnrollment(Payment payment) {
        Enrollment enrollment = new Enrollment();
        enrollment.setUser(payment.getUser());
        enrollment.setCourse(payment.getCourse());
        enrollment.setPayment(payment);
        enrollment.setProgress(0);
        enrollment.setIsCompleted(false);
        enrollmentRepository.save(enrollment);
    }
}
