package yoonsome.mulang.api.player.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VodCourseResponse {
    private Long courseId;
    private String title;
    private String subtitle;
    private String language;
    private String category;
    private Integer price;
    private String statusText;
}
