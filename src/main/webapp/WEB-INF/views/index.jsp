<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="icon" href="img/favicon.svg" type="image/png">
    <link rel="stylesheet" href="css/global.css"/>
    <link rel="stylesheet" href="css/index.css"/>
    <title>Mulang?</title>
</head>
<body>
<%@include file="common/header.jsp" %>
<main>
    <!-- 배너 영역 -->
    <%@include file="common/banner.jsp" %>
    <!-- 본문 -->
    <div class="contents">
        <!-- 카테고리 버튼 영역 -->
        <section class="category-menu">
            <a href="/ranking?languageId=1"><img src="/img/icon/main1.png"/>실시간 랭킹</a>
            <a href="/newCourse"><img src="/img/icon/main2.png"/>신규 클래스</a>
            <a href="/dailyConversation"><img src="/img/icon/main3.png"/>일상 회화</a>
            <a href="#"><img src="/img/icon/main4.png"/>자격증 시험</a>
            <a href="#"><img src="/img/icon/main5.png"/>레벨 테스트</a>
            <a href="#"><img src="/img/icon/main6.png"/>학습 현황</a>
        </section>

        <!-- BEST 인기 클래스 -->
        <section class="best-class">
            <h3>주간 BEST 인기 클래스</h3>
            <div class="class-list">
                <article class="class-card">
                    <img src="/img/dummy/teacher1.png" alt="영어 클래스 썸네일">
                    <div class="class-info">
                        <h4>톰에 박힌 한국식 영어는 이제 그만!</h4>
                        <p>⭐ 4.8 (1000)</p>
                        <p class="price">155,000원</p>
                    </div>
                </article>

                <article class="class-card">
                    <img src="/img/dummy/teacher2.png" alt="문법 토크쇼">
                    <div class="class-info">
                        <h4>하루 단 10분 실생활 영문법 토크쇼</h4>
                        <p>⭐ 4.8 (1000)</p>
                        <p class="price">155,000원</p>
                    </div>
                </article>

                <article class="class-card">
                    <img src="/img/dummy/teacher3.png" alt="문법 토크쇼">
                    <div class="class-info">
                        <h4>하루 단 10분 실생활 영문법 토크쇼</h4>
                        <p>⭐ 4.8 (1000)</p>
                        <p class="price">155,000원</p>
                    </div>
                </article>

                <article class="class-card">
                    <img src="/img/dummy/teacher1.png" alt="문법 토크쇼">
                    <div class="class-info">
                        <h4>하루 단 10분 실생활 영문법 토크쇼</h4>
                        <p>⭐ 4.8 (1000)</p>
                        <p class="price">155,000원</p>
                    </div>
                </article>
            </div>
        </section>
    </div>
</main>
<%@include file="common/footer.jsp" %>
</body>
</html>