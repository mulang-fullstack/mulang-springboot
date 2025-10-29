<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="icon" href="/img/favicon.svg" type="image/png">

    <link rel="stylesheet" href="/css/global.css"/>
    <link rel="stylesheet" href="/css/pages/mypage/payhistory/pay.css"/>

    <title>결제내역</title>
</head>
<body>

<%@include file="../../common/header.jsp" %>

<main>
    <div class="contents">
        <section class="mypage">

            <%@include file="../sidebar.jsp" %>

            <section class="content">
                <h2>결제내역</h2>

                <!-- 정산 요약 -->
                <div class="settlement-summary">
                    <div class="summary-box">
                        <h3>총 결제 금액</h3>
                        <p class="amount">
                            <fmt:formatNumber value="${totalamount}" type="number" groupingUsed="true"/>원
                            <span>(${totalsize}건)</span>
                        </p>
                    </div>
                </div>

                <!-- 정산 테이블 -->
                <div class="settlement-table">
                    <div class="table-header">
                        <span>주문번호</span>
                        <span>강의명</span>
                        <span>결제수단</span>
                        <span>결제금액</span>
                    </div>

                    <div class="table-body">
                        <c:forEach var="payment" items="${paymentResponseList}">
                            <div class="table-row">
                                <!-- 주문번호 -->
                                <span>${payment.paymentId}</span>

                                <!-- 강의명 -->
                                <span>${payment.course.title}</span>

                                <!-- 결제수단-->
                                <span>${payment.paymentMethod}</span>

                                <!-- 결제 금액 -->
                                <span>
                                    <fmt:formatNumber value="${payment.course.price}" type="number" groupingUsed="true"/>원
                                </span>
                            </div>
                        </c:forEach>

                        <!-- 결제 내역이 없을 때 -->
                        <c:if test="${empty paymentResponseList}">
                            <div class="table-row empty-message">
                                <span style="text-align: center; width: 100%; padding: 40px 0; color: #999;">
                                    결제 내역이 없습니다.
                                </span>
                            </div>
                        </c:if>
                    </div>
                </div>
            </section>
        </section>
    </div>
</main>

<%@include file="../../common/footer.jsp" %>

</body>
</html>
