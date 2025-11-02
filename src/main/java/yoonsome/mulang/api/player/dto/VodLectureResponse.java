package yoonsome.mulang.api.player.dto;

import lombok.Builder;
import lombok.Data;;

/**
 * 공용 VOD 화면에서 사용하는 강의 DTO
 */
@Data
@Builder
public class VodLectureResponse {
    private Long id;
    private String title;
    private String fileUrl;
}
