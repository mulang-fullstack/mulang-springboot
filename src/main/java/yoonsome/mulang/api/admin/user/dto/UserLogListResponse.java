package yoonsome.mulang.api.admin.user.dto;

import lombok.Builder;
import lombok.Getter;
import yoonsome.mulang.domain.log.entity.UserLog;
import java.time.format.DateTimeFormatter;

/**
 * 로그인/로그아웃 로그 응답 DTO
 * - 리스트 뷰용
 */
@Getter
@Builder
public class UserLogListResponse {

    private Long id;
    private String username;
    private String email;
    private String action; // LOGIN / LOGOUT
    private String ip;
    private String userAgent;
    private String createdAt;

    public static UserLogListResponse from(UserLog log) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return UserLogListResponse.builder()
                .id(log.getId())
                .username(log.getUsername())
                .email(log.getEmail())
                .action(log.getAction().name())
                .ip(log.getIp())
                .userAgent(log.getUserAgent())
                .createdAt(log.getCreatedAt() != null ? log.getCreatedAt().format(fmt) : "")
                .build();
    }
}
