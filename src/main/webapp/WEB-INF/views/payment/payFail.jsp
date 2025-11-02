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
    <link rel="stylesheet" href="/css/pages/payment/payFail.css"/>

    <title>결제 실패 | Mulang</title>
</head>
<body>

<%@include file="../common/header.jsp" %>

<main>
    <div class="pay-fail">

        <div class="fail-icon">
            <svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" viewBox="0 0 24 24"
                 fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <circle cx="12" cy="12" r="10"></circle>
                <line x1="15" y1="9" x2="9" y2="15"></line>
                <line x1="9" y1="9" x2="15" y2="15"></line>
            </svg>
        </div>

        <h2 class="fail-title">결제에 실패했습니다</h2>
        <p class="fail-subtitle">다시 시도해 주시기 바랍니다.</p>

        <section class="fail-summary">
            <h3>실패 정보</h3>
            <div class="summary-box">
                <c:if test="${not empty errorCode}">
                    <div class="row">
                        <span class="label">오류 코드</span>
                        <span class="value error">${errorCode}</span>
                    </div>
                </c:if>
                <c:if test="${not empty errorMessage}">
                    <div class="row">
                        <span class="label">오류 메시지</span>
                        <span class="value">${errorMessage}</span>
                    </div>
                </c:if>
                <c:if test="${not empty orderId}">
                    <div class="row">
                        <span class="label">주문번호</span>
                        <span class="value" style="font-size: 13px; color: var(--text-tertiary);">
                                ${orderId}
                        </span>
                    </div>
                </c:if>
            </div>

            <div class="help-box">
                <h4>📌 결제 실패 원인</h4>
                <ul>
                    <li>카드 한도 초과 또는 잔액 부족</li>
                    <li>카드 정보 입력 오류</li>
                    <li>결제 도중 취소</li>
                    <li>일시적인 네트워크 오류</li>
                </ul>
                <p class="help-text">
                    문제가 계속되면 고객센터로 문의해주세요.
                </p>
            </div>
        </section>

        <div class="button-wrap">
            <a href="javascript:history.back();" class="btn-outline">이전으로</a>
            <a href="/" class="btn-main">홈으로 가기</a>
        </div>

    </div>
</main>

<%@include file="../common/footer.jsp" %>

</body>
</html>
