package yoonsome.mulang.infra.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class CustomFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        String message;

        if (exception instanceof BadCredentialsException) {
            message = "이메일 또는 비밀번호가 올바르지 않습니다.";
        } else if (exception instanceof LockedException) {
            message = "계정이 정지되었습니다. 관리자에게 문의하세요.";
        } else {
            message = "로그인에 실패했습니다. 잠시 후 다시 시도하세요.";
        }

        // flash처럼 1회성으로 세션에 저장
        request.getSession().setAttribute("FLASH_LOGIN_ERROR", message);

        // 리다이렉트 (URL은 깔끔하게 유지)
        response.sendRedirect("/auth/login");

    }
}
