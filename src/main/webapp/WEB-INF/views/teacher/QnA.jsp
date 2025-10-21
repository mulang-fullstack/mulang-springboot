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

    <link rel="stylesheet" href="/css/pages/teacher/QnA.css"/>
    <title>리뷰 · Q&A | Mulang?</title>
</head>
<body>

<%@ include file="../common/header.jsp" %>

<main>
    <div class="contents">
        <section class="dashboard"><%@ include file="sidebar.jsp" %>
            <section class="content">
                <h2>Q&A</h2>

                <div class="filter-bar">
                    <select class="sort-select">
                        <option>최신순</option>
                        <option>오래된순</option>
                    </select>
                </div>

                <div class="review-list">
                    <div class="empty">데이터가 없습니다.</div>
                </div>
            </section>
        </section>
    </div>
</main>

<%@ include file="../common/footer.jsp" %>
</body>
</html>
