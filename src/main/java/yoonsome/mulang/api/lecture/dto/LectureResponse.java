package yoonsome.mulang.api.lecture.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LectureResponse {
    private Long id;
    private String title;
    private String length;
}
