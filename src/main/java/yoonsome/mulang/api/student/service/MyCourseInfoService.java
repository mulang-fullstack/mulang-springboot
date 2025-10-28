package yoonsome.mulang.api.student.service;

import yoonsome.mulang.api.student.dto.MycourseResponse;
import yoonsome.mulang.domain.payment.entity.PaymentStatus;

import java.util.List;

public interface MyCourseInfoService {
    List<MycourseResponse> findByUserId(Long userId);

}
