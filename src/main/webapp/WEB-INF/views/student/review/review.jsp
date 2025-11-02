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
    <link rel="stylesheet" href="/css/pages/mypage/review/review.css"/>
    <title>리뷰</title>
</head>
<body>

<%@include file="../../common/header.jsp" %>

<main>
    <div class="contents">
        <section class="mypage">

            <%@include file="../sidebar.jsp" %>

            <section class="content">
                <h2>리뷰</h2>

                <!-- 필터 영역 -->
                <div class="filter-bar">
                    <select class="sort-select">
                        <option value="최신순" ${currentSort == '최신순' ? 'selected' : ''}>최신순</option>
                        <option value="오래된순" ${currentSort == '오래된순' ? 'selected' : ''}>오래된순</option>
                    </select>
                    <button type="button" class="apply-btn" onclick="location.href='/student/reviewwrite'">작성하기</button>
                </div>

                <!-- 성공/에러 메시지 -->
                <c:if test="${not empty message}">
                    <div class="success-message">${message}</div>
                </c:if>
                <c:if test="${not empty error}">
                    <div class="error-message">${error}</div>
                </c:if>

                <!-- 리뷰 리스트 -->
                <div class="qna-list">
                    <c:choose>
                        <c:when test="${empty myReviews}">
                            <div class="empty">리뷰가 없습니다.</div>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="review" items="${myReviews}">
                                <!-- ⭐ 클릭 가능하도록 수정 -->
                                <div class="review-item" onclick="location.href='/student/review/${review.id}'" style="cursor: pointer;">
                                    <!-- 강좌 정보와 날짜 -->
                                    <div class="review-header">
                                        <h3 class="course-title">${review.course.title}</h3>
                                        <span class="review-date">
                                                ${review.createdAt.toString().substring(0, 16).replace('T', ' ')}
                                        </span>
                                    </div>

                                    <!-- 별점과 내용을 가로로 배치 -->
                                    <div class="review-body">
                                        <!-- 별점 -->
                                        <div class="review-rating">
                                            <c:forEach begin="1" end="5" var="i">
                                                <span class="star ${i <= review.rating ? 'filled' : ''}">★</span>
                                            </c:forEach>
                                            <span class="rating-text">${review.rating}점</span>
                                        </div>

                                        <!-- 리뷰 내용 -->
                                        <div class="review-content">
                                            <p>${review.content}</p>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>

                </div>
            </section>
        </section>
    </div>
</main>

<script src="/js/pages/mypage/review.js"></script>
<%@include file="../../common/footer.jsp" %>

</body>
</html>
