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
            <img src="/img/icon/x-circle.svg" alt="결제 실패 아이콘">
        </div>

        <h2 class="fail-title">결제에 실패했습니다</h2>
        <p class="fail-subtitle">다시 시도해 주시기 바랍니다</p>

        <section class="fail-summary">
            <h3>실패 정보</h3>
            <div class="summary-box">
                <c:if test="${not empty orderId}">
                    <div class="row">
                        <span class="label">주문번호</span>
                        <span class="value">${orderId}</span>
                    </div>
                </c:if>
                <c:if test="${not empty errorCode}">
                    <div class="row">
                        <span class="label">오류 코드</span>
                        <span class="value error">${errorCode}</span>
                    </div>
                </c:if>
                <div class="row">
                    <span class="label">오류 메시지</span>
                    <span class="value error">
                        <c:choose>
                            <c:when test="${not empty errorMessage}">
                                ${errorMessage}
                            </c:when>
                            <c:otherwise>
                                알 수 없는 오류가 발생했습니다.
                            </c:otherwise>
                        </c:choose>
                    </span>
                </div>
            </div>
        </section>

        <section class="help-section">
            <h3>이런 경우를 확인해보세요</h3>
            <ul class="help-list">
                <li>카드 한도를 초과했거나 잔액이 부족한 경우</li>
                <li>카드 정보를 잘못 입력한 경우</li>
                <li>해외 결제가 차단된 카드인 경우</li>
                <li>본인 인증에 실패한 경우</li>
                <li>네트워크 연결이 불안정한 경우</li>
            </ul>
        </section>

        <section class="contact-section">
            <h3>문제가 계속되시나요?</h3>
            <div class="contact-box">
                <div class="contact-item">
                    <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24"
                         fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                        <path d="M22 16.92v3a2 2 0 0 1-2.18 2 19.79 19.79 0 0 1-8.63-3.07 19.5 19.5 0 0 1-6-6 19.79 19.79 0 0 1-3.07-8.67A2 2 0 0 1 4.11 2h3a2 2 0 0 1 2 1.72 12.84 12.84 0 0 0 .7 2.81 2 2 0 0 1-.45 2.11L8.09 9.91a16 16 0 0 0 6 6l1.27-1.27a2 2 0 0 1 2.11-.45 12.84 12.84 0 0 0 2.81.7A2 2 0 0 1 22 16.92z"></path>
                    </svg>
                    <div>
                        <p class="contact-label">고객센터</p>
                        <p class="contact-value">1234-5678</p>
                    </div>
                </div>
                <div class="contact-item">
                    <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24"
                         fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                        <circle cx="12" cy="12" r="10"></circle>
                        <polyline points="12 6 12 12 16 14"></polyline>
                    </svg>
                    <div>
                        <p class="contact-label">운영시간</p>
                        <p class="contact-value">평일 09:00 ~ 18:00</p>
                    </div>
                </div>
                <div class="contact-item">
                    <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24"
                         fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                        <path d="M4 4h16c1.1 0 2 .9 2 2v12c0 1.1-.9 2-2 2H4c-1.1 0-2-.9-2-2V6c0-1.1.9-2 2-2z"></path>
                        <polyline points="22,6 12,13 2,6"></polyline>
                    </svg>
                    <div>
                        <p class="contact-label">이메일</p>
                        <p class="contact-value">support@mulang.com</p>
                    </div>
                </div>
            </div>
        </section>

        <div class="button-wrap">
            <button type="button" class="btn-outline" onclick="history.back()">
                다시 결제하기
            </button>
            <a href="/courses" class="btn-main">강좌 목록 보기</a>
        </div>

    </div>
</main>

<%@include file="../common/footer.jsp" %>

<script>
    // 결제 실패 이벤트 로깅 및 추적
    window.addEventListener('load', function() {
        console.error('결제 실패:', {
            orderId: '${orderId}',
            errorCode: '${errorCode}',
            errorMessage: '${errorMessage}'
        });

        // TODO: Google Analytics 또는 기타 분석 도구 연동
        // gtag('event', 'exception', {
        //     description: '결제 실패: ${errorCode}',
        //     fatal: false
        // });
    });
</script>
</body>
</html>
