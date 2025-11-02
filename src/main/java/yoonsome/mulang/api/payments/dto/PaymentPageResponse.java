package yoonsome.mulang.api.payments.dto;

import lombok.Builder;
import lombok.Data;

/**
 * 결제 페이지 진입 시 반환
 * GET /payments/{courseId}
 */
@Data
@Builder
public class PaymentPageResponse {
    // 결제 정보
    private String orderId;          // 생성된 주문번호
    private String username;         // 구매자명
    private Integer amount;          // 결제 금액

    // 강좌 정보
    private Long courseId;
    private String courseTitle;
    private String courseDescription;
    private String thumbnailUrl;
    private String teacherNickname;
    private Integer lectureCount;    // 강의 수
    private Double averageRating;
    private Integer reviewCount;
}