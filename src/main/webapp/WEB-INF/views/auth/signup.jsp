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
    <link rel="stylesheet" href="/css/pages/auth/signup.css"/>
    <title>Mulang? login</title>
</head>
<body>
<section class="signup-box">
    <div class="signup-container">
        <div class="logo">
            <img src="/img/logo.svg" alt="머랭 로고">
        </div>

        <form action="/signup" method="post">
            <label class="input-label">이메일</label>
            <input type="email" name="email" placeholder="이메일 입력" class="input-box" required>

            <label class="input-label">비밀번호</label>
            <input type="password" name="password" placeholder="6자리 이상, 숫자와 영문자 조합" class="input-box" required>
            <input type="password" name="passwordConfirm" placeholder="비밀번호 재입력" class="input-box" required>

            <label class="input-label">이름</label>
            <input type="text" name="name" placeholder="이름 입력" class="input-box" required>

            <label class="input-label">휴대폰 번호</label>
            <div class="phone-field">
                <select name="countryCode" class="country-select">
                    <option value="+82">대한민국 +82</option>
                </select>
                <input type="text" name="phone" placeholder="휴대폰 번호 입력 (-제외)" class="input-box" required>
                <button type="button" class="btn-sub">인증요청</button>
            </div>
            <div class="verify-field">
                <input type="text" name="verifyCode" placeholder="인증번호 입력" class="input-box">
                <button type="button" class="btn-sub">인증확인</button>
            </div>

            <label class="input-label">성별</label>
            <div class="gender">
                <label><input type="radio" name="gender" value="M" checked> 남자</label>
                <label><input type="radio" name="gender" value="F"> 여자</label>
            </div>

            <div class="agree-section">
                <label><input type="checkbox" id="allAgree"> 전체 약관 동의</label>
                <ul>
                    <li>[필수] 만 14세 이상</li>
                    <li>[필수] 서비스 약관 동의</li>
                    <li>[필수] 개인정보 처리방침 및 제3자 제공 동의</li>
                    <li>[선택] 광고성 정보 수신 동의</li>
                </ul>
            </div>

            <div class="btn-wrap">
                <button type="submit" class="btn-primary">가입하기</button>
                <a href="/login" class="btn-secondary">로그인</a>
            </div>
        </form>
    </div>
</section>
</div>
</body>
</html>