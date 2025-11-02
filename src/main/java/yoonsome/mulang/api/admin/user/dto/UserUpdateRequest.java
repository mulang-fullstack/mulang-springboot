package yoonsome.mulang.api.admin.user.dto;

import lombok.Data;
import yoonsome.mulang.domain.user.entity.User.UserStatus;

/**
 * 관리자 회원 정보 상태 수정 요청 DTO
 */
@Data
public class UserUpdateRequest {
    private UserStatus status;
}
