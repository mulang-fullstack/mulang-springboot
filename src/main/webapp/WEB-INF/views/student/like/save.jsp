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
                <!-- 아이템 1 -->
                <article class="like-item">
                    <div class="thumb">
                        <img src="${pageContext.request.contextPath}/img/dummy/teacher1.png" alt="강사 썸네일">
                    </div>

                    <div class="meta">
                        <div class="topline">
                            <span class="teacher">담당강사: 박지민</span>
                            <span class="subject">영어</span>
                        </div>
                        <h3 class="title">토익 실전 1000제</h3>
                        <p class="sub">강의수 40</p>
                        <div class="period">
                            수강 가능 기간: 2025-10-02 ~ 2025-12-31
                        </div>
                    </div>

                    <div class="actions">
                        <button class="btn outline">찜 해제</button>
                        <button class="btn edit">수강하기</button>
                    </div>
                </article>

                <!-- 아이템 2 -->
                <article class="like-item">
                    <div class="thumb">
                        <img src="${pageContext.request.contextPath}/img/dummy/teacher2.png" alt="강사 썸네일">
                    </div>

                    <div class="meta">
                        <div class="topline">
                            <span class="teacher">담당강사: 김하늘</span>
                            <span class="subject">일본어</span>
                        </div>
                        <h3 class="title">가장 빠르게 만드는 JLPT</h3>
                        <p class="sub">강의수 35</p>
                        <div class="period">
                            수강 가능 기간: 2025-10-13 ~ 2025-12-31
                        </div>
                    </div>

                    <div class="actions">
                        <button class="btn outline">찜 해제</button>
                        <button class="btn edit">수강하기</button>
                    </div>
                </article>

                <!-- 아이템 3 -->
                <article class="like-item">
                    <div class="thumb">
                        <img src="${pageContext.request.contextPath}/img/dummy/teacher3.png" alt="강사 썸네일">
                    </div>

                    <div class="meta">
                        <div class="topline">
                            <span class="teacher">담당강사: 장미남</span>
                            <span class="subject">중국어</span>
                        </div>
                        <h3 class="title">중국어 회화 스타터</h3>
                        <p class="sub">강의수 20</p>
                        <div class="period">
                            수강 가능 기간: 2025-11-01 ~ 2026-01-15
                        </div>
                    </div>

                    <div class="actions">
                        <button class="btn outline">찜 해제</button>
                        <button class="btn edit">수강하기</button>
                    </div>
                </article>
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
</body>
</html>
