package yoonsome.mulang.course.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CourseListResponse {
    private Long id;               // 상세페이지 이동용 (화면엔 안보임)
    private String title;          // 강의 제목
    private String content;        // 강의 설명
    private String teacherName;    // 강사명
    private double averageRating;  // 별점
    private int reviewCount;       // 리뷰 수
    private Integer price;         // 수강료
}
