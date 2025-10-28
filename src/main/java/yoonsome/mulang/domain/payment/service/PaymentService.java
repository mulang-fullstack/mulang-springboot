package yoonsome.mulang.domain.payment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import yoonsome.mulang.domain.course.entity.Course;
import yoonsome.mulang.domain.course.repository.CourseRepository;
import yoonsome.mulang.domain.enrollment.entity.Enrollment;
import yoonsome.mulang.domain.enrollment.repository.EnrollmentRepository;
import yoonsome.mulang.domain.payment.dto.*;
import yoonsome.mulang.domain.payment.entity.Payment;
import yoonsome.mulang.domain.payment.entity.PaymentMethod;
import yoonsome.mulang.domain.payment.entity.PaymentStatus;
import yoonsome.mulang.domain.payment.repository.PaymentRepository;
import yoonsome.mulang.domain.user.entity.User;
import yoonsome.mulang.domain.user.repository.UserRepository;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class PaymentService {

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
     * 결제 준비 - 주문 ID 생성 및 초기 Payment 엔티티 저장
     */
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

        String orderId = "ORDER_" + UUID.randomUUID().toString();

        Payment payment = new Payment();
        payment.setUser(user);
        payment.setCourse(course);
        payment.setOrderId(orderId);
        payment.setAmount(requestDto.getAmount());
        payment.setStatus(PaymentStatus.PENDING);

        Payment savedPayment = paymentRepository.save(payment);

        log.info("결제 준비 완료 - OrderId: {}, UserId: {}, CourseId: {}",
                orderId, userId, requestDto.getCourseId());

        return PaymentResponseDto.builder()
                .paymentId(savedPayment.getId())
                .orderId(orderId)
                .amount(requestDto.getAmount())
                .status(PaymentStatus.PENDING.name())
                .orderName(requestDto.getOrderName())
                .build();
    }

    /**
     * 토스 페이먼츠 결제 승인
     */
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

            PaymentMethod paymentMethod = convertToPaymentMethod(tossResponse.getMethod());
            payment.setPaymentMethod(paymentMethod);

            if (tossResponse.getApprovedAt() != null) {
                try {
                    LocalDateTime approvedAt = ZonedDateTime.parse(
                            tossResponse.getApprovedAt(),
                            DateTimeFormatter.ISO_OFFSET_DATE_TIME
                    ).toLocalDateTime();
                    payment.setApprovedAt(approvedAt);
                } catch (Exception e) {
                    log.warn("승인 시간 파싱 실패, 현재 시간으로 설정: {}", e.getMessage());
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

            log.info("결제 승인 완료 - PaymentKey: {}, OrderId: {}",
                    tossResponse.getPaymentKey(), confirmDto.getOrderId());

            return PaymentResponseDto.builder()
                    .paymentId(payment.getId())
                    .orderId(payment.getOrderId())
                    .paymentKey(payment.getPaymentKey())
                    .amount(payment.getAmount())
                    .status(payment.getStatus().name())
                    .orderName(tossResponse.getOrderName())
                    .approvedAt(tossResponse.getApprovedAt())
                    .paymentMethod(payment.getPaymentMethod().name())
                    .paymentMethodDetail(payment.getPaymentMethodDetail())
                    .build();

        } catch (Exception e) {
            log.error("결제 승인 실패 - OrderId: {}, Error: {}",
                    confirmDto.getOrderId(), e.getMessage());

            paymentRepository.findByOrderId(confirmDto.getOrderId())
                    .ifPresent(payment -> {
                        payment.setStatus(PaymentStatus.FAILED);
                        payment.setFailureMessage(e.getMessage());
                        paymentRepository.save(payment);
                    });

            throw new RuntimeException("결제 승인에 실패했습니다: " + e.getMessage(), e);
        }
    }

    private PaymentMethod convertToPaymentMethod(String method) {
        if (method == null) {
            return PaymentMethod.CARD;
        }

        switch (method.toUpperCase()) {
            case "카드":
            case "CARD":
                return PaymentMethod.CARD;
            case "가상계좌":
            case "VIRTUAL_ACCOUNT":
                return PaymentMethod.VIRTUAL_ACCOUNT;
            case "계좌이체":
            case "TRANSFER":
                return PaymentMethod.TRANSFER;
            case "휴대폰":
            case "MOBILE_PHONE":
            case "휴대폰결제":
                return PaymentMethod.MOBILE_PHONE;
            case "문화상품권":
            case "CULTURE_GIFT_CARD":
                return PaymentMethod.CULTURE_GIFT_CARD;
            case "도서문화상품권":
            case "BOOK_CULTURE_GIFT_CARD":
                return PaymentMethod.BOOK_CULTURE_GIFT_CARD;
            case "게임문화상품권":
            case "GAME_CULTURE_GIFT_CARD":
                return PaymentMethod.GAME_CULTURE_GIFT_CARD;
            default:
                log.warn("알 수 없는 결제 수단: {}, CARD로 설정", method);
                return PaymentMethod.CARD;
        }
    }

    private TossPaymentResponseDto requestTossPaymentConfirm(PaymentConfirmDto confirmDto) {
        try {
            log.info("=== 토스 페이먼츠 API 호출 시작 ===");
            log.info("🔑 Secret Key 존재 여부: {}", tossSecretKey != null && !tossSecretKey.isEmpty());
            log.info("📦 결제 승인 요청:");
            log.info("  - paymentKey: {}", confirmDto.getPaymentKey());
            log.info("  - orderId: {}", confirmDto.getOrderId());
            log.info("  - amount: {}", confirmDto.getAmount());

            String auth = tossSecretKey + ":";
            String encodedAuth = Base64.getEncoder()
                    .encodeToString(auth.getBytes(StandardCharsets.UTF_8));

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Basic " + encodedAuth);
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<PaymentConfirmDto> request = new HttpEntity<>(confirmDto, headers);

            log.info("🌐 API URL: {}", TOSS_PAYMENT_URL);

            ResponseEntity<TossPaymentResponseDto> response = restTemplate.exchange(
                    TOSS_PAYMENT_URL,
                    HttpMethod.POST,
                    request,
                    TossPaymentResponseDto.class
            );

            log.info("✅ 토스 API 응답 성공: {}", response.getStatusCode());

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return response.getBody();
            } else {
                throw new RuntimeException("토스 페이먼츠 API 응답이 올바르지 않습니다.");
            }

        } catch (HttpClientErrorException e) {
            log.error("❌ 토스 API 에러:");
            log.error("  Status: {}", e.getStatusCode());
            log.error("  Body: {}", e.getResponseBodyAsString());

            String errorMessage = parseErrorMessage(e.getResponseBodyAsString());
            throw new RuntimeException("토스 페이먼츠 API 에러: " + errorMessage, e);

        } catch (Exception e) {
            log.error("❌ 예외: {}", e.getClass().getName());
            log.error("❌ 메시지: {}", e.getMessage());
            throw new RuntimeException("토스 페이먼츠 결제 승인 실패: " + e.getMessage(), e);
        }
    }

    private String parseErrorMessage(String errorBody) {
        try {
            var errorNode = objectMapper.readTree(errorBody);
            if (errorNode.has("message")) {
                return errorNode.get("message").asText();
            }
            return errorBody;
        } catch (Exception e) {
            return errorBody;
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

        log.info("강좌 등록 완료 - UserId: {}, CourseId: {}",
                payment.getUser().getId(), payment.getCourse().getId());
    }

    @Transactional
    public void handlePaymentFailure(String orderId, String failureCode, String failureMessage) {
        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문 정보를 찾을 수 없습니다."));

        payment.setStatus(PaymentStatus.FAILED);
        payment.setFailureCode(failureCode);
        payment.setFailureMessage(failureMessage);

        paymentRepository.save(payment);

        log.info("결제 실패 처리 완료 - OrderId: {}, Code: {}, Message: {}",
                orderId, failureCode, failureMessage);
    }

    public PaymentResponseDto getPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("결제 정보를 찾을 수 없습니다."));

        return convertToDto(payment);
    }

    private PaymentResponseDto convertToDto(Payment payment) {
        return PaymentResponseDto.builder()
                .paymentId(payment.getId())
                .orderId(payment.getOrderId())
                .paymentKey(payment.getPaymentKey())
                .amount(payment.getAmount())
                .status(payment.getStatus().name())
                .approvedAt(payment.getApprovedAt() != null ?
                        payment.getApprovedAt().toString() : null)
                .paymentMethod(payment.getPaymentMethod() != null ?
                        payment.getPaymentMethod().name() : null)
                .paymentMethodDetail(payment.getPaymentMethodDetail())
                .build();
    }
}