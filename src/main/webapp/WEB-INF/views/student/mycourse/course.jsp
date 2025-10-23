<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
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
                        <span>강좌 유형</span>
                        <span>수강 상태</span>
                        <span>수강기간</span>
                    </div>

                    <div class="table-body">
                        <c:forEach var="enrollment" items="${enrollment}">
                            <div class="table-row">
                                <!-- 썸네일 -->
                                <div class="thumb">
                                    <img src="https://placehold.co/160x90" alt="썸네일">
                                </div>

                                <!-- 강좌 제목 -->
                                <button type="button" class="title" onclick="openPlayer(${enrollment.course.id})">
                                        ${enrollment.course.title}
                                </button>

                                <!-- 강좌 타입 -->
                                <div class="type">
                                    <c:choose>
                                        <c:when test="${enrollment.course.type.name() == 'ONLINE'}">온라인</c:when>
                                        <c:when test="${enrollment.course.type.name() == 'OFFLINE'}">오프라인</c:when>
                                        <c:when test="${enrollment.course.type.name() == 'HYBRID'}">온/오프라인</c:when>
                                        <c:otherwise>${enrollment.course.type}</c:otherwise>
                                    </c:choose>
                                </div>

                                <!-- 수강 상태 -->
                                <div class="status">
                                    <c:choose>
                                        <c:when test="${enrollment.enrollmentStatus.name() == 'APPLIED'}">
                                            <span class="tag gray">신청</span>
                                        </c:when>
                                        <c:when test="${enrollment.enrollmentStatus.name() == 'PAID'}">
                                            <span class="tag blue">수강중</span>
                                        </c:when>
                                        <c:when test="${enrollment.enrollmentStatus.name() == 'COMPLETED'}">
                                            <span class="tag green">수강완료</span>
                                        </c:when>
                                        <c:when test="${enrollment.enrollmentStatus.name() == 'CANCELLED'}">
                                            <span class="tag red">취소</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="tag gray">${enrollment.enrollmentStatus}</span>
                                        </c:otherwise>
                                    </c:choose>
                                </div>

                                <!-- 강좌 기간 -->
                                <div class="date">
                                    <c:if test="${not empty enrollment.course.startedAt and not empty enrollment.course.endedAt}">
                                        ${enrollment.course.startedAt} ~ ${enrollment.course.endedAt}
                                    </c:if>
                                    <c:if test="${empty enrollment.course.startedAt or empty enrollment.course.endedAt}">
                                        <c:if test="${not empty enrollment.appliedAt}">
                                            ${enrollment.appliedAt}
                                        </c:if>
                                        <c:if test="${empty enrollment.appliedAt}">
                                            -
                                        </c:if>
                                    </c:if>
                                </div>
                            </div>
                        </c:forEach>

                        <!-- 데이터가 없을 때 -->
                        <c:if test="${empty enrollment}">
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
