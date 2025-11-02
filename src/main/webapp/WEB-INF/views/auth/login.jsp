<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="icon" href="/img/favicon.svg" type="image/png">
    <link rel="stylesheet" href="/css/global.css"/>
    <link rel="stylesheet" href="/css/pages/auth/login.css"/>
    <title>머랭? | 로그인</title>
</head>
<body>
<div class="login-container">
    <div class="login-box">
        <!-- 로고 영역 -->
        <div class="logo-area">
            <img src="/img/logo.svg" alt="머랭 로고" class="logo" />
        </div>

        <form id="loginForm" action="/auth/login" method="post">
            <!-- 이메일 입력 -->
            <div class="form-group">
                <label for="email">이메일</label>
                <input type="email"
                       id="email"
                       name="email"
                       class="input-box"
                       placeholder="이메일 입력"
                       required>
            </div>

            <!-- 비밀번호 입력 -->
            <div class="form-group">
                <label for="password">비밀번호</label>
                <div class="password-input-wrapper">
                    <input type="password"
                           id="password"
                           name="password"
                           class="input-box"
                           placeholder="비밀번호 입력"
                           required>
                    <button type="button" class="btn-password-toggle" onclick="togglePassword('password')">
                        <span class="eye-icon">👁</span>
                    </button>
                </div>
            </div>
            <c:if test="${not empty sessionScope.FLASH_LOGIN_ERROR}">
                <div class="error-message">${sessionScope.FLASH_LOGIN_ERROR}</div>
                <c:remove var="FLASH_LOGIN_ERROR" scope="session"/>
            </c:if>


            <!-- 로그인 버튼 -->
            <button type="submit" class="btn-primary login-btn">로그인</button>
        </form>

        <!-- 구분선 -->
        <div class="divider">
            <span>또는</span>
        </div>

        <!-- 소셜 로그인 -->
        <button type="button" class="social-btn google-btn" onclick="location.href='/oauth2/authorization/google'">
            <img src="/img/icon/google.svg" alt="Google" width="20">
            <span>Google 계정으로 로그인</span>
        </button>

        <!-- 하단 링크 -->
        <div class="links">
            <a href="/auth/signup">회원가입</a>
            <span class="separator">|</span>
            <a href="/auth/find-email">이메일 찾기</a>
            <span class="separator">|</span>
            <a href="/auth/find-password">비밀번호 찾기</a>
        </div>
    </div>
</div>
</body>
</html>