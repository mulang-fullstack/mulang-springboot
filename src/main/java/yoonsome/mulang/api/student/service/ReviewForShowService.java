package yoonsome.mulang.api.student.service;

import yoonsome.mulang.api.student.dto.ReviewResponse;

import java.util.List;

public interface ReviewForShowService {

    List<ReviewResponse> findByStudentIdOrderByCreatedAtDesc(Long userId);
    List<ReviewResponse> findByStudentIdOrderByCreatedAtAsc(Long userId);
}
