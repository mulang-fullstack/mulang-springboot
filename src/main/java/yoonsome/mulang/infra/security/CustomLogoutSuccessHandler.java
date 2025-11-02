package yoonsome.mulang.infra.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import yoonsome.mulang.domain.log.entity.UserLog;
import yoonsome.mulang.domain.log.repository.UserLogRepository;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    private final UserLogRepository loginLogRepository;

    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response,
                                Authentication authentication)
            throws IOException {

        if (authentication != null) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            UserLog log = UserLog.builder()
                    .email(userDetails.getUser().getEmail())
                    .username(userDetails.getUser().getUsername())
                    .action(UserLog.ActionType.LOGOUT)
                    .ip(getClientIp(request))
                    .userAgent(parseUserAgent(request.getHeader("User-Agent")))
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

        // IPv6 로컬 주소를 IPv4로 변환
        if (ip != null && (ip.equals("0:0:0:0:0:0:0:1") || ip.equals("::1"))) {
            ip = "127.0.0.1";
        }

        return ip;
    }

    private String parseUserAgent(String userAgent) {
        if (userAgent == null || userAgent.isBlank()) {
            return "Unknown";
        }

        String os = extractOS(userAgent);
        String browser = extractBrowser(userAgent);

        return os + "/" + browser;
    }

    private String extractOS(String userAgent) {
        if (userAgent.contains("Windows NT 10.0")) {
            return "Windows 11";
        } else if (userAgent.contains("Windows NT 11.0")) {
            return "Windows 11";
        } else if (userAgent.contains("Windows NT 6.3")) {
            return "Windows 8.1";
        } else if (userAgent.contains("Windows NT 6.2")) {
            return "Windows 8";
        } else if (userAgent.contains("Windows NT 6.1")) {
            return "Windows 7";
        } else if (userAgent.contains("Mac OS X")) {
            Pattern pattern = Pattern.compile("Mac OS X ([0-9_]+)");
            Matcher matcher = pattern.matcher(userAgent);
            if (matcher.find()) {
                return "macOS " + matcher.group(1).replace("_", ".");
            }
            return "macOS";
        } else if (userAgent.contains("Android")) {
            Pattern pattern = Pattern.compile("Android ([0-9.]+)");
            Matcher matcher = pattern.matcher(userAgent);
            if (matcher.find()) {
                return "Android " + matcher.group(1);
            }
            return "Android";
        } else if (userAgent.contains("iPhone") || userAgent.contains("iPad")) {
            return "iOS";
        } else if (userAgent.contains("Linux")) {
            return "Linux";
        }
        return "Unknown OS";
    }

    private String extractBrowser(String userAgent) {
        // Edge 확인 (Chrome보다 먼저 확인해야 함)
        if (userAgent.contains("Edg/")) {
            Pattern pattern = Pattern.compile("Edg/([0-9]+)");
            Matcher matcher = pattern.matcher(userAgent);
            if (matcher.find()) {
                return "Edge " + matcher.group(1);
            }
            return "Edge";
        }
        // Chrome 확인
        if (userAgent.contains("Chrome/")) {
            Pattern pattern = Pattern.compile("Chrome/([0-9]+)");
            Matcher matcher = pattern.matcher(userAgent);
            if (matcher.find()) {
                return "Chrome " + matcher.group(1);
            }
            return "Chrome";
        }
        // Safari 확인 (Chrome이 아닌 경우)
        if (userAgent.contains("Safari/") && !userAgent.contains("Chrome")) {
            Pattern pattern = Pattern.compile("Version/([0-9]+)");
            Matcher matcher = pattern.matcher(userAgent);
            if (matcher.find()) {
                return "Safari " + matcher.group(1);
            }
            return "Safari";
        }
        // Firefox 확인
        if (userAgent.contains("Firefox/")) {
            Pattern pattern = Pattern.compile("Firefox/([0-9]+)");
            Matcher matcher = pattern.matcher(userAgent);
            if (matcher.find()) {
                return "Firefox " + matcher.group(1);
            }
            return "Firefox";
        }
        return "Unknown Browser";
    }
}