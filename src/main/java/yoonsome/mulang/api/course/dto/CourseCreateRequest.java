package yoonsome.mulang.api.course.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class CourseCreateRequest {
    private String title;               //제목
    private String content;             //소개
    private LocalDate applyStartedAt;   //접수 시작일
    private LocalDate applyEndedAt;     //접수 종료일
    private LocalDate startedAt;        //강좌 시작일
    private LocalDate endedAt;          //강좌 종료일
    private boolean status;             //상태
    private Integer maxStudent;         //전체 인원수
    private Integer currentStudent;     //신청 인원수
    private Integer price;              //수강비
    private String type;                //종류
    private Integer lectureCount;       //강의 수
    private Long tutorId;               //강사번호
    private Long languageId;            //언어번호
    private Long categoryId;            //카테고리번호
}
