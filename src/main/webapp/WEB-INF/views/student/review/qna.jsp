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
    <link rel="stylesheet" href="/css/pages/mypage/review/qna.css"/>
    <title>Q&A | Mulang?</title>
</head>
<body>

<%@include file="../../common/header.jsp" %>

<main>
    <div class="contents">
        <section class="mypage">

            <%@include file="../sidebar.jsp" %>

            <section class="content">
                <h2>Q&A</h2>

                <!-- 탭 메뉴 -->
                <div class="tab-menu">
                    <a href="review" class="tab">리뷰</a>
                    <a href="qna" class="tab active">Q&A</a>
                </div>

                <!-- 필터 영역 -->
                <div class="filter-bar">
                    <select class="sort-select">
                        <option value="최신순" ${currentSort == '최신순' ? 'selected' : ''}>최신순</option>
                        <option value="오래된순" ${currentSort == '오래된순' ? 'selected' : ''}>오래된순</option>
                    </select>
                    <button type="button" class="apply-btn" onclick="location.href='/student/qnawrite'">작성하기</button>
                </div>

                <!-- 성공/에러 메시지 -->
                <c:if test="${not empty message}">
                    <div class="success-message">${message}</div>
                </c:if>
                <c:if test="${not empty error}">
                    <div class="error-message">${error}</div>
                </c:if>

                <!-- Q&A 리스트 -->
                <div class="qna-list">
                    <c:choose>
                        <c:when test="${empty myqna}">
                            <div class="empty">작성한 질문이 없습니다.</div>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="qna" items="${myqna}">
                                <div class="qna-item">
                                    <!-- 질문 헤더 -->
                                    <div class="qna-header">
                                        <h3 class="qna-title">${qna.title}</h3>
                                        <span class="qna-date">
                                                ${qna.createdAt.toString().substring(0, 16).replace('T', ' ')}
                                        </span>
                                    </div>

                                    <!-- 강좌 정보 -->
                                    <div class="qna-course">
                                        <span class="course-label">강좌:</span>
                                        <span class="course-name">${qna.course.title}</span>
                                    </div>

                                    <!-- 질문 내용 -->
                                    <div class="qna-content">
                                        <p>${qna.content}</p>
                                    </div>

                                    <!-- 액션 버튼 -->
                                    <div class="qna-actions">
                                        <button type="button" class="edit-btn" onclick="location.href='/student/qna/edit/${qna.id}'">수정</button>
                                        <button type="button" class="delete-btn" onclick="deleteQna(${qna.id})">삭제</button>
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

<script src="/js/pages/mypage/qna.js"></script>
<%@include file="../../common/footer.jsp" %>

</body>
</html>
