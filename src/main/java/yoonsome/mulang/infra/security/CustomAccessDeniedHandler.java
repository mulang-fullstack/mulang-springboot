package yoonsome.mulang.infra.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException)
            throws IOException, ServletException {

        log.warn("Access Denied: {} - {}", request.getRequestURI(), accessDeniedException.getMessage());

        // API 요청인 경우 JSON 응답
        String requestURI = request.getRequestURI();
        if (requestURI.startsWith("/api/")) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"error\":\"접근 권한이 없습니다.\",\"status\":403}");
            return;
        }

        // 일반 페이지 요청인 경우 에러 페이지로 포워딩
        request.setAttribute("errorMessage", "접근 권한이 없습니다.");
        request.getRequestDispatcher("/WEB-INF/views/error/access-denied.jsp").forward(request, response);
    }
}