package yoonsome.mulang.api.course.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class CourseDetailResponse {
    private Long id;
    private String title;                       //  강의 제목
    private String thumbnail;                   //  강의 썸네일
    private String subtitle;                    //  강의 부제목
    private String content;                     //  강의 설명
    private String teacherName;                 //  강사명
    private Integer lectureCount;               //  강의 수
    private Double averageRating;               //  평균 별점
    private Integer reviewCount;                //  리뷰 수
    private Integer price;                      //  수강비
    private List<CourseLectureResponse> lectures;     //  커리큘럼
    private boolean favorited;                  // 찜
}
