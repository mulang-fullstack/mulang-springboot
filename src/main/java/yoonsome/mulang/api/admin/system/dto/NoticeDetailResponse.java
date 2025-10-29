package yoonsome.mulang.api.admin.system.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yoonsome.mulang.domain.notice.entity.Notice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 공지사항 상세 응답 DTO (수정 모달용)
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeDetailResponse {

    private Long id;
    private Notice.NoticeType type;
    private String title;
    private String content;
    private Notice.NoticeStatus status;
    private String userNickname;
    private String createdAt;
    private String updatedAt;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /**
     * Entity -> DTO 변환
     */
    public static NoticeDetailResponse from(Notice notice) {
        return NoticeDetailResponse.builder()
                .id(notice.getId())
                .type(notice.getType())
                .title(notice.getTitle())
                .content(notice.getContent())
                .status(notice.getStatus())
                .userNickname(notice.getUser() != null ? notice.getUser().getNickname() : "관리자")
                .createdAt(notice.getCreatedAt() != null ? notice.getCreatedAt().format(FORMATTER) : "")
                .updatedAt(notice.getUpdatedAt() != null ? notice.getUpdatedAt().format(FORMATTER) : "")
                .build();
    }
}