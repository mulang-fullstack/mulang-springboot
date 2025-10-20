<%@ page contentType="text/html;charset=utf-8" import="java.sql.*" %>

<link rel="stylesheet" href="/css/common/header.css"/>
<script src="js/common/header.js"></script>
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
                <c:when test="${not empty sessionScope.loginUser}">
                    <div class="user-menu">
                        <a href="/mypage.do?tap=my" class="menu-item">내 학습</a>
                        <div class="menu-divider"></div>
                        <div class="profile-section">
                            <button class="profile-toggle">
                                내 정보
                                <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="chevron">
                                    <polyline points="6 9 12 15 18 9"></polyline>
                                </svg>
                            </button>
                            <div class="profile-dropdown">
                                <p class="profile-name"><span>${loginUser.username}</span>님 안녕하세요!</p>
                                <div class="profile-divider"></div>
                                <a href="#" class="profile-link">프로필 설정</a>
                                <a href="#" class="profile-link">결제 내역</a>
                                <a href="#" class="profile-link">고객센터</a>
                                <a href="/auth/logout" class="profile-link">로그아웃</a>
                            </div>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <a href="/auth/login" class="btn-login">로그인</a>
                    <a href="/auth/signup" class="btn-join">회원가입</a>
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
            <c:when test="${empty sessionScope.loginUser}">
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