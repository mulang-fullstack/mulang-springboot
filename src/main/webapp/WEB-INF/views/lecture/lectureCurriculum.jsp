<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="icon" href="img/favicon.svg" type="image/png">
    <link rel="stylesheet" href="css/global.css"/>
    <link rel="stylesheet" href="css/pages/lecture/lecture-curriculum.css"/>
    <title>Mulang?</title>
</head>
<body>
<%@include file="../common/header.jsp" %>
<main>
    <div class="contents">
        <div class="lecture-curriculum-contents">
            <%@include file="lectureInfo.jsp" %>
            <div class="lecture-tabs">
                <div class="tab" onclick="location.href='lectureDetail'">강의소개</div>
                <div class="tab active" onclick="location.href='lectureCurriculum'">커리큘럼</div>
                <div class="tab" onclick="location.href='lectureReview'">리뷰</div>
            </div>
            <div class="lecture-curriculum">
                <div class="curriculum-header">
                    <div class="no">강의 번호</div>
                    <div class="title header">제목</div>
                    <div class="time">영상 길이</div>
                </div>

                <c:forEach var="i" begin="1" end="20">
                    <div class="curriculum-row">
                        <div class="no">${i}</div>
                        <div class="title">Dining Out!</div>
                        <div class="time">00:12:43</div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</main>
<%@include file="../common/footer.jsp" %>
</body>
</html>