<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<link rel="stylesheet" href="css/pages/course/course-curriculum.css"/>
<h2>커리큘럼</h2>
<div class="course-curriculum">
    <div class="curriculum-header">
        <div class="no">강의 번호</div>
        <div class="title header">제목</div>
        <div class="time">영상 길이</div>
    </div>

    <c:forEach var="i" begin="1" end="20">
        <div class="curriculum-row">
            <div class="no">${i}</div>
            <div class="title">Dining Out!</div>
            <div class="time">00:12:43</div>
        </div>
    </c:forEach>
</div>
