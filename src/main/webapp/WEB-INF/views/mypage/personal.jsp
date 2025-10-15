
<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<meta charset="UTF-8">
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="icon" href="img/favicon.svg" type="image/png">

    <link rel="stylesheet" href="/css/global.css"/>
    <link rel="stylesheet" href="/css/pages/mypage/personal.css"/>
    <title>Mulang?</title>
</head>
<body>

<%@include file="../common/header.jsp" %>

<main>
    <div class="contents">
        <section class="mypage">
        <jsp:include page="/WEB-INF/views/mypage/sidebar.jsp" />

        <!-- 메인 콘텐츠 -->
        <section class="content">
            <h2>개인정보</h2>

            <div class="info-grid">
                <div class="info-labels">
                    <div>이름</div>
                    <div>이메일</div>
                    <div>생년월일</div>
                    <div>전화번호</div>
                    <div>비밀번호</div>
                </div>
                <div class="info-values">
                    <div>최윤서</div>
                    <div>071112yoonseo@naver.com</div>
                    <div>2007.11.12</div>
                    <div>010-5849-0283</div>
                    <div>************</div>
                </div>
            </div>
        </section>
    </section>
    </div>
</main>

<%@include file="../common/footer.jsp" %>

</body>
</html>
