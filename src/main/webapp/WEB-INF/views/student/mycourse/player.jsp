<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>${course.title} | Mulang</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">

    <link rel="icon" href="/img/favicon.svg" type="image/png">
    <link rel="stylesheet" href="/css/global.css"/>
    <link rel="stylesheet" href="/css/pages/mypage/mycourse/player.css">
    <link rel="stylesheet" href="/css/pages/mypage/mycourse/qna.css">
</head>
<body>
<div class="player-wrapper">

    <!-- =============================
         [주석 처리된 영상/강의 관련 영역]
    ============================== -->

    <%--
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
    --%>

    <!-- ===============================
         Q&A SECTION (PLAYER 하단 가로형)
    =============================== -->
    <section class="qna-section" data-course-id="${course.id}">
        <!-- Q&A HEADER -->
        <div class="qna-header">
            <h3 class="qna-title">Q&A 게시판</h3>
            <button id="QnaWriteBtn" class="qna-write-btn">질문 작성</button>
        </div>

        <!-- Q&A BODY: 2-COLUMN LAYOUT -->
        <div class="qna-container">
            <!-- LEFT: 질문 리스트 -->
            <div id="qna-list" class="qna-list"></div>

            <!-- RIGHT: 질문 상세 및 작성 -->
            <div class="qna-detail-area">
                <!-- 질문 작성 FORM (기본 숨김, JS로 토글됨) -->
                <div id="qna-write-form" class="qna-write-form" style="display:none;">
                    <input type="text" id="QnaTitle" placeholder="질문 제목을 입력하세요.">
                    <textarea id="QnaContent" placeholder="질문 내용을 입력하세요."></textarea>
                    <div class="qna-form-actions">
                        <button id="QnaSubmit">등록</button>
                        <button id="QnaCancel">취소</button>
                    </div>
                </div>

                <!-- 선택된 질문 + 답변 표시 영역 -->
                <div id="qna-detail" class="qna-detail"></div>
            </div>
        </div>
    </section>

    <%--
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
    --%>
</div>

<!-- ===============================
     스크립트 영역
================================ -->

<!-- ① 서버에서 JS로 강좌 ID 전달 -->
<script>
    window.MulangContext = {
        courseId: ${course.id}
    };
</script>

<!-- ② Q&A 모듈 -->
<script src="/js/pages/qna/QnaApi.js"></script>
<script src="/js/pages/qna/QnaView.js"></script>
<script src="/js/pages/qna/QnaController.js"></script>

<!-- ③ 페이지 초기화 -->
<script>
    document.addEventListener("DOMContentLoaded", () => {
        QnaController.init(window.MulangContext.courseId);
    });
</script>

</body>
</html>
