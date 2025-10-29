<%@ page contentType="text/html;charset=utf-8" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
    <link rel="icon" href="img/favicon.svg" type="image/png">
    <link rel="stylesheet" href="/css/global.css"/>
    <link rel="stylesheet" href="/css/pages/course/course-detail.css"/>
    <link rel="stylesheet" href="/css/pages/course/course-review.css"/>
    <title>Mulang?</title>
</head>
<body>
<%@include file="../common/header.jsp" %>
<main data-course-id="${detail.id}">
    <div class="contents">
        <div class="leture-detail-contents">
             <%@include file="courseInfo.jsp" %>
            <div class="course-tabs">
                <div class="tab" data-target="introduction">강의소개</div>
                <div class="tab" data-target="curriculum">커리큘럼</div>
                <div class="tab" data-target="review">리뷰</div>
            </div>
        </div>
        <div id="introduction">
            <%@include file="courseIntroduction.jsp" %>
        </div>
        <div id="curriculum">
            <%@include file="courseCurriculum.jsp" %>
        </div>
        <div id="review">
            </br><h2>리뷰</h2>
            <!-- 리뷰 컨테이너 -->
            <div id="review-container">
            </div>
        </div>
    </div>
</main>
<%@include file="../common/footer.jsp" %>
<script src="/js/pages/course/courseDetailTab.js"></script>
<script src="/js/pages/course/courseReview.js"></script>
<script> const currentUserId = ${userId != null ? userId : 0};</script>
</body>
</html>
