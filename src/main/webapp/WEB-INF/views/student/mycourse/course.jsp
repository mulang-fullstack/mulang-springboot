<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="icon" href="/img/favicon.svg" type="image/png">

    <link rel="stylesheet" href="/css/global.css"/>
    <link rel="stylesheet" href="/css/pages/mypage/mycourse/course.css"/>
    <title>나의 학습방 | Mulang?</title>
</head>
<body>

<%@include file="../../common/header.jsp" %>

<main>
    <div class="contents">
        <section class="mypage">

            <%@include file="../sidebar.jsp" %>

            <section class="content">
                <h2>나의 학습방</h2>

                <!-- 툴바(검색/정렬) -->
                <div class="likes-toolbar">
                    <div class="left">
                        <select class="sel-subject" id="subjectFilter" onchange="filterBySubject()">
                            <option value="">전체 과목</option>
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
                </div>  <!-- ⭐ likes-toolbar 닫기 -->

                <!-- 테이블 -->
                <div class="class-table">
                    <div class="table-header">
                        <span>썸네일</span>
                        <span>제목</span>
                        <span>강사명</span>
                        <span>진척도</span>
                    </div>

                    <div class="table-body">
                        <c:forEach var="course" items="${mycourseResponseList}">
                            <div class="table-row">
                                <!-- 썸네일 -->
                                <div class="thumb">
                                    <img src="${course.course.thumbnail}" alt="썸네일">
                                </div>

                                <!-- 강좌 제목 -->
                                <div class="title-wrap" data-id="${course.course.id}">
                                    <div class="title">${course.course.title}</div>
                                </div>
                                <!-- 강사명 -->
                                <div class="teacher-name">
                                    <c:choose>
                                        <c:when test="${not empty course.course.teacher.user.username}">
                                            ${course.course.teacher.user.username}
                                        </c:when>
                                        <c:otherwise>
                                            <span class="no-teacher">-</span>
                                        </c:otherwise>
                                    </c:choose>
                                </div>

                                <!-- 진척도 -->
                                <div class="progress-wrapper">
                                    <c:choose>
                                        <c:when test="${course.progress == null}">
                                            <span class="no-progress">수강 정보 없음</span>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="progress-bar">
                                                <div class="progress-fill ${course.progress < 30 ? 'low' : course.progress < 70 ? 'medium' : 'high'}"
                                                     style="width: ${course.progress}%">
                                                    <span class="progress-text">${course.progress}%</span>
                                                </div>
                                            </div>

                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </c:forEach>

                        <!-- 데이터가 없을 때 -->
                        <c:if test="${empty mycourseResponseList}">
                            <div class="empty-state">
                                <p>수강 중인 강좌가 없습니다.</p>
                            </div>
                        </c:if>
                    </div>
                </div>  <!-- ⭐ class-table 닫기 -->

            </section>  <!-- ⭐ content 닫기 -->
        </section>  <!-- ⭐ mypage 닫기 -->
    </div>  <!-- ⭐ contents 닫기 -->
</main>

<%@include file="../../common/footer.jsp" %>
<script src="/js/pages/mypage/course.js" defer></script>
</body>
</html>
