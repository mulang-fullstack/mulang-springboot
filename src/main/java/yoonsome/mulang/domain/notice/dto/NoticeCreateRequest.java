package yoonsome.mulang.domain.notice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import yoonsome.mulang.domain.notice.entity.Notice.NoticeStatus;
import yoonsome.mulang.domain.notice.entity.Notice.NoticeType;

/**
 * 공지사항 생성 요청 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeCreateRequest {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @NotNull(message = "공지 유형은 필수입니다.")
    private NoticeType type; // GENERAL, EVENT, SYSTEM, UPDATE

    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    @NotBlank(message = "내용은 필수입니다.")
    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @NotNull(message = "공개 여부는 필수입니다.")
    private NoticeStatus status; // PUBLIC, PRIVATE
}
