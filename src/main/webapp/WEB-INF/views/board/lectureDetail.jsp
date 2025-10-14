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
    <link rel="stylesheet" href="css/lecture-list.css"/>
    <title>Mulang?</title>
</head>
<body>
<%@include file="../common/header.jsp" %>
<main class="lecture-main">
    <h1 class="lecture-title">왕초보 기초회화</h1>

    <section class="lecture-banner">
        <img class="lecture-image" src="https://placehold.co/347x250" alt="강의 이미지">
        <div class="lecture-price">155,000원</div>
        <div class="price-circle"></div>
        <div class="pay-button">
            <span>결제하기</span>
        </div>
        <div class="lecture-summary">
            <p>틀에 박힌 한국식 영어는 이제 그만! - 찐 원어민 기초 회화!</p>
            <p>Sean Pablo 강사</p>
            <p>수강기간: 2025.11.01.~2025.12.31.</p>
            <p>신청기간: 2025.10.15.~2025.10.31.</p>
            <p>강의 수: 20강</p>
        </div>
    </section>

    <!-- 탭 -->
    <nav class="lecture-tabs">
        <div class="tab active">강의소개</div>
        <div class="tab">커리큘럼</div>
        <div class="tab">리뷰</div>
    </nav>

    <!-- 강의 소개 -->
    <section class="lecture-info">
        <h2 class="lecture-subtitle">왕초보 기초회화</h2>
        <p class="lecture-description">
            틀에 박힌 한국식 영어는 이제 그만!
            유학파 및 원어민 강사가 알려주는 신비한 뉘앙스의 세계- 찐 원어민 회화 패키지!
            어렵게만 느껴지는 스몰톡! 자신감이 필요하다면?
            필수 Expressions & Vocabulary 를 자연스럽게 적용한 대화문을 온전히 내 것으로!
            현실감 최고인 Role-Play 대화문 속 유용한 Expressions를 반복 연습해보세요.
            [강의 구성]
            - Warm up Quiz
            해당 강의에서 다룰 여러 문장 중에 가장 중요한 한 문장을 소개하며 무슨 뜻일 지 유추해보는 워밍업 시간
            - Today’s Expression
            해당 강의 속 스몰톡 대화문에 등장 할 주요 패턴을 미리 파악하고 덤으로 추가 예시 문장도 배우는 시간
            - Role Play, Conversation 등
        </p>
    </section>
</main>
<%@include file="../common/footer.jsp" %>
</body>
</html>