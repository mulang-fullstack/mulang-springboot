<%@ page contentType="text/html;charset=utf-8" import="java.sql.*" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<link rel="stylesheet" href="/css/common/header.css"/>
<script src="/js/common/header.js"></script>

<header>
    <div class="header-inner">
        <!-- LEFT: 로고 + 네비게이션 -->
        <div class="header-left">
            <a href="/" class="logo">
                <img id="logo-img" src="/img/logo.svg" alt="로고">
            </a>
            <ul>
                <li><a href="/course?languageId=1">영어</a></li>
                <li><a href="/course?languageId=2">중국어</a></li>
                <li><a href="/course?languageId=3">일본어</a></li>
                <li><a href="#">AI테스트</a></li>
            </ul>
        </div>

        <!-- RIGHT: 로그인/회원가입 또는 프로필 -->
        <div class="header-right">

            <!-- 학생 사용자 -->
            <sec:authorize access="hasRole('STUDENT')">
                <div class="user-menu">
                    <a href="/student/personal" class="menu-item">내 학습</a>
                    <div class="menu-divider"></div>
                    <div class="profile-section">
                        <button class="profile-toggle">
                            내 정보
                            <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="chevron">
                                <polyline points="6 9 12 15 18 9"></polyline>
                            </svg>
                        </button>
                        <div class="profile-dropdown">
                            <p class="profile-name">
                                <span><sec:authentication property="principal.user.username"/></span>님 안녕하세요!
                            </p>
                            <div class="profile-divider"></div>
                            <a href="#" class="profile-link">프로필 설정</a>
                            <a href="#" class="profile-link">결제 내역</a>
                            <a href="#" class="profile-link">고객센터</a>
                            <a href="/logout" class="profile-link">로그아웃</a>
                        </div>
                    </div>
                </div>
            </sec:authorize>

            <!-- 선생님 사용자 -->
            <sec:authorize access="hasRole('TEACHER')">
                <div class="user-menu">
                    <a href="/teacher/mypage/profile" class="menu-item">내 강의</a>
                    <div class="menu-divider"></div>
                    <div class="profile-section">
                        <button class="profile-toggle">
                            내 정보
                            <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="chevron">
                                <polyline points="6 9 12 15 18 9"></polyline>
                            </svg>
                        </button>
                        <div class="profile-dropdown">
                            <p class="profile-name">
                                <span><sec:authentication property="principal.user.username"/></span>님 안녕하세요!
                            </p>
                            <div class="profile-divider"></div>
                            <a href="#" class="profile-link">프로필 설정</a>
                            <a href="#" class="profile-link">결제 내역</a>
                            <a href="#" class="profile-link">고객센터</a>
                            <a href="/logout" class="profile-link">로그아웃</a>
                        </div>
                    </div>
                </div>
            </sec:authorize>

            <!-- 어드민 사용자 -->
            <sec:authorize access="hasRole('ADMIN')">
                <div class="user-menu">
                    <a href="/mypage.do?tap=my" class="menu-item">관리자 페이지</a>
                    <div class="menu-divider"></div>
                    <div class="profile-section">
                        <button class="profile-toggle">
                            내 정보
                            <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="chevron">
                                <polyline points="6 9 12 15 18 9"></polyline>
                            </svg>
                        </button>
                        <div class="profile-dropdown">
                            <p class="profile-name">
                                <span><sec:authentication property="principal.user.username"/></span>님 안녕하세요!
                            </p>
                            <div class="profile-divider"></div>
                            <a href="#" class="profile-link">프로필 설정</a>
                            <a href="#" class="profile-link">결제 내역</a>
                            <a href="#" class="profile-link">고객센터</a>
                            <a href="/logout" class="profile-link">로그아웃</a>
                        </div>
                    </div>
                </div>
            </sec:authorize>

            <!-- 비로그인 사용자 -->
            <sec:authorize access="isAnonymous()">
                <a href="/auth/login" class="btn-login">로그인</a>
                <a href="/auth/signup" class="btn-join">회원가입</a>
            </sec:authorize>

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

        <!-- 비로그인 -->
        <sec:authorize access="isAnonymous()">
            <a href="/auth/login">로그인</a>
            <a href="/auth/signup">회원가입</a>
        </sec:authorize>

        <!-- 로그인 -->
        <sec:authorize access="isAuthenticated()">
            <a href="/mypage.do?tap=my">마이페이지</a>
            <a href="/logout">로그아웃</a>
        </sec:authorize>
    </div>
</nav>
