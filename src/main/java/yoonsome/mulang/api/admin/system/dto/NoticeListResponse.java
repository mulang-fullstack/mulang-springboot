package yoonsome.mulang.api.admin.system.dto;

import lombok.*;
import yoonsome.mulang.domain.notice.entity.Notice;

import java.time.LocalDateTime;

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
    private LocalDateTime createdAt;

    public static NoticeListResponse from(Notice notice) {
        return NoticeListResponse.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .type(notice.getType().name())
                .status(notice.getStatus().name())
                .userNickname(notice.getUser().getNickname())
                .createdAt(notice.getCreatedAt())
                .build();
    }
}
