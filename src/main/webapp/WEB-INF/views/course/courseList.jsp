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
                <h1>${languageName}</h1>
                <div class="course-tabs">
                    <a href="/course" class="tab ${empty courseListRequest.categoryId ? 'active' : ''}">
                        전체
                    </a>
                    <c:forEach var="category" items="${categories}">
                        <a href="/course?categoryId=${category.id}" class="tab ${courseListRequest.categoryId == category.id ? 'active' : ''}">
                                ${category.name}
                        </a>
                    </c:forEach>
                </div>
            </section>

            <section class="course-sort">
                <div class="search-box">
                    <!-- 검색박스 내부 요소들 -->
                    <div class="search-input">
                        <form action="/course" method="get">
                            <input type="hidden" name="categoryId" value="${courseListRequest.categoryId}">
                            <input type="text" name="keyword" value="${courseListRequest.keyword}">
                            <button type="submit">
                                <img src="/img/icon/search.svg" alt="검색 아이콘">
                            </button>
                        </form>
                    </div>
                    <div class="search-icon"></div>
                </div>
                <div class="sort-options">
                    <a href="/course?categoryId=${courseListRequest.categoryId}&keyword=${courseListRequest.keyword}&page=${courseListRequest.sortBy != 'averageRating' ? 0 : courseListRequest.page}&sortBy=averageRating">
                        <div class="sort-item ${courseListRequest.sortBy == 'averageRating'? 'active':''}">별점순</div>
                    </a>
                    <a href="/course?categoryId=${courseListRequest.categoryId}&keyword=${courseListRequest.keyword}&page=${courseListRequest.sortBy != 'reviewCount' ? 0 : courseListRequest.page}&sortBy=reviewCount">
                        <div class="sort-item ${courseListRequest.sortBy == 'reviewCount'? 'active':''}">리뷰순</div>
                    </a>
                    <a href="/course?categoryId=${courseListRequest.categoryId}&keyword=${courseListRequest.keyword}&page=${courseListRequest.sortBy != 'createdAt' ? 0 : courseListRequest.page}&sortBy=createdAt">
                        <div class="sort-item ${courseListRequest.sortBy == 'createdAt'? 'active':''}">최신순</div>
                    </a>
                </div>
            </section>

            <section class="course-list">
                <c:choose>
                    <c:when test="${not empty courses}">
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
                    </c:when>
                    <c:otherwise>
                        <div class="empty-content">
                        <img src="/img/dummy/empty-content.jpg" alt="강좌 없음">
                        </div>
                    </c:otherwise>
                </c:choose>
            </section>

            <!-- 페이지네이션 -->
            <section class="pagination">
                <c:if test="${courseListRequest.page > 0}">
                    <c:url var="prevUrl" value="/course">
                        <c:param name="categoryId" value="${courseListRequest.categoryId}" />
                        <c:param name="keyword" value="${courseListRequest.keyword}" />
                        <c:param name="page" value="${courseListRequest.page - 1}" />
                    </c:url>
                    <a href="${prevUrl}">
                        <button class="prev"><img src="/img/icon/page-left.svg" alt="왼쪽 아이콘"></button>
                    </a>
                </c:if>
                <span class="page-numbers">
                    <c:if test="${totalPages > 0}">
                        <c:forEach begin="0" end="${totalPages-1}" var="i">
                            <c:url var="pageUrl" value="/course">
                                <c:param name="categoryId" value="${courseListRequest.categoryId}" />
                                <c:param name="keyword" value="${courseListRequest.keyword}" />
                                <c:param name="page" value="${i}" />
                            </c:url>
                            <a href="${pageUrl}">
                                <span class="${i == courseListRequest.page ? 'current' : ''}">${i+1}</span>
                            </a>
                        </c:forEach>
                    </c:if>
                </span>
                <c:if test="${courseListRequest.page < totalPages-1}">
                    <c:url var="nextUrl" value="/course">
                        <c:param name="categoryId" value="${courseListRequest.categoryId}" />
                        <c:param name="keyword" value="${courseListRequest.keyword}" />
                        <c:param name="page" value="${courseListRequest.page + 1}" />
                    </c:url>
                    <a href="${nextUrl}">
                        <button class="next"><img src="/img/icon/page-right.svg" alt="오른쪽 아이콘"></button>
                    </a>
                </c:if>
            </section>
        </div>
    </div>
</main>
<%@include file="../common/footer.jsp" %>
<script src="/js/pages/course/courseFavorite.js"></script>
</body>
</html>
