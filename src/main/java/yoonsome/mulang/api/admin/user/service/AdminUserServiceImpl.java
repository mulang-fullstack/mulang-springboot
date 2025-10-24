package yoonsome.mulang.api.admin.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import yoonsome.mulang.api.admin.user.dto.UserListResponse;
import yoonsome.mulang.api.admin.user.dto.UserLogListResponse;
import yoonsome.mulang.api.admin.user.dto.UserLogSearchRequest;
import yoonsome.mulang.api.admin.user.dto.UserSearchRequest;
import yoonsome.mulang.domain.log.entity.UserLog;
import yoonsome.mulang.domain.log.service.UserLogService;
import yoonsome.mulang.domain.user.entity.User;
import yoonsome.mulang.domain.user.service.UserService;


@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {

    private final UserService userService;
    private final UserLogService userLogService;

    @Override
    public Page<UserListResponse> getUserList(UserSearchRequest request) {
        Page<User> userPage = userService.getUserList(request);
        // 엔티티 → DTO 변환 (페이징 정보 유지)
        return userPage.map(UserListResponse::from);
    }

    @Override
    public Page<UserLogListResponse> getUserLogList(UserLogSearchRequest request) {
        Page<UserLog> userLogPage = userLogService.getUserLogList(request);
        return userLogPage.map(UserLogListResponse::from);
    }
}
