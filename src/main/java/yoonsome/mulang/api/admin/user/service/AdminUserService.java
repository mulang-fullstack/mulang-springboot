package yoonsome.mulang.api.admin.user.service;

import org.springframework.data.domain.Page;
import yoonsome.mulang.api.admin.user.dto.UserListResponse;
import yoonsome.mulang.api.admin.user.dto.UserSearchRequest;
import yoonsome.mulang.domain.user.entity.User;

public interface AdminUserService {
    /**
     * 관리자용 사용자 목록 조회
     */
    Page<UserListResponse> getUserList(UserSearchRequest request);
}
