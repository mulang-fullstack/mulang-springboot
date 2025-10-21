<%@ page contentType="text/html;charset=utf-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>강의 재생 | Mulang</title>
    <link rel="stylesheet" href="/css/pages/mypage/mycourse/player.css">
</head>
<body>

<div class="player-wrapper">

    <!-- 🎥 왼쪽 영상 영역 -->
    <section class="video-section">
        <div class="video-frame">
            <video controls autoplay id="lectureVideo" controlsList="nodownload">
                <source src="/course/course.mp4" type="video/mp4">
                브라우저가 video 태그를 지원하지 않습니다.
            </video>
        </div>

        <div class="video-info">
            <h3>[기초회화] 왕초보 영어 완성 - 01강</h3>
            <p>현재 강의 시간: <span id="currentTime">00:00</span> / 00:03</p>
        </div>

        <div class="video-controls">
            <button onclick="setPlaybackRate(1)">1.0x</button>
            <button onclick="setPlaybackRate(1.5)">1.5x</button>
            <button onclick="setPlaybackRate(2)">2.0x</button>
        </div>

        <!-- Q&A 섹션 (스크롤 내리면 보임) -->
        <div class="qna-section">
            <h4>강의 Q&A</h4>
            <div class="qna-item">
                <p class="question"><strong>Q.</strong> 문법이 어려운데 어떻게 복습해야 하나요?</p>
                <p class="answer"><strong>A.</strong> 매일 5분씩 반복 듣기만 해도 충분합니다 😊</p>
            </div>
            <div class="qna-item">
                <p class="question"><strong>Q.</strong> 교재는 따로 구매하나요?</p>
                <p class="answer"><strong>A.</strong> PDF 교재가 포함되어 있습니다.</p>
            </div>
            <div class="qna-item">
                <p class="question"><strong>Q.</strong> 강의 자료는 어디서 다운받나요?</p>
                <p class="answer"><strong>A.</strong> 강의 목록 하단에 자료실 메뉴가 있습니다.</p>
            </div>
            <div class="qna-item">
                <p class="question"><strong>Q.</strong> 수강 기간은 얼마나 되나요?</p>
                <p class="answer"><strong>A.</strong> 구매 후 1년간 무제한 수강 가능합니다.</p>
            </div>
        </div>
    </section>

    <!-- 📚 오른쪽: 강의목차만 -->
    <aside class="lecture-list">
        <h4>강의 목록</h4>
        <ul>
            <li onclick="loadVideo('/upload/sample1.mp4')" class="active">01강 영어의 기초</li>
            <li onclick="loadVideo('/upload/sample2.mp4')">02강 토익의 기초</li>
            <li onclick="loadVideo('/upload/sample3.mp4')">03강 회화 집중 훈련</li>
            <li onclick="loadVideo('/upload/sample4.mp4')">04강 문법 완성</li>
            <li onclick="loadVideo('/upload/sample5.mp4')">05강 듣기 훈련</li>
        </ul>
    </aside>
</div>

<script src="/js/pages/mypage/player.js"></script>

</body>
</html>
