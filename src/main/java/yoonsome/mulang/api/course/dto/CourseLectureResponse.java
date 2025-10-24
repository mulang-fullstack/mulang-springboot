package yoonsome.mulang.api.course.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CourseLectureResponse {
    private Long id;
    private String title;
    private String length;
}
