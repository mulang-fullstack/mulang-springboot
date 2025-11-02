<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="icon" href="/img/favicon.svg" type="image/png">
    <link rel="stylesheet" href="/css/global.css"/>
    <link rel="stylesheet" href="/css/pages/ai/aiLevelPopup.css"/>
    <title>AI 레벨테스트 | Mulang</title>
</head>
<body>
<main>
    <div class="contents">
        <section class="test-wrapper">
            <!-- 상단 제목 및 라인 -->
            <header class="test-header">
                <h1 class="test-title">AI 레벨테스트</h1>
                <div class="test-line"></div>
            </header>

            <!-- 문제 영역 -->
            <article class="test-question-box">
                <p class="test-question-text">Q3. 아래 지시에 알맞은 단어를 고르세요.</p>
                <p class="test-subtext">반의어 찾기: broke</p>
            </article>

            <!-- 보기 영역 -->
            <div class="test-options" id="optionsContainer">
                <button class="option-item">1. affluent</button>
                <button class="option-item">2. insolvent</button>
                <button class="option-item">3. bankrupt</button>
                <button class="option-item">4. penniless</button>
            </div>

            <!-- 하단 버튼 -->
            <footer class="test-footer">
                <button class="footer-button next" id="nextBtn">다음 문제</button>
            </footer>
        </section>
    </div>
</main>
<script src="/js/pages/ai/levelTest.js"></script>
</body>
</html>
