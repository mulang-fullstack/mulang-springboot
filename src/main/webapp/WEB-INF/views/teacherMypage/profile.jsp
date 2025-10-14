<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="icon" href="/img/favicon.svg" type="image/png">

    <!-- 공통 및 프로필 전용 CSS -->
    <link rel="stylesheet" href="/css/global.css"/>
    <link rel="stylesheet" href="/css/pages/teacher/profile.css"/>

    <title>튜터 프로필 · 채널 관리</title>
</head>
<body>

<%@include file="../common/header.jsp" %>

<main class="main">
    <section class="dashboard">
        <%@include file="sidebar.jsp" %>

        <section class="content">
            <div class="profile-main">
                <h2>내 튜터 프로필 · 채널 관리</h2>

                <section class="profile-section">
                    <!-- 튜터명 -->
                    <div class="field">
                        <label>튜터명</label>
                        <div class="field-content">
                            <span>최윤서</span>
                            <button class="edit-btn">수정</button>
                        </div>
                    </div>

                    <!-- 프로필 이미지 -->
                    <div class="field">
                        <label>프로필 이미지</label>
                        <div class="field-content">
                            <div class="profile-img-wrap">
                                <img src="https://placehold.co/120x120" alt="프로필 이미지">
                            </div>
                            <button type="button" class="camera-btn">
                                <img src="/img/icon/bx-camera.svg" alt="사진 변경">
                            </button>
                        </div>
                    </div>

                    <!-- 전화번호 -->
                    <div class="field">
                        <label>전화번호</label>
                        <div class="input-wrap">
                            <input type="text" value="010 9593 8009" readonly>
                            <span class="check-icon">✔</span>
                        </div>
                    </div>

                    <!-- 튜터 소개 -->
                    <div class="field">
                        <label>튜터 소개</label>
                        <div class="input-wrap">
                            <textarea rows="2" readonly>나는 귀여운 윤서양</textarea>
                            <button class="edit-btn">수정</button>
                        </div>
                    </div>

                    <!-- 이력 -->
                    <div class="field">
                        <label>이력 (권장사항)</label>
                        <button class="add-btn">+ 이력 추가하기</button>
                    </div>

                    <!-- 클래스 장소 -->
                    <div class="field">
                        <label>클래스 장소</label>
                        <button class="add-btn">+ 장소 추가하기</button>
                    </div>
                </section>
            </div>
        </section>
    </section>
</main>
<%@include file="../common/footer.jsp" %>
</body>
</html>
