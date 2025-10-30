<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="icon" href="/img/favicon.svg" type="image/png">

    <link rel="stylesheet" href="/css/global.css"/>
    <link rel="stylesheet" href="/css/pages/payment/paySuccess.css"/>

    <title>결제 완료 | Mulang</title>
</head>
<body>

<%@include file="../common/header.jsp" %>

<main>
    <div class="pay-success">

        <div class="success-icon">
            <img src="/img/icon/check-circle.svg" alt="결제 완료 아이콘" onerror="this.style.display='none';">
            <!-- 이미지가 없을 경우를 대비한 SVG -->
            <svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" viewBox="0 0 24 24"
                 fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                 style="display: none;" id="fallback-icon">
                <path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"></path>
                <polyline points="22 4 12 14.01 9 11.01"></polyline>
            </svg>
        </div>

        <h2 class="success-title">결제가 완료되었습니다 🎉</h2>
        <p class="success-subtitle">이용해 주셔서 감사합니다!</p>

        <section class="pay-summary">
            <h3>결제 정보</h3>
            <div class="summary-box">
                <div class="row">
                    <span class="label">강좌명</span>
                    <span class="value">${payment.courseTitle}</span>
                </div>
                <div class="row">
                    <span class="label">결제 금액</span>
                    <span class="value point">
                        <fmt:formatNumber value="${payment.amount}" pattern="#,###"/>원
                    </span>
                </div>
                <c:if test="${not empty payment.paymentMethod}">
                    <div class="row">
                        <span class="label">결제 수단</span>
                        <span class="value">
                            <c:choose>
                                <c:when test="${payment.paymentMethod == 'CARD'}">카드</c:when>
                                <c:when test="${payment.paymentMethod == 'TOSSPAY'}">토스페이</c:when>
                                <c:when test="${payment.paymentMethod == 'KAKAOPAY'}">카카오페이</c:when>
                                <c:when test="${payment.paymentMethod == 'MOBILE_PHONE'}">휴대폰</c:when>
                                <c:otherwise>카드</c:otherwise>
                            </c:choose>

                            <c:if test="${not empty payment.paymentMethodDetail}">
                                (${payment.paymentMethodDetail})
                            </c:if>
                        </span>
                    </div>
                </c:if>
                <div class="row">
                    <span class="label">주문번호</span>
                    <span class="value" style="font-size: 13px; color: var(--text-tertiary);">
                        ${payment.orderId}
                    </span>
                </div>
                <c:if test="${not empty payment.approvedAt}">
                    <div class="row">
                        <span class="label">결제 일시</span>
                        <span class="value">${payment.approvedAt}</span>
                    </div>
                </c:if>
            </div>
        </section>

        <div class="button-wrap">
            <a href="/mypage/enrollments" class="btn-outline">내 강좌 보기</a>
            <a href="/" class="btn-main">홈으로 가기</a>
        </div>

    </div>
</main>

<%@include file="../common/footer.jsp" %>

<script>
    // 체크 아이콘 이미지 로드 실패 시 SVG 폴백
    document.querySelector('.success-icon img').addEventListener('error', function() {
        this.style.display = 'none';
        document.getElementById('fallback-icon').style.display = 'block';
    });
</script>

</body>
</html>
