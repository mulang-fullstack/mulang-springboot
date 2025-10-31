package yoonsome.mulang.api.review;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class ReviewResponse {
    private Long id;
    private Long studentId;
    private String studentName;
    private double rating;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String profileImg;
}