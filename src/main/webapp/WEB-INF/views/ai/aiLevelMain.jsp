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
    <link rel="stylesheet" href="/css/pages/ai/aiLevelMain.css"/>
    <title>AI 언어 레벨테스트 | Mulang</title>
</head>
<body>
<%@include file="../common/header.jsp" %>

<main>
    <div class="contents ai-main">

        <!-- Hero Section -->
        <section class="ai-hero">
            <div class="ai-hero-text">
                <p class="ai-hero-sub">효율적인 언어 실력 향상을 위한 첫걸음</p>
                <h1 class="ai-hero-title">정확하게 진단하고<br>확실하게 가이드한다</h1>
                <div class="ai-hero-logo">AI <span>언어 레벨테스트</span></div>
                <p class="ai-hero-desc">
                    AI 분석 기반의 개인 맞춤 진단으로<br>
                    지금 당신의 언어 실력을 확인하세요.
                </p>
            </div>
        </section>
        <!-- 특징 Section -->
        <section class="ai-features">
            <div class="feature-card">
                <h3 class="feature-title">실력 진단</h3>
                <p class="feature-text">
                    학습자의 실력을 객관적으로 측정하여<br>
                    언어 습득의 현재 위치를 파악합니다.
                </p>
            </div>
            <div class="feature-card">
                <h3 class="feature-title">주기적 관리</h3>
                <p class="feature-text">
                    테스트 결과를 바탕으로<br>
                    학습 효율을 극대화할 수 있는<br>맞춤형 학습 루틴을 제안합니다.
                </p>
            </div>
            <div class="feature-card">
                <h3 class="feature-title">분석과 가이드</h3>
                <p class="feature-text">
                    AI가 각 영역별 강약점을 분석하여<br>
                    필요한 학습 전략과 자료를 추천합니다.
                </p>
            </div>
            <div class="feature-card">
                <h3 class="feature-title">맞춤 추천</h3>
                <p class="feature-text">
                    당신의 결과에 맞춰<br>
                    수준별 강좌와 콘텐츠를 연결해드립니다.
                </p>
            </div>
        </section>

        <!-- 시작 버튼 Section -->
        <section class="ai-start">
            <button class="ai-start-btn" onclick="openAiLevelPopup()">AI 레벨테스트 시작하기</button>
        </section>

    </div>
</main>

<%@include file="../common/footer.jsp" %>
<script src="/js/pages/ai/levelMain.js"></script>
</body>
</html>
