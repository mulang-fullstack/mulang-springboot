<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="icon" href="/img/favicon.svg" type="image/png">
    <link rel="stylesheet" href="/css/global.css"/>
    <link rel="stylesheet" href="/css/pages/teacher/classEdit.css"/>

    <title>클래스 관리 | Mulang?</title>
</head>
<body>
<%@include file="../common/header.jsp" %>

<main>
    <div class="contents">
        <section class="dashboard">
            <%@ include file="sidebar.jsp" %>

            <section class="content">
                <!-- 버튼 -->
                <div class="title-wrap">
                    <h2>클래스 관리</h2>
                    <a href="/teacher/mypage/classes/new" class="create-class-btn">
                        <span class="plus">+</span> 클래스 만들기
                    </a>
                </div>
                <!-- 클래스 테이블 -->
                <div class="class-table">
                    <div class="table-header">
                        <span>썸네일</span>
                        <span>클래스명 / 부제목</span>
                        <span>카테고리 / 강의언어</span>
                    </div>

                    <div class="table-body">
                        <c:forEach var="course" items="${courses}">
                            <div class="table-row">
                                <div class="thumb">
                                    <img src="<c:url value='${course.thumbnail}'/>" alt="클래스 썸네일" width="160" height="90">
                                </div>
                                <div class="title-wrap">
                                    <div class="title">${course.title}</div>
                                    <div class="subtitle">${course.subtitle}</div>
                                </div>
                                <div class="category">
                                        ${course.category} / ${course.language}
                                </div>

                                <div class="menu-wrap">
                                    <button class="menu-btn">⋯</button>
                                    <div class="menu-dropdown">
                                        <button class="menu-item edit-btn" data-id="${course.id}">수정</button>
                                        <button class="menu-item delete-btn" data-id="${course.id}">삭제</button>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>

                        <c:if test="${empty courses}">
                            <div class="table-row">
                                <div class="no-class-message">
                                    등록된 클래스가 없습니다.
                                </div>
                            </div>
                        </c:if>
                    </div>
                </div>

            </section>
        </section>
    </div>
</main>

<%@include file="../common/footer.jsp" %>
<script src="/js/pages/teacher/classEdit.js"></script>
</body>
</html>
