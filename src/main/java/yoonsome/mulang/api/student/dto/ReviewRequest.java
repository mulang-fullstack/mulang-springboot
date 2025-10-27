package yoonsome.mulang.api.student.dto;

import lombok.Data;

@Data
public class ReviewRequest {
    private Long courseId;
    private Integer rating;
    private String content;
}
