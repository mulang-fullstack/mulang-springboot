package yoonsome.mulang.api.admin.system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yoonsome.mulang.domain.notice.entity.Notice;

/**
 * 공지사항 수정 요청 DTO
 */
@Getter
@Setter
@NoArgsConstructor
public class NoticeUpdateRequest {

    @NotNull(message = "공지 유형을 선택해주세요")
    private Notice.NoticeType type;

    @NotBlank(message = "제목을 입력해주세요")
    @Size(max = 200, message = "제목은 200자 이내로 입력해주세요")
    private String title;

    @NotBlank(message = "내용을 입력해주세요")
    @Size(max = 1000, message = "내용은 1000자 이내로 입력해주세요")
    private String content;

    @NotNull(message = "공개 여부를 선택해주세요")
    private Notice.NoticeStatus status;
}