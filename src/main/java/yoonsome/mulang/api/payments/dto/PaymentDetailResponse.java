package yoonsome.mulang.api.payments.dto;

import lombok.Builder;
import lombok.Data;

/**
 * 결제 상세 조회 응답 (관리자용)
 */
@Data
@Builder
public class PaymentDetailResponse {

    private Long paymentId;
    private String orderId;
    private String paymentKey;
    private Integer amount;
    private String status;

    // 사용자 정보
    private UserSummary user;

    // 강좌 정보
    private CourseSummary course;

    // 결제 상세
    private String paymentMethod;
    private String paymentMethodDetail;
    private String approvedAt;
    private String failureMessage;

    @Data
    @Builder
    public static class UserSummary {
        private Long id;
        private String username;
        private String email;
        private String nickname;
    }

    @Data
    @Builder
    public static class CourseSummary {
        private Long id;
        private String title;
        private String teacherName;
        private Integer price;
    }
}
