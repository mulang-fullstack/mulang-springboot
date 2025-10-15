<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="icon" href="/img/favicon.svg" type="image/png">

    <link rel="stylesheet" href="/css/global.css"/>
    <link rel="stylesheet" href="/css/pages/teacher/settlement.css"/>

    <title>정산 현황 | Mulang?</title>
</head>
<body>

<%@ include file="../common/header.jsp" %>

<main>
    <div class="contents">

        <section class="dashboard">
            <%@ include file="sidebar.jsp" %>

            <section class="content">
                <h2>정산 현황</h2>

                <!-- 정산 요약 -->
                <div class="settlement-summary">
                    <div class="summary-box">
                        <h3>정산 가능한 금액</h3>
                        <p class="amount">0원 <span>(0건)</span></p>
                    </div>
                    <div class="summary-box">
                        <h3>정산 신청한 금액</h3>
                        <p class="amount">0원 <span>(0건)</span></p>
                    </div>
                </div>

                <!-- 정산 액션 -->
                <div class="action-bar">
                    <button class="apply-btn" disabled>정산 요청</button>
                    <button class="pending-btn" disabled>정산 미신청만 보기</button>
                </div>

                <!-- 정산 테이블 -->
                <div class="settlement-table">
                    <div class="table-header">
                        <span>신청서번호</span>
                        <span>정산 상태</span>
                        <span>클래스명</span>
                        <span>정산 대상 금액</span>
                    </div>

                    <div class="table-body">
                        <div class="empty">데이터가 없습니다.</div>
                    </div>
                </div>
            </section>
        </section>

    </div>
</main>

<%@ include file="../common/footer.jsp" %>
</body>
</html>
