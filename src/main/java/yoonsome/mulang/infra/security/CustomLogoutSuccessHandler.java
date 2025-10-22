package yoonsome.mulang.infra.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import yoonsome.mulang.domain.authlog.entity.LoginLog;
import yoonsome.mulang.domain.authlog.repository.LoginLogRepository;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    private final LoginLogRepository loginLogRepository;

    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response,
                                Authentication authentication)
            throws IOException {

        if (authentication != null) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            LoginLog log = LoginLog.builder()
                    .email(userDetails.getUser().getEmail())
                    .username(userDetails.getUser().getUsername())
                    .action("LOGOUT")
                    .ip(getClientIp(request))
                    .userAgent(request.getHeader("User-Agent"))
                    .build();

            loginLogRepository.save(log);
        }

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

