package yoonsome.mulang.api.student.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yoonsome.mulang.api.student.dto.PaymentResponse;
import yoonsome.mulang.domain.payment.entity.Payment;
import yoonsome.mulang.domain.payment.entity.PaymentStatus;
import yoonsome.mulang.domain.payment.repository.PaymentRepository;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class MypagePaymentServiceImpl implements MypagePaymentService {

    private final PaymentRepository paymentRepository;

    @Override
    public List<PaymentResponse> findByUserId(Long userId) {
        List<Payment> payments = paymentRepository.findByUserId(userId);

        return payments.stream()
                .filter(payment -> payment.getStatus() == PaymentStatus.COMPLETED)
                .map(PaymentResponse::from)
                .collect(Collectors.toList());
    }
}
