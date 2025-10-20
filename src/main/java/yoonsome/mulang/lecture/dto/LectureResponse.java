package yoonsome.mulang.lecture.dto;

import lombok.Data;

@Data
public class LectureResponse {
    private Long lectureId;
    private String title;
    private String length;
}
