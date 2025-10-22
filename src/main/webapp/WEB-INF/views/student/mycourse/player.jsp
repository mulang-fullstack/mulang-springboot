<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>${lecture.title} | Mulang</title>
    <link rel="stylesheet" href="/css/pages/mypage/mycourse/player.css">
</head>
<body>

<div class="player-wrapper">

    <!-- 🎥 왼쪽 영상 영역 -->
    <section class="video-section">
        <div class="video-frame">
            <video controls autoplay id="lectureVideo" controlsList="nodownload">
                <source src="${lecture.file.url}" type="video/mp4">
                브라우저가 video 태그를 지원하지 않습니다.
            </video>
        </div>

        <div class="video-info">
            <h3>${lecture.title}</h3>
            <p>현재 강의 시간: <span id="currentTime">00:00</span></p>
        </div>

        <div class="video-controls">
            <button onclick="setPlaybackRate(1)">1.0x</button>
            <button onclick="setPlaybackRate(1.5)">1.5x</button>
            <button onclick="setPlaybackRate(2)">2.0x</button>
        </div>
    </section>

    <!-- 📚 오른쪽: 강의목차 -->
    <aside class="lecture-list">
        <h4>강의 목록</h4>
        <ul>
            <c:forEach var="lec" items="${lectureList}">
                <li onclick="location.href='/student/${lec.course.id}/lecture/${lec.id}'"
                    class="${lec.id == lecture.id ? 'active' : ''}">
                        ${lec.title}
                </li>
            </c:forEach>
        </ul>
    </aside>
</div>

<script src="/js/pages/mypage/player.js"></script>
</body>
</html>
