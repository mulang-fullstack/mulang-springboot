<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="icon" href="/img/favicon.svg" type="image/png">

    <link rel="stylesheet" href="/css/global.css"/>
    <link rel="stylesheet" href="/css/pages/mypage/like/save.css"/>
    <title>찜</title>
</head>
<body>

<%@include file="../../common/header.jsp" %>

<main>
    <div class="contents">
        <section class="mypage">

            <!-- 사이드바 -->
            <%@ include file="../sidebar.jsp" %>

            <!-- 메인 컨텐츠 -->
            <section class="content">
                <h2>찜</h2>

                <!-- 툴바(검색/정렬) 필요시 -->
                <div class="likes-toolbar">
                    <div class="left">
                        <select class="sel-subject" id="subjectFilter" onchange="filterBySubject()">
                            <option value="0">전체 과목</option>  <!-- ⭐ value="" (빈 문자열) -->
                            <option value="1" ${languageId == 1 ? 'selected' : ''}>영어</option>
                            <option value="2" ${languageId == 2 ? 'selected' : ''}>중국어</option>
                            <option value="3" ${languageId == 3 ? 'selected' : ''}>일본어</option>
                        </select>

                    </div>
                    <div class="right">
                        <form id="searchForm" onsubmit="return searchCourse(event)">
                            <input type="text"
                                   id="searchInput"
                                   class="search-input"
                                   placeholder="강좌명 검색"
                                   value="${keyword}">
                            <button type="submit" class="btn search">검색</button>
                        </form>
                    </div>

                </div>

                <!-- 찜 목록 -->
                <div class="likes-list">
                    <c:forEach var="favorite" items="${favorites}">
                        <article class="like-item" data-favorite-id="${favorite.id}">
                            <div class="thumb">
                                <img src="${favorite.thumbnailUrl}" alt="${favorite.course.title}">
                            </div>

                            <div class="meta">
                                <div class="topline">
                                    <span class="teacher">담당강사:${favorite.course.teacher.user.username}</span>
                                    <span class="subject">과목:${favorite.course.language.name}</span>
                                </div>
                                <h3 class="title">${favorite.course.title}</h3>
                                <p class="sub">${favorite.course.subtitle}</p>
                                <div class="period">
                                    <c:set var="dateStr" value="${fn:substring(favorite.createdAt, 0, 16)}" />
                                    <c:set var="formatted" value="${fn:replace(dateStr, 'T', ' ')}" />
                                    <c:set var="formatted" value="${fn:replace(formatted, '-', '.')}" />
                                    📌 ${formatted}
                                </div>
                            </div>

                            <div class="actions">
                                <button class="btn outline"
                                        onclick="removeFavorite(${favorite.course.id}, ${favorite.id})">
                                    찜 해제
                                </button>
                                <button class="btn edit" onclick="location.href='/payments/${favorite.course.id}'">결제하기</button>
                            </div>
                        </article>
                    </c:forEach>

                    <!-- 찜한 강좌가 없을 때 -->
                    <c:if test="${empty favorites}">
                        <p style="text-align: center; padding: 50px;">찜한 강좌가 없습니다.</p>
                    </c:if>
                </div>

                <!-- 페이지네이션(옵션) -->

                    <!-- 페이지네이션 -->
                    <c:if test="${totalPages > 1}">
                        <div class="pagination">
                            <c:if test="${currentPage > 0}">
                                <a href="?page=${currentPage - 1}" class="page-btn">이전</a>
                            </c:if>

                            <c:forEach begin="0" end="${totalPages - 1}" var="i">
                                <a href="?page=${i}" class="page-btn ${currentPage == i ? 'active' : ''}">
                                        ${i + 1}
                                </a>
                            </c:forEach>

                            <c:if test="${currentPage < totalPages - 1}">
                                <a href="?page=${currentPage + 1}" class="page-btn">다음</a>
                            </c:if>
                        </div>
                    </c:if>

                </div>
            </section>
        </section>
</main>
<%@include file="../../common/footer.jsp" %>

<!-- JS 파일 추가 -->
<script src="/js/pages/mypage/savecancel.js"></script>
</body>
</html>
