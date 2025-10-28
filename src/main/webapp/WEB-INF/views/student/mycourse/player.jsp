<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>${lecture.title} | Mulang</title>

    <!-- 전역 / 페이지 CSS -->
    <link rel="stylesheet" href="/css/global.css"/>
    <link rel="stylesheet" href="/css/pages/mypage/mycourse/player.css">
    <link rel="stylesheet" href="/css/pages/mypage/mycourse/qna.css">
</head>

<body>
<div class="player-wrapper">

    <!-- 왼쪽: 영상 영역 -->
    <section class="video-section">
        <!-- 영상 -->
        <div class="video-frame">
            <video controls autoplay id="lectureVideo" controlsList="nodownload">
                <source src="${lecture.file.url}" type="video/mp4">
                브라우저가 video 태그를 지원하지 않습니다.
            </video>
        </div>

        <!-- 영상 정보 -->
        <div class="video-info">
            <h3>${lecture.title}</h3>
            <p>현재 강의 시간: <span id="currentTime">00:00</span></p>
        </div>

        <!-- 재생 속도 -->
        <div class="video-controls">
            <button onclick="setPlaybackRate(1)">1.0x</button>
            <button onclick="setPlaybackRate(1.5)">1.5x</button>
            <button onclick="setPlaybackRate(2)">2.0x</button>
        </div>

        <!-- Q&A 섹션 -->
        <section class="qna-section" data-course-id="${lecture.course.id}">
            <div class="qna-header">
                <h4>Q&A 게시판</h4>
            </div>

            <!-- 질문 작성 -->
            <div class="qna-write-box">
                <textarea id="QnaContent" placeholder="강좌에 대한 질문을 입력하세요."></textarea>
                <button id="QnaSubmit">등록</button>
            </div>

            <!-- 질문 목록 -->
            <div id="qna-list" class="qna-list">
                <!-- JS에서 동적으로 채워짐 -->
            </div>

            <!-- 페이징 -->
            <div class="pagination"></div>
        </section>
    </section>

    <!-- 오른쪽: 강의 목록 -->
    <aside class="lecture-list">
        <h4>강의 목록</h4>
        <ul>
            <c:forEach var="lec" items="${lectureList}">
                <li onclick="location.href='/student/course/${lec.course.id}/lecture/${lec.id}'"
                    class="${lec.id == lecture.id ? 'active' : ''}">
                        ${lec.title}
                </li>
            </c:forEach>
        </ul>
    </aside>
</div>

<!-- ===============================
     스크립트 영역
================================ -->

<!-- ① 서버에서 JS로 강좌 ID 전달 -->
<script>
    window.MulangContext = {
        courseId: ${lecture.course.id}
    };
</script>

<!-- ② 기존 player 관련 -->
<script src="/js/pages/mypage/player.js"></script>
<script src="/js/pages/mypage/qnaPagination.js"></script>

<!-- ③ Q&A 모듈 -->
<script src="/js/pages/qna/QnaApi.js"></script>
<script src="/js/pages/qna/QnaView.js"></script>
<script src="/js/pages/qna/QnaController.js"></script>

<!-- ④ 페이지 초기화 (LecturePageInit.js) -->
<script src="/js/pages/mypage/LecturePageInit.js"></script>

</body>
</html>
