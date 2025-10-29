package yoonsome.mulang.api.admin.user.service;

import org.springframework.data.domain.Page;
import yoonsome.mulang.api.admin.user.dto.*;
import yoonsome.mulang.domain.user.entity.User;

public interface AdminUserService {
    /**
     * 관리자용 사용자 목록 조회
     */
    Page<UserListResponse> getUserList(UserSearchRequest request);
    /**
     * 사용자 로그 목록 조회
     */
    Page<UserLogListResponse> getUserLogList(UserLogSearchRequest request);

    void updateUserInfo(Long userId, User.UserStatus status);
}
