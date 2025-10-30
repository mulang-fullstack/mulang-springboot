package yoonsome.mulang.api.course.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Data
public class CourseListResponse {
    private Long id;                    //courseId 상세페이지 이동용
    private String thumbnail;           // 썸네일
    private String title;               // 강의 제목
    private String subtitle;            // 강의 부제목
    private String teacherName;         // 강사명
    private double averageRating;       // 별점
    private int reviewCount;            // 리뷰 수
    private Integer price;              // 수강료
    private LocalDateTime createdAt;    // 강의 등록일
    private boolean favorited;          // 찜
}
