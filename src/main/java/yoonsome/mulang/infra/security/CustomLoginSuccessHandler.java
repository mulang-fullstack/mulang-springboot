package yoonsome.mulang.infra.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import yoonsome.mulang.domain.authlog.entity.LoginLog;
import yoonsome.mulang.domain.authlog.repository.LoginLogRepository;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final LoginLogRepository loginLogRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException {

        String ip = getClientIp(request);
        String userAgent = request.getHeader("User-Agent");

        // CustomUserDetails로 캐스팅해 User 엔티티 접근
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String email = userDetails.getUser().getEmail();
        String username = userDetails.getUser().getUsername();

        LoginLog log = LoginLog.builder()
                .email(email)
                .username(username)
                .action("LOGIN")
                .ip(ip)
                .userAgent(userAgent)
                .build();

        loginLogRepository.save(log);
        response.sendRedirect("/");
    }

    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isBlank() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }
}
