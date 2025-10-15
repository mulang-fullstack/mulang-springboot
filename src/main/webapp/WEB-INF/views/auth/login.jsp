<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="icon" href="/img/favicon.svg" type="image/png">
    <link rel="stylesheet" href="/css/global.css"/>
    <link rel="stylesheet" href="/css/pages/auth/login.css"/>
    <title>Mulang? login</title>
</head>
<body>
<section class="login-box">
    <div class="login-container">
        <div class="logo">
            <img src="/img/logo.svg" alt="머랭 로고" />
        </div>
        <h1>로그인</h1>
        <form action="/auth/login.do" method="post">
            <input type="email" name="email" class="input-box" placeholder="이메일 입력" required>
            <input type="password" name="password" class="input-box" placeholder="비밀번호 입력" required>
            <button type="submit" class="login-btn">로그인</button>
        </form>

        <button class="google-btn">
            <img src="/img/icon/google.svg" alt="Google" width="20">
            Google 계정으로 로그인
        </button>

        <div class="links">
            <a href="/auth/signup">회원가입</a>
            <a href="/find-email">이메일 찾기</a>
            <a href="/find-password">비밀번호 찾기</a>
        </div>
    </div>
</section>
</body>
</html>