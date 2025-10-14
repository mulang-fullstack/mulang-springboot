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
    <link rel="stylesheet" href="/css/personal.css">
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
        <!-- 메인 콘텐츠 -->
        <section class="content">
            <h2>개인정보</h2>
            <div class="info-grid">
                <div class="info-labels">
                    <div>이름</div>
                    <div>생년월일</div>
                    <div>이메일</div>
                    <div>비밀번호</div>
                    <div>전화번호</div>
                    <div>별명</div>
                </div>
                <div class="info-values">
                    <div>최윤서</div>
                    <div>2007.11.12</div>
                    <div>071112yoonseo@naver.com</div>
                    <div>*********************</div>
                    <div>010-5849-0283</div>
                    <div>파워껌담</div>
                </div>
            </div>
            <div class="btn-area">
                <button class="btn edit">수정</button>
                <button class="btn photo">사진수정</button>
            </div>
        </section>
    </section>
</main>

</body>



</html>
