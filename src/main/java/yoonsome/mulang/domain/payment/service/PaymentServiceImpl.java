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
import yoonsome.mulang.api.admin.dashboard.dto.SalesStatisticsResponse;
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

     * 특정 사용자의 결제 강좌ID 조회
     */
    @Override
    public Set<Long> getCourseIdsByUserId(Long userId){
        return paymentRepository.findCourseIdsByUserId(userId);
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

    /**
     * 매출 통계 조회 (대시보드용)
     */
    @Override
    public SalesStatisticsResponse getSalesStatistics() {
        LocalDateTime now = LocalDateTime.now();

        // 1. 이번 달 통계
        LocalDateTime thisMonthStart = now.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime thisMonthEnd = now.plusMonths(1).withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);

        Long thisMonthSales = paymentRepository.sumAmountByPeriod(thisMonthStart, thisMonthEnd);

        // 2. 지난 달 통계
        LocalDateTime lastMonthStart = thisMonthStart.minusMonths(1);
        LocalDateTime lastMonthEnd = thisMonthStart;

        Long lastMonthSales = paymentRepository.sumAmountByPeriod(lastMonthStart, lastMonthEnd);

        // 3. 월별 성장률 계산
        Double monthlyGrowthRate = calculateGrowthRate(lastMonthSales, thisMonthSales);

        // 4. 오늘 통계
        LocalDateTime todayStart = now.withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime todayEnd = now.plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);

        Long todaySales = paymentRepository.sumAmountByPeriod(todayStart, todayEnd);
        Long todayCount = paymentRepository.countByPeriod(todayStart, todayEnd);

        // 5. 어제 통계
        LocalDateTime yesterdayStart = todayStart.minusDays(1);
        LocalDateTime yesterdayEnd = todayStart;

        Long yesterdaySales = paymentRepository.sumAmountByPeriod(yesterdayStart, yesterdayEnd);
        Long yesterdayCount = paymentRepository.countByPeriod(yesterdayStart, yesterdayEnd);

        // 6. 일별 성장률
        Double dailySalesGrowthRate = calculateGrowthRate(yesterdaySales, todaySales);

        // 7. 환불 통계
        Long todayRefunds = paymentRepository.countRefundsByPeriod(todayStart, todayEnd);
        Long yesterdayRefunds = paymentRepository.countRefundsByPeriod(yesterdayStart, yesterdayEnd);
        Long refundDifference = todayRefunds - yesterdayRefunds;

        // 8. 최근 7일 차트/테이블 데이터
        LocalDateTime sevenDaysAgo = todayStart.minusDays(6);
        List<Object[]> salesData = paymentRepository.getDailySalesData(sevenDaysAgo, todayEnd);
        List<Object[]> refundData = paymentRepository.getDailyRefundData(sevenDaysAgo, todayEnd);

        // 환불 데이터를 Map으로 변환
        Map<LocalDate, Long> refundMap = new HashMap<>();
        for (Object[] row : refundData) {
            LocalDate date = ((java.sql.Date) row[0]).toLocalDate();
            Long count = ((Number) row[1]).longValue();
            refundMap.put(date, count);
        }

        // 일자별 데이터 생성
        List<SalesStatisticsResponse.DailySalesData> dailyDataList = new ArrayList<>();
        DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("MM/dd");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (Object[] row : salesData) {
            LocalDate date = ((java.sql.Date) row[0]).toLocalDate();
            Long sales = ((Number) row[1]).longValue();
            Long count = ((Number) row[2]).longValue();
            Long refunds = refundMap.getOrDefault(date, 0L);

            dailyDataList.add(SalesStatisticsResponse.DailySalesData.builder()
                    .date(date.format(dateFormatter))
                    .displayDate(date.format(displayFormatter))
                    .sales(sales)
                    .paymentCount(count)
                    .refundCount(refunds)
                    .build());
        }

        // 9. 응답 구성
        return SalesStatisticsResponse.builder()
                .monthlyStats(SalesStatisticsResponse.MonthlyStats.builder()
                        .totalSales(thisMonthSales)
                        .lastMonthSales(lastMonthSales)
                        .growthRate(monthlyGrowthRate)
                        .build())
                .todayStats(SalesStatisticsResponse.DailyStats.builder()
                        .totalSales(todaySales)
                        .paymentCount(todayCount)
                        .growthRate(dailySalesGrowthRate)
                        .build())
                .yesterdayStats(SalesStatisticsResponse.DailyStats.builder()
                        .totalSales(yesterdaySales)
                        .paymentCount(yesterdayCount)
                        .build())
                .refundStats(SalesStatisticsResponse.RefundStats.builder()
                        .refundCount(todayRefunds)
                        .yesterdayRefundCount(yesterdayRefunds)
                        .difference(refundDifference)
                        .build())
                .chartData(dailyDataList)
                .tableData(dailyDataList)
                .build();
    }

    /**
     * 결제 취소 (PENDING 상태만 가능)
     */
    @Override
    @Transactional
    public Payment cancelPayment(String orderId, Long userId) {
        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new IllegalArgumentException("결제 정보를 찾을 수 없습니다."));

        // 본인 확인
        if (!payment.getUser().getId().equals(userId)) {
            throw new IllegalStateException("본인의 결제만 취소할 수 있습니다.");
        }

        // PENDING 상태만 취소 가능
        if (payment.getStatus() != PaymentStatus.PENDING) {
            throw new IllegalStateException("대기 중인 결제만 취소할 수 있습니다.");
        }

        payment.setStatus(PaymentStatus.CANCELLED);
        payment.setUpdatedAt(LocalDateTime.now());

        return paymentRepository.save(payment);
    }

    /**
     * 결제 환불 (COMPLETED 상태만 가능)
     */
    @Override
    @Transactional
    public Payment refundPayment(String orderId, Long userId, String reason) {
        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new IllegalArgumentException("결제 정보를 찾을 수 없습니다."));

        // 본인 확인
        if (!payment.getUser().getId().equals(userId)) {
            throw new IllegalStateException("본인의 결제만 환불할 수 있습니다.");
        }

        // COMPLETED 상태만 환불 가능
        if (payment.getStatus() != PaymentStatus.COMPLETED) {
            throw new IllegalStateException("완료된 결제만 환불할 수 있습니다.");
        }

        // 토스 페이먼츠 API 환불 요청
        try {
            requestTossRefund(payment.getPaymentKey(), reason);
        } catch (Exception e) {
            log.error("토스 환불 요청 실패: {}", e.getMessage());
            throw new RuntimeException("환불 처리 중 오류가 발생했습니다.");
        }

        payment.setStatus(PaymentStatus.REFUNDED);
        payment.setUpdatedAt(LocalDateTime.now());

        return paymentRepository.save(payment);
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

    /**
     * 성장률 계산 헬퍼 메서드
     */
    private Double calculateGrowthRate(Long previousValue, Long currentValue) {
        if (previousValue == null || previousValue == 0) {
            return currentValue > 0 ? 100.0 : 0.0;
        }
        return ((currentValue - previousValue) * 100.0) / previousValue;
    }

    /**
     * 토스 페이먼츠 환불 API 호출
     */
    private void requestTossRefund(String paymentKey, String reason) {
        RestTemplate restTemplate = new RestTemplate();

        String url = "https://api.tosspayments.com/v1/payments/" + paymentKey + "/cancel";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(tossSecretKey, "");

        Map<String, String> body = new HashMap<>();
        body.put("cancelReason", reason);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        restTemplate.postForEntity(url, request, String.class);
    }

}
