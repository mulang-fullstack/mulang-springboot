package yoonsome.mulang.api.support.dto;

import lombok.*;
import yoonsome.mulang.domain.notice.entity.Notice;

import java.time.format.DateTimeFormatter;

/**
 * 공지사항 상세 조회 응답 DTO
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeDetailResponse {
    private Long id;
    private String type;
    private String title;
    private String content;
    private String createdAt;

    /**
     * Entity → DTO 변환
     */
    public static NoticeDetailResponse from(Notice notice) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

        return NoticeDetailResponse.builder()
                .id(notice.getId())
                .type(notice.getType() != null ? notice.getType().name() : "")
                .title(notice.getTitle())
                .content(notice.getContent())
                .createdAt(notice.getCreatedAt() != null ? notice.getCreatedAt().format(formatter) : "")
                .build();
    }
}