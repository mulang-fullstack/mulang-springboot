<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="icon" href="/img/favicon.svg" type="image/png">

    <link rel="stylesheet" href="/css/global.css"/>
    <link rel="stylesheet" href="/css/pages/teacher/profile.css"/>

    <title>강사 프로필 | Mulang?</title>
</head>
<body>
<%@include file="../common/header.jsp" %>

<main>
    <div class="contents">

        <section class="dashboard">
            <%@ include file="sidebar.jsp" %>

            <section class="content">
                <div class="profile-main">
                    <h2>강사 프로필</h2>

                    <section class="profile-section">
                        <!-- 강사명 -->
                        <div class="field">
                            <label>강사명</label>
                            <div class="field-content">
                                <span class="text-value">최윤서</span>
                            </div>
                        </div>

                        <!-- 프로필 이미지 -->
                        <div class="field">
                            <label>프로필 이미지</label>
                            <div class="field-content">
                                <div class="profile-img-wrap">
                                    <img src="/img/dummy/yoon.png" alt="프로필 이미지">
                                </div>
                            </div>
                        </div>

                        <!-- 전화번호 -->
                        <div class="field">
                            <label>이메일</label>
                            <div class="field-content">
                                <span class="text-value">test@test</span>
                            </div>
                        </div>

                        <!-- 강사 소개 -->
                        <div class="field">
                            <label>강사 소개</label>
                            <div class="field-content">
                                <p class="text-area">안녕하세요 중국어 일타강사 최윤서입니다.</p>
                            </div>
                        </div>

                        <!-- 이력 -->
                        <div class="field">
                            <label>이력</label>
                            <div class="field-content">
                                <p class="text-area">조선족 출신 중국어 강의 경력 10년</p>
                            </div>
                        </div>

                        <!-- 클래스 장소 -->
                        <div class="field">
                            <label>클래스 장소</label>
                            <div class="field-content">
                                <p class="text-area">온오프라인 병행 가능</p>
                            </div>
                        </div>
                        <!-- 수정 버튼 -->
                        <div class="submit-wrap">
                            <button type="submit" class="submit-btn">수정하기</button>
                        </div>
                    </section>
                </div>
            </section>
        </section>

    </div>
</main>

<%@include file="../common/footer.jsp" %>
</body>
</html>
