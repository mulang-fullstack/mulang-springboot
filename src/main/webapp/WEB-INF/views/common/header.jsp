<%@ page contentType="text/html;charset=utf-8" import="java.sql.*" %>

<link rel="stylesheet" href="/css/common/header.css"/>
<script src="js/header.js"></script>
<header>
    <div class="header-inner">
        <!-- LEFT: 로고 + 네비게이션 -->
        <div class="header-left">
            <a href="/" class="logo">
                <img id="logo-img" src="/img/logo.svg" alt="로고">
            </a>
            <ul>
                <li><a href="#">영어</a></li>
                <li><a href="#">중국어</a></li>
                <li><a href="#">일본어</a></li>
                <li><a href="#">AI테스트</a></li>
            </ul>
        </div>

        <!-- RIGHT: 로그인/회원가입 또는 프로필 -->
        <div class="header-right">
            <c:choose>
                <c:when test="${not empty sessionScope.tests}">
                    <button class="write-button">작성</button>
                    <div class="profile-area">
                        <button class="profile-toggle">
                            <img src="img/profile.png" alt="프로필 이미지">
                        </button>
                        <div class="profile-dropdown">
                            <p class="profile-email">${tests.email}</p>
                            <p class="profile-nick">${tests.email}님, 안녕하세요.</p>
                            <div class="profile-divider"></div>
                            <a href="/mypage.do?tap=my" class="profile-link">마이페이지</a>
                            <a href="auth.do?mode=logout" class="profile-link">로그아웃</a>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <a href="auth.do?mode=login" class="btn-login">로그인</a>
                    <a href="auth.do?mode=join" class="btn-join">회원가입</a>
                </c:otherwise>
            </c:choose>

            <!-- 모바일 메뉴 버튼 -->
            <button class="mobile-menu-toggle" aria-label="메뉴 열기" aria-expanded="false">
                <span></span><span></span><span></span>
            </button>
        </div>
    </div>
</header>

<!-- 모바일 메뉴 -->
<div class="overlay" hidden></div>
<nav id="mobilePanel" class="mobile-panel" aria-hidden="true">
    <div class="mobile-panel-top">
        <a href="/"><img src="/img/logo.svg" alt="로고"></a>
        <ul class="mobile-nav">
            <li><a href="#">영어</a></li>
            <li><a href="#">중국어</a></li>
            <li><a href="#">일본어</a></li>
            <li><a href="#">AI테스트</a></li>
        </ul>
    </div>
    <div class="mobile-panel-bottom">
        <c:choose>
            <c:when test="${empty sessionScope.tests}">
                <a href="auth.do?mode=login">로그인</a>
                <a href="auth.do?mode=join">회원가입</a>
            </c:when>
            <c:otherwise>
                <a href="/mypage.do?tap=my">마이페이지</a>
                <a href="auth.do?mode=logout">로그아웃</a>
            </c:otherwise>
        </c:choose>
    </div>
</nav>