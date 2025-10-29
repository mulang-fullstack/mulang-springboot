package yoonsome.mulang.api.student.service;

import yoonsome.mulang.api.student.dto.PaymentResponse;

import java.util.List;

public interface MypagePaymentService {

    List<PaymentResponse> findByUserId(Long userId);
}
