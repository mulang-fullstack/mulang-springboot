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
        // 사용자 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 강좌 조회
        Course course = courseRepository.findById(requestDto.getCourseId())
                .orElseThrow(() -> new IllegalArgumentException("강좌를 찾을 수 없습니다."));

        // 이미 구매한 강좌인지 확인
        if (enrollmentRepository.existsByUserIdAndCourseId(userId, requestDto.getCourseId())) {
            throw new IllegalStateException("이미 구매한 강좌입니다.");
        }

        // 금액 검증
        if (!course.getPrice().equals(requestDto.getAmount())) {
            throw new IllegalArgumentException("결제 금액이 일치하지 않습니다.");
        }

        // 주문 ID 생성 (UUID 사용)
        String orderId = "ORDER_" + UUID.randomUUID().toString();

        // Payment 엔티티 생성 및 저장
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
            // 주문 조회
            Payment payment = paymentRepository.findByOrderId(confirmDto.getOrderId())
                    .orElseThrow(() -> new IllegalArgumentException("주문 정보를 찾을 수 없습니다."));

            // 금액 검증
            if (!payment.getAmount().equals(confirmDto.getAmount())) {
                throw new IllegalArgumentException("결제 금액이 일치하지 않습니다.");
            }

            // 토스 페이먼츠 API 호출
            TossPaymentResponseDto tossResponse = requestTossPaymentConfirm(confirmDto);

            // Payment 엔티티 업데이트
            payment.setPaymentKey(tossResponse.getPaymentKey());
            payment.setStatus(PaymentStatus.COMPLETED);
            payment.setPaymentMethod(PaymentMethod.valueOf(tossResponse.getMethod().toUpperCase()));
            payment.setApprovedAt(LocalDateTime.parse(
                    tossResponse.getApprovedAt(),
                    DateTimeFormatter.ISO_OFFSET_DATE_TIME
            ));

            // 결제 수단 상세 정보 저장
            if (tossResponse.getCard() != null) {
                payment.setPaymentMethodDetail(tossResponse.getCard().getCompany());
            } else if (tossResponse.getVirtualAccount() != null) {
                payment.setPaymentMethodDetail(tossResponse.getVirtualAccount().getBankCode());
            }

            paymentRepository.save(payment);

            // Enrollment 생성 (강좌 등록)
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

            // 결제 실패 처리
            paymentRepository.findByOrderId(confirmDto.getOrderId())
                    .ifPresent(payment -> {
                        payment.setStatus(PaymentStatus.FAILED);
                        payment.setFailureMessage(e.getMessage());
                        paymentRepository.save(payment);
                    });

            throw new RuntimeException("결제 승인에 실패했습니다: " + e.getMessage(), e);
        }
    }

    /**
     * 토스 페이먼츠 API 호출
     */
    private TossPaymentResponseDto requestTossPaymentConfirm(PaymentConfirmDto confirmDto) {
        try {
            // 시크릿 키 로깅 (디버깅용)
            log.info("🔑 Secret Key: {}", tossSecretKey);
            log.info("📦 결제 승인 요청 데이터:");
            log.info("  - paymentKey: {}", confirmDto.getPaymentKey());
            log.info("  - orderId: {}", confirmDto.getOrderId());
            log.info("  - amount: {}", confirmDto.getAmount());

            // Base64 인코딩
            String auth = tossSecretKey + ":";
            String encodedAuth = Base64.getEncoder()
                    .encodeToString(auth.getBytes(StandardCharsets.UTF_8));

            log.info("🔐 Authorization: Basic {}", encodedAuth.substring(0, 20) + "...");

            // HTTP 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Basic " + encodedAuth);
            headers.setContentType(MediaType.APPLICATION_JSON);

            // 요청 바디를 JSON으로 변환해서 로깅
            String requestBody = objectMapper.writeValueAsString(confirmDto);
            log.info("📤 Request Body: {}", requestBody);

            // 요청 바디 설정
            HttpEntity<PaymentConfirmDto> request = new HttpEntity<>(confirmDto, headers);

            log.info("🌐 토스 API 호출: {}", TOSS_PAYMENT_URL);

            // API 호출
            ResponseEntity<String> response = restTemplate.exchange(
                    TOSS_PAYMENT_URL,
                    HttpMethod.POST,
                    request,
                    String.class  // 일단 String으로 받아서 로그 출력
            );

            log.info("✅ 토스 API 응답:");
            log.info("  - Status: {}", response.getStatusCode());
            log.info("  - Body: {}", response.getBody());

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                // JSON을 DTO로 변환
                TossPaymentResponseDto tossResponse = objectMapper.readValue(
                        response.getBody(),
                        TossPaymentResponseDto.class
                );
                return tossResponse;
            } else {
                throw new RuntimeException("토스 페이먼츠 API 응답이 올바르지 않습니다.");
            }

        } catch (HttpClientErrorException e) {
            // 토스 API의 상세 에러 응답 로깅
            log.error("❌ 토스 API 에러 응답:");
            log.error("  Status Code: {}", e.getStatusCode());
            log.error("  Response Body: {}", e.getResponseBodyAsString());

            throw new RuntimeException("토스 페이먼츠 API 에러: " + e.getResponseBodyAsString(), e);

        } catch (Exception e) {
            log.error("❌ 예외 타입: {}", e.getClass().getName());
            log.error("❌ 예외 메시지: {}", e.getMessage());
            log.error("❌ 스택 트레이스:", e);

            throw new RuntimeException("토스 페이먼츠 결제 승인에 실패했습니다: " + e.getMessage(), e);
        }
    }

    /**
     * 강좌 등록 (Enrollment) 생성
     */
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

    /**
     * 결제 실패 처리
     */
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

    /**
     * 사용자의 결제 내역 조회
     */
    public PaymentResponseDto getPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("결제 정보를 찾을 수 없습니다."));

        return convertToDto(payment);
    }

    /**
     * Payment 엔티티를 DTO로 변환
     */
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
