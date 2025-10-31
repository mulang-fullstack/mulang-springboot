package yoonsome.mulang.domain.enrollment.service;

import yoonsome.mulang.domain.payment.entity.Payment;

/**
 * 수강신청 서비스 인터페이스
 */
public interface EnrollmentService {

    /**
     * 수강신청 생성 (+ Progress 자동 생성)
     * @param payment 결제 정보
     * @return 생성된 Enrollment ID
     */
    Long createEnrollment(Payment payment);

    /**
     * 중복 구매 체크
     * @param userId 사용자 ID
     * @param courseId 강좌 ID
     * @return 이미 수강신청했으면 true
     */
    boolean hasEnrollment(Long userId, Long courseId);

    /**
     * 강의 취소
     * @param userId 사용자
     * @param courseId 강좌
     */
    void cancelEnrollment(Long userId, Long courseId);
}
