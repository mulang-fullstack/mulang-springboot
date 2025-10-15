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
    <link rel="stylesheet" href="/css/pages/mypage/review.css"/>
    <title>리뷰 · Q&A</title>
</head>
<body>

<%@include file="../common/header.jsp" %>

<main class="main">
    <section class="dashboard">

        <%@include file="sidebar.jsp" %>

        <section class="content">
            <h2>리뷰 · Q&A</h2>

            <div class="tab-menu">
                <button class="tab active">리뷰</button>
                <button class="tab">Q&A</button>
            </div>

            <div class="filter-bar">
                <select class="sort-select">
                    <option>최신순</option>
                    <option>오래된순</option>
                </select>
                    <button class="apply-btn">정산 요청</button>

            </div>


            <div class="review-list">
                <div class="empty">데이터가 없습니다.</div>
            </div>
        </section>
    </section>
</main>

<%@include file="../common/footer.jsp" %>

</body>
</html>
