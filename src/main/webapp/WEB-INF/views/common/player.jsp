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
<div class="player-layout">

    <!-- ===============================
         중앙 VOD 플레이어 + Q&A 섹션
    =============================== -->
    <main class="player-main">
        <!-- 영상 영역 -->
        <section class="video-section">
            <div class="video-frame">
                <video controls autoplay id="lectureVideo" controlsList="nodownload">
                    <source src="${lecture.fileUrl}" type="video/mp4">
                    브라우저가 video 태그를 지원하지 않습니다.
                </video>
            </div>

            <div class="video-info">
                <h3>${lecture.title}</h3>
            </div>
        </section>

        <!-- Q&A 섹션 -->
        <section class="qna-section" data-course-id="${course.id}">
            <div class="qna-header">
                <h3 class="qna-title">Q&A 게시판</h3>
                <button id="QnaWriteBtn" class="qna-write-btn">질문 작성</button>
            </div>

            <div class="qna-body">
                <!-- 왼쪽: 질문 리스트 -->
                <div class="qna-list-wrapper-container">
                    <div id="qna-list" class="qna-list-wrapper scrollable"></div>
                    <div class="qna-pagination"></div>
                </div>
                <!-- 오른쪽: 질문 작성 폼 -->
                <div id="qna-write-form" class="qna-write-form" style="display:none;">
                    <input type="text" id="QnaTitle" placeholder="질문 제목을 입력하세요.">
                    <textarea id="QnaContent" placeholder="질문 내용을 입력하세요."></textarea>
                    <div class="qna-form-actions">
                        <button id="QnaSubmit">등록</button>
                        <button id="QnaCancel">취소</button>
                    </div>
                </div>
            </div>
        </section>
    </main>

    <!-- ===============================
         오른쪽 강의 목록 사이드바
    =============================== -->
    <aside class="lecture-sidebar">
        <h4 class="lecture-title">강의 목록</h4>
        <ul class="lecture-list">
            <c:forEach var="lec" items="${lectureList}">
                <li onclick="location.href='/course/${course.id}/vod?lectureId=${lec.id}'"
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
<script>
    window.MulangContext = {
        courseId: ${course.id}
    };
</script>

<script src="/js/pages/qna/QnaApi.js"></script>
<script src="/js/pages/qna/QnaView.js"></script>
<script src="/js/pages/qna/QnaController.js"></script>
<script src="/js/pages/qna/QnaPagination.js"></script>

<script>
    document.addEventListener("DOMContentLoaded", () => {
        QnaController.init(window.MulangContext.courseId);
    });
</script>
</body>
</html>
