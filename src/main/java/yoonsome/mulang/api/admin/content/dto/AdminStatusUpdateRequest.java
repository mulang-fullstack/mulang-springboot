package yoonsome.mulang.api.admin.content.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import yoonsome.mulang.domain.course.entity.Course;
import yoonsome.mulang.domain.course.entity.StatusType;

/**
 * 강좌 상태 업데이트 요청 DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminStatusUpdateRequest {

    /**
     * 변경할 상태
     * PUBLIC, PRIVATE, PENDING, REJECTED
     */
    @NotNull(message = "상태는 필수입니다.")
    private StatusType status;

    /**
     * 거절 사유 (REJECTED 상태일 때 필수)
     */
    private String rejectionReason;
}