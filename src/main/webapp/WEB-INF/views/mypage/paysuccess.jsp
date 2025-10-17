<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="icon" href="/img/favicon.svg" type="image/png">

    <link rel="stylesheet" href="/css/global.css"/>
    <link rel="stylesheet" href="/css/pages/mypage/paySuccess.css"/>

    <title>결제 완료 | Mulang</title>
</head>
<body>

<%@include file="../common/header.jsp" %>

<main>
    <div class="pay-success">

        <div class="success-icon">
            <img src="/img/icon/check-circle.svg" alt="결제 완료 아이콘">
        </div>

        <h2 class="success-title">결제가 완료되었습니다.</h2>
        <p class="success-subtitle">이용해 주셔서 감사합니다!</p>

        <section class="pay-summary">
            <h3>결제 정보</h3>
            <div class="summary-box">
                <div class="row">
                    <span class="label">클래스명</span>
                    <span class="value">${className}</span>
                </div>
                <div class="row">
                    <span class="label">결제 금액</span>
                    <span class="value point">${price}원</span>
                </div>
                <div class="row">
                    <span class="label">결제 수단</span>
                    <span class="value">${paymentMethod}</span>
                </div>
                <div class="row">
                    <span class="label">결제 일시</span>
                    <span class="value">${paymentDate}</span>
                </div>
            </div>
        </section>

        <div class="button-wrap">
            <a href="/mypage/pay/history" class="btn-outline">결제내역 보기</a>
            <a href="/" class="btn-main">홈으로 가기</a>
        </div>

    </div>
</main>

<%@include file="../common/footer.jsp" %>
</body>
</html>
