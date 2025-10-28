package yoonsome.mulang.api.student.service;

import yoonsome.mulang.api.student.dto.ReviewResponse;

import java.util.List;

public interface MypageReviewService {

    List<ReviewResponse> findByStudentIdOrderByCreatedAtDesc(Long userId);
    List<ReviewResponse> findByStudentIdOrderByCreatedAtAsc(Long userId);

    ReviewResponse findById(Long reviewId);
}
