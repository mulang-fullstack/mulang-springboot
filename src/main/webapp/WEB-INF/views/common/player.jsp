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
    <link rel="stylesheet" href="/css/pages/mypage/mycourse/vodmenu.css">
</head>
<body>

<div class="vod-layout">

    <!-- 상단: 뒤로가기 + 제목 -->
    <header class="vod-header">
        <a href="${backUrl}" class="vod-back-btn">←</a>
        <h3 class="vod-title">${course.title}</h3>
    </header>

    <!-- ===============================
         본문: 왼쪽 영상 + 오른쪽 패널
    =============================== -->
    <div class="vod-main">

        <!-- 왼쪽: 영상 -->
        <main class="player-main">
            <section class="video-section">
                <div class="video-frame">
                    <video controls autoplay id="lectureVideo" controlsList="nodownload">
                        <source src="${lecture.fileUrl}" type="video/mp4">
                        브라우저가 video 태그를 지원하지 않습니다.
                    </video>
                </div>
            </section>
        </main>

        <!-- 오른쪽: 패널 -->
        <aside class="vod-side">
            <div class="vod-panel">

                <!-- 커리큘럼 -->
                <div id="panel-curriculum" class="panel active">
                    <!-- 강좌 정보 -->
                    <div class="course-info">
                        <h4 class="course-title">${course.title}</h4>
                        <p class="course-subtitle">${course.subtitle}</p>
                    </div>
                    <ul class="lecture-list">
                        <c:forEach var="lec" items="${lectureList}">
                            <li onclick="location.href='/player/${course.courseId}?lectureId=${lec.id}'"
                                class="${lec.id == lecture.id ? 'active' : ''}">
                                    ${lec.title}
                            </li>
                        </c:forEach>
                    </ul>
                </div>

                <!-- Q&A -->
                <div id="panel-qna" class="panel">
                    <div class="qna-sidebar" data-course-id="${course.courseId}">
                        <div class="qna-header">
                            <h3 class="qna-title">Q&A 게시판</h3>
                        </div>
                        <div class="qna-body scrollable">
                            <div id="qna-list" class="qna-list-wrapper"></div>
                            <div class="qna-pagination"></div>

                            <button id="QnaWriteBtn" class="qna-write-btn">질문 작성</button>

                            <div id="qna-write-form" class="qna-write-form" style="display:none;">
                                <input type="text" id="QnaTitle" placeholder="질문 제목을 입력하세요.">
                                <textarea id="QnaContent" placeholder="질문 내용을 입력하세요."></textarea>
                                <div class="qna-form-actions">
                                    <button id="QnaSubmit">등록</button>
                                    <button id="QnaCancel">취소</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </aside>
    </div>

    <!-- ===============================
         메뉴바 (vod-main 밖, 화면 고정)
    =============================== -->
    <nav class="vod-menu">
        <button class="menu-btn active" data-target="curriculum" title="커리큘럼">
            <img src="/img/icon/bx-list-ul.png" alt="커리큘럼 아이콘" class="menu-icon">
        </button>
        <button class="menu-btn" data-target="qna" title="Q&A">
            <img src="/img/icon/bx-message-rounded-dots.png" alt="Q&A 아이콘" class="menu-icon">
        </button>
    </nav>

</div>

<!-- ===============================
     스크립트 영역
================================ -->
<script>
    window.MulangContext = {
        courseId: ${course.courseId}
    };
</script>

<!-- QnA 스크립트 -->
<script src="/js/pages/qna/QnaApi.js"></script>
<script src="/js/pages/qna/QnaView.js"></script>
<script src="/js/pages/qna/QnaController.js"></script>
<script src="/js/pages/qna/QnaPagination.js"></script>
<script src="/js/pages/teacher/vod/VodMenuController.js"></script>
<script>
    document.addEventListener("DOMContentLoaded", () => {
        if (window.QnaController && window.MulangContext) {
            QnaController.init(window.MulangContext.courseId);
        }
    });
</script>

</body>
</html>
