<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="icon" href="/img/favicon.svg" type="image/png">

    <link rel="stylesheet" href="/css/global.css"/>
    <link rel="stylesheet" href="/css/pages/mypage/pay.css"/>

    <title>결제내역</title>
</head>
<body>

<%@include file="../common/header.jsp" %>

<main class="main">
    <section class="dashboard">

        <%@include file="sidebar.jsp" %>

        <section class="content">
            <h2>결제내역</h2>

            <!-- 정산 요약 -->
            <div class="settlement-summary">
                <div class="summary-box">
                    <h3>이번달 결제 금액</h3>
                    <p class="amount">123,000원 <span>(4건)</span></p>
                </div>
            </div>

          

            <!-- 정산 테이블 -->
            <div class="settlement-table">
                <div class="table-header">
                    <span>결제번호</span>
                    <span>강의 기한</span>
                    <span>강좌 이름</span>
                    <span>가격</span>
                </div>

                <div class="table-body">
                    <div class="empty">데이터가 없습니다.</div>
                </div>
            </div>
        </section>
    </section>
</main>

<%@include file="../common/footer.jsp" %>

</body>
</html>
