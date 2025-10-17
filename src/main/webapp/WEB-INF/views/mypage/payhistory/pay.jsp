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
                            <div class="table-row">
                                <span>202510150001</span>
                                <span>2025-10-01 ~ 2025-11-01</span>
                                <span>왕초보 일본어 회화</span>
                                <span>30,000원</span>
                            </div>

                            <div class="table-row">
                                <span>202510150002</span>
                                <span>2025-09-15 ~ 2025-10-15</span>
                                <span>영어 발음 완성반</span>
                                <span>25,000원</span>
                            </div>

                            <div class="table-row">
                                <span>202510150003</span>
                                <span>2025-08-01 ~ 2025-09-01</span>
                                <span>중국어 입문 코스</span>
                                <span>68,000원</span>
                            </div>

                    </div>
                </div>
            </section>
        </section>
    </div>
</main>

    <%@include file="../../common/footer.jsp" %>

    </body>
    </html>
