package yoonsome.mulang.api.student.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yoonsome.mulang.api.student.dto.ReviewResponse;
import yoonsome.mulang.domain.review.entity.CourseReview;
import yoonsome.mulang.domain.review.repository.ReviewRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MypageReviewServiceImpl implements MypageReviewService {

    private final ReviewRepository reviewRepository;

    @Override
    public List<ReviewResponse> findByStudentIdOrderByCreatedAtDesc(Long userId) {

        List<CourseReview> reviews = reviewRepository
                .findByStudentIdOrderByCreatedAtDesc(userId);

        return reviews.stream()
                .map(ReviewResponse::from)
                .collect(Collectors.toList());
    }
    @Override
    public List<ReviewResponse> findByStudentIdOrderByCreatedAtAsc(Long userId) {

        List<CourseReview> reviews = reviewRepository
                .findByStudentIdOrderByCreatedAtAsc(userId);

        return reviews.stream()
                .map(ReviewResponse::from)
                .collect(Collectors.toList());
    }

    @Override
    public ReviewResponse findById(Long reviewId) {
        CourseReview review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));

        return ReviewResponse.from(review);
    }

}
