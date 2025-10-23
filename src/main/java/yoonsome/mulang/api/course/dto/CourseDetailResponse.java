package yoonsome.mulang.api.course.dto;

import lombok.Builder;
import lombok.Data;
import yoonsome.mulang.api.lecture.dto.LectureResponse;

import java.time.LocalDate;
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
    //private LocalDate applyStartedAt;           //  접수 시작일
    //private LocalDate applyEndedAt;             //  접수 종료일
    //private LocalDate startedAt;                //  강좌 시작일
    //private LocalDate endedAt;                  //  강좌 종료일
    private Integer lectureCount;               //  강의 수
    private Integer price;                      //  수강비
    private List<LectureResponse> lectures;     //  커리큘럼
}
