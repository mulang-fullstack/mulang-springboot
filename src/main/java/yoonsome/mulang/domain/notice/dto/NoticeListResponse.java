package yoonsome.mulang.domain.notice.dto;

import lombok.*;
import yoonsome.mulang.domain.notice.entity.Notice;

import java.time.format.DateTimeFormatter;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeListResponse {
    private Long id;
    private String title;
    private String type;
    private String status;
    private String userNickname;
    private String createdAt;

    public static NoticeListResponse from(Notice notice) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return NoticeListResponse.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .type(notice.getType().name())
                .status(notice.getStatus().name())
                .userNickname(notice.getUser().getNickname())
                .createdAt(notice.getCreatedAt() != null ? notice.getCreatedAt().format(fmt) : "")
                .build();
    }
}
