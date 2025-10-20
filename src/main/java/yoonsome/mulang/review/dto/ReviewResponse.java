package yoonsome.mulang.review.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReviewResponse {
    private Long reviewId;
    private String writerName;
    private double rating;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}