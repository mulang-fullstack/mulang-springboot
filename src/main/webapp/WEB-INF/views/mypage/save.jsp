<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="icon" href="img/favicon.svg" type="image/png">
    <link rel="stylesheet" href="/css/global.css">
    <link rel="stylesheet" href="/css/save.css">
    <title>마이페이지</title>
</head>
<body>

<header class="header">
    <div class="nav-container">
        <div class="logo"></div>
        <nav class="nav-menu">
            <a href="#">영어</a>
            <a href="#">일본어</a>
            <a href="#">중국어</a>
            <a href="#">AI테스트</a>
        </nav>
        <div class="login-btn">로그인</div>
    </div>
</header>

<main class="main">
    <section class="mypage">
        <!-- 사이드바 -->
        <jsp:include page="/WEB-INF/views/mypage/sidebar.jsp" />

        <!-- 메인 콘텐츠: 찜 목록 -->
        <section class="content">
            <h2>찜</h2>

            <div class="course-list">
                <!-- 카드 1 -->
                <article class="course-card">
                    <img class="thumb" src="/img/teacher1.png" alt="강사 썸네일">
                    <div class="item-body">
                        <h3 class="title">가장 빠르게 만드는 JLPT</h3>
                        <p class="sub">일본어 · 최윤서 · 강의수 35/35</p>
                        <div class="meta">
                            <span class="dot"></span> 수강 가능 기간: 25.10.13 ~ 25.12.31
                        </div>
                        <div class="actions">
                            <button class="btn small">수강증 발급</button>
                            <button class="btn edit small">수강하기</button>
                        </div>
                    </div>
                </article>

                <!-- 카드 2 -->
                <article class="course-card">
                    <img class="thumb" src="/img/teacher2.png" alt="강사 썸네일">
                    <div class="item-body">
                        <h3 class="title">국어 핵심 문항 실전</h3>
                        <p class="sub">국어 · 한병훈 · 강의수 22/22</p>
                        <div class="meta">
                            <span class="dot"></span> 수강 가능 기간: 25.10.13 ~ 25.12.31
                        </div>
                        <div class="actions">
                            <button class="btn small">수강증 발급</button>
                            <button class="btn edit small">수강하기</button>
                        </div>
                    </div>
                </article>

                <!-- 카드 3 (예시) -->
                <article class="course-card">
                    <img class="thumb" src="/img/teacher3.png" alt="강사 썸네일">
                    <div class="item-body">
                        <h3 class="title">중국어 회화 스타터</h3>
                        <p class="sub">중국어 · 장미남 · 강의수 18/20</p>
                        <div class="meta">
                            <span class="dot"></span> 수강 가능 기간: 25.11.01 ~ 26.01.15
                        </div>
                        <div class="actions">
                            <button class="btn small">수강증 발급</button>
                            <button class="btn edit small">수강하기</button>
                        </div>
                    </div>
                </article>
            </div>
        </section>
    </section>
</main>

</body>
</html>
