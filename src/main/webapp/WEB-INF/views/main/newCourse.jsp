<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
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
    <link rel="stylesheet" href="/css/pages/course/course-list.css"/>
    <title>Mulang?</title>
</head>
<body>
<%@include file="../common/header.jsp" %>
<main class="main">
    <div class="contents">
        <div class="course-list-contents">
            <section class="course-category">
                <h1>신규 클래스</h1>
            </section>
            <section class="course-list">
                <c:forEach var="course" items="${courses}">
                    <div class="course-card">
                        <a href="courseDetail?id=${course.id}">
                            <img src="${course.thumbnail}" alt="course">
                        </a>
                        <div class="course-list-info">
                            <h2><a href="courseDetail?id=${course.id}">${course.title}</a></h2>
                            <p class="subtitle">${course.subtitle}</p>
                            <p class="teacher">${course.teacherName}</p>
                            <div class="rating">
                                <span class="score">${course.averageRating}</span>
                                <span class="stars">
                                    <c:forEach begin="1" end="5" var="i">
                                        <img src="/img/icon/star-${i <= course.averageRating ? 'full' : (i - 0.5 <= course.averageRating ? 'half' : 'empty')}.svg" alt="별">
                                    </c:forEach>
                                </span>
                                <span class="review-count">(${course.reviewCount})</span>
                            </div>
                        </div>
                        <div class="heart-purchase-wrap">
                            <div class="heart-icon" data-course-id="${course.id}">
                                <img src="${course.favorited ? '/img/icon/heart-full.svg' : '/img/icon/heart-empty.svg'}" alt="찜 아이콘">
                            </div>
                            <div class="course-purchase">
                                <span class="price"><fmt:formatNumber value="${course.price}" type="number" groupingUsed="true"/>원</span>
                                <a href="/payments/${course.id}"><button class="purchase-btn">결제하기</button></a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </section>
        </div>
    </div>
</main>
<%@include file="../common/footer.jsp" %>
<script src="/js/pages/course/courseFavorite.js"></script>
</body>
</html>