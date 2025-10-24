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
                <div class="class-table">
                    <div class="table-header">
                        <span>썸네일</span>
                        <span>제목</span>
                        <span>강사명</span>
                        <span>진척도</span>
                    </div>

                    <div class="table-body">
                        <c:forEach var="course" items="${mycourseDTO}">
                            <div class="table-row">
                                <!-- 썸네일 -->
                                <div class="thumb">
                                    <img src="${course.courseThumbnail}" alt="썸네일">
                                </div>

                                <!-- 강좌 제목 -->
                                <button type="button" class="title" onclick="openPlayer(${course.courseId})">
                                        ${course.courseTitle}
                                </button>

                                <!-- 강사명 -->
                                <div class="teacher-name">
                                    <c:choose>
                                        <c:when test="${not empty course.teacherName}">
                                            ${course.teacherName}
                                        </c:when>
                                        <c:otherwise>
                                            <span class="no-teacher">-</span>
                                        </c:otherwise>
                                    </c:choose>
                                </div>

                                <!-- 진척도 -->
                                <div class="progress-wrapper">
                                    <c:choose>
                                        <c:when test="${course.progressPercentage == 0 or course.progressPercentage == null}">
                                            <span class="no-progress">수강 정보 없음</span>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="progress-bar">
                                                <div class="progress-fill" style="width: ${course.progressPercentage}%"></div>
                                            </div>
                                            <span class="progress-text">
                                                ${course.viewedLectures} / ${course.totalLectures}
                                                (<fmt:formatNumber value="${course.progressPercentage}" maxFractionDigits="1" />%)
                                            </span>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </c:forEach>

                        <!-- 데이터가 없을 때 -->
                        <c:if test="${empty mycourseDTO}">
                            <div class="empty-state">
                                <p>수강 중인 강좌가 없습니다.</p>
                            </div>
                        </c:if>
                    </div>
                </div>
            </section>
        </section>
    </div>
</main>

<%@include file="../../common/footer.jsp" %>
<script src="/js/pages/mypage/course.js" defer></script>
</body>
</html>
