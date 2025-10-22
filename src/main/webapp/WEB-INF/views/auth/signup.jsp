<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" href="/img/favicon.svg" type="image/png">
    <link rel="stylesheet" href="/css/global.css"/>
    <link rel="stylesheet" href="/css/pages/auth/signup.css">
    <title>머랭? | 회원가입</title>
</head>
<body>
<div class="signup-container">
    <div class="signup-box">
        <!-- 로고 영역 -->
        <div class="logo-area">
            <!-- 로고 이미지 위치 - 실제 로고로 교체하세요 -->
            <img src="/img/logo.svg" alt="Logo" class="logo">
        </div>

        <form id="signupForm" action="/auth/signup" method="post">
            <!-- 이름 -->
            <div class="form-group">
                <label for="username">이름</label>
                <input type="text" id="username" name="username" placeholder="이름 입력" required>
            </div>

            <!-- 닉네임 -->
            <div class="form-group">
                <label for="nickname">닉네임</label>
                <input type="text" id="nickname" name="nickname" placeholder="닉네임 입력" required>
            </div>

            <!-- 이메일 입력 -->
            <div class="form-group">
                <label for="email">이메일</label>
                <div class="input-with-button">
                    <input type="email" id="email" name="email" placeholder="이메일 입력" required>
                    <button type="button" class="btn-verify" onclick="verifyEmail()">인증요청</button>
                </div>
            </div>

            <!-- 이메일 인증코드 -->
            <div class="form-group email-code-group hidden">
                <label for="emailCode">이메일 인증코드</label>
                <div class="input-with-button">
                    <input type="text" id="emailCode" name="emailCode" placeholder="이메일 인증코드 입력" required>
                    <button type="button" class="btn-verify" onclick="verifyEmailCode()">인증확인</button>
                </div>
            </div>

            <!-- 비밀번호 -->
            <div class="form-group">
                <label for="password">비밀번호</label>
                <div class="password-input-wrapper">
                    <input type="password" id="password" name="password" placeholder="6자리 이상, 숫자와 영문자 조합" required>
                    <button type="button" class="btn-password-toggle" onclick="togglePassword('password')">
                        <span class="eye-icon">👁</span>
                    </button>
                </div>
            </div>

            <!-- 비밀번호 재입력 -->
            <div class="form-group">
                <label for="passwordConfirm">비밀번호 재입력</label>
                <input type="password" id="passwordConfirm" name="passwordConfirm" placeholder="비밀번호 재입력" required>
            </div>

            <!-- 계정타입 선택 -->
            <div class="form-group">
                <label>계정타입</label>
                <div class="radio-group">
                    <label class="radio-label active">
                        <input type="radio" name="accountType" value="S" checked>
                        학생
                    </label>
                    <label class="radio-label">
                        <input type="radio" name="accountType" value="T">
                        강사
                    </label>
                </div>
            </div>

            <!-- 버튼 영역 -->
            <div class="button-group">
                <button type="submit" class="btn-primary">가입하기</button>
                <button type="button" class="btn-secondary" onclick="location.href='login'">로그인</button>
            </div>
        </form>
    </div>
</div>
<script src="/js/pages/auth/signup/signupUtils.js"></script>
<script src="/js/pages/auth/signup/signupValidation.js"></script>
<script src="/js/pages/auth/signup/signupFormUI.js"></script>
<script src="/js/pages/auth/signup/signupNickname.js"></script>
<script src="/js/pages/auth/signup/signupEmail.js"></script>
<script src="/js/pages/auth/signup/signupEmailCode.js"></script>
<script src="/js/pages/auth/signup/signupMain.js"></script>
</body>
</html>