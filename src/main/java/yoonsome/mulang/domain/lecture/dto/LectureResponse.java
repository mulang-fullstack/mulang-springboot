package yoonsome.mulang.domain.lecture.dto;

import lombok.Data;

@Data
public class LectureResponse {
    private Long lectureId;
    private String title;
    private String length;
}
