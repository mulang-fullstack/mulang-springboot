package yoonsome.mulang.api.admin.user.dto;

import lombok.Builder;
import lombok.Getter;
import yoonsome.mulang.domain.user.entity.User;
import java.time.format.DateTimeFormatter;

@Getter
@Builder
public class UserListResponse {

    private Long id;
    private String username;
    private String nickname;
    private String email;
    private String role;
    private String userStatus;
    private String createdAt;

    public static UserListResponse from(User user) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return UserListResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .role(user.getRole().name()) // STUDENT, TEACHER, ADMIN
                .userStatus(user.getUserStatus().name()) // ACTIVE, INACTIVE
                .createdAt(user.getCreatedAt() != null ? user.getCreatedAt().format(fmt) : "")
                .build();
    }
}
