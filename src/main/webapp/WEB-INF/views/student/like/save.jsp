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
                        <select class="sel-subject">
                            <option>전체 과목</option>
                            <option>영어</option>
                            <option>일본어</option>
                            <option>중국어</option>
                        </select>
                    </div>
                    <div class="right">
                        <input type="text" class="search-input" placeholder="강좌명 검색">
                        <button class="btn search">검색</button>
                    </div>
                </div>

                <!-- 찜 목록 -->
                <div class="likes-list">
                    <c:forEach var="favorite" items="${favorites}">
                        <article class="like-item" data-favorite-id="${favorite.id}">
                            <div class="thumb">
                                <img src="${favorite.course.thumbnail}" alt="${favorite.course.title}">
                            </div>

                            <div class="meta">
                                <div class="topline">
                                    <span class="teacher">담당강사: -</span>
                                    <span class="subject">-</span>
                                </div>
                                <h3 class="title">${favorite.course.title}</h3>
                                <p class="sub">${favorite.course.subtitle}</p>
                                <div class="period">
                                    찜한 날짜: ${favorite.createdAt}
                                </div>
                            </div>

                            <div class="actions">
                                <button class="btn outline"
                                        onclick="removeFavorite(${favorite.course.id}, ${favorite.id})">
                                    찜 해제
                                </button>
                                <button class="btn edit">수강하기</button>
                            </div>
                        </article>
                    </c:forEach>

                    <!-- 찜한 강좌가 없을 때 -->
                    <c:if test="${empty favorites}">
                        <p style="text-align: center; padding: 50px;">찜한 강좌가 없습니다.</p>
                    </c:if>
                </div>

                <!-- 페이지네이션(옵션) -->
                <div class="pagination">
                    <button class="page-btn active">1</button>
                    <button class="page-btn">2</button>
                    <button class="page-btn">3</button>
                </div>
            </section>
        </section>
    </div>
</main>
<%@include file="../../common/footer.jsp" %>

<!-- JS 파일 추가 -->
<script src="/js/pages/mypage/savecancel.js"></script>
</body>
</html>
