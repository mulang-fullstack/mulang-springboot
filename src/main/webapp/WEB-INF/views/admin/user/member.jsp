<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">
    <link rel="icon" href="/img/favicon.svg" type="image/png">
    <link rel="stylesheet" href="/css/global.css"/>
    <link rel="stylesheet" href="/css/pages/admin/user/member.css"/>
    <title>관리자 | 회원 관리</title>
</head>
<body>
<div class="main-container">
    <%@ include file="../adminSidebar.jsp" %>

    <div class="right-container">
        <header>
            <h1>사용자 관리</h1>
            <div class="header-info">
                <div class="info-box"><p>안녕하세요 <span>관리자</span>님</p></div>
                <a class="logout" href="/auth/logout">로그아웃</a>
            </div>
        </header>

        <div class="content-wrap">
            <div class="content-header">
                <h2>회원 관리</h2>
                <p class="date-range" id="current-time"></p>
            </div>

            <section class="member-section">
                <!-- 검색 영역 -->
                <div class="search-bar">
                    <form action="/admin/user/memberList" method="get">
                        <input type="text" name="keyword" placeholder="이름 또는 이메일 검색" value="${param.keyword}">
                        <button type="submit">검색</button>
                    </form>
                    <button class="add-btn" onclick="location.href='/admin/user/memberAdd'">+ 회원 추가</button>
                </div>

                <!-- 회원 테이블 -->
                <div class="table-wrap">
                    <table>
                        <thead>
                        <tr>
                            <th>번호</th>
                            <th>이름</th>
                            <th>이메일</th>
                            <th>가입일</th>
                            <th>권한</th>
                            <th>상태</th>
                            <th>관리</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="member" items="${members}">
                            <tr>
                                <td>${member.id}</td>
                                <td>${member.name}</td>
                                <td>${member.email}</td>
                                <td>${member.createdAt}</td>
                                <td>${member.role}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${member.active}">활성</c:when>
                                        <c:otherwise>비활성</c:otherwise>
                                    </c:choose>
                                </td>
                                <td class="actions">
                                    <a href="/admin/user/memberDetail?id=${member.id}" class="detail">상세</a>
                                    <a href="/admin/user/memberEdit?id=${member.id}" class="edit">수정</a>
                                    <a href="/admin/user/memberDelete?id=${member.id}" class="delete"
                                       onclick="return confirm('정말 삭제하시겠습니까?');">삭제</a>
                                </td>
                            </tr>
                        </c:forEach>

                        <c:if test="${empty members}">
                            <tr><td colspan="7" class="no-data">등록된 회원이 없습니다.</td></tr>
                        </c:if>
                        </tbody>
                    </table>
                </div>
            </section>
        </div>
    </div>
</div>
<script src="/js/common/currentTime.js"></script>
</body>
</html>
