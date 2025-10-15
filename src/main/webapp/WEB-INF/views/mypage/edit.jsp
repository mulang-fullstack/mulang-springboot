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

    <link rel="stylesheet" href="/css/global.css"/>
    <link rel="stylesheet" href="/css/pages/mypage/edit.css"/>
    <title>내 프로필 수정</title>
</head>
<body>

<%@include file="../common/header.jsp" %>

<main class="main">
    <section class="dashboard">
        <%@include file="sidebar.jsp" %>

        <section class="content">
            <div class="profile-main">
                <h2>내 프로필 수정</h2>

                <section class="profile-section">
                    <!-- 튜터명 -->
                    <div class="field">
                        <label>이름</label>
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
                            <input type="text" value="010-5849-0283" readonly>
                            <span class="check-icon">✔</span>
                        </div>
                    </div>
                    <!-- 생년월일 -->
                    <div class="field">
                        <label>생년월일</label>
                        <div class="input-wrap">
                            <input type="text" value="2007-11-12" readonly>
                            <span class="check-icon">✔</span>
                        </div>
                    </div>

                    <!-- 이메일 -->
                    <div class="field">
                        <label>이메일</label>
                        <div class="input-wrap">
                            <textarea rows="1" readonly>071112yoonseo@naver.com</textarea>
                            <button class="edit-btn">수정</button>
                        </div>
                    </div>

                        <div class="field">
                         <label>비밀번호</label>
                         <div class="input-wrap">
                             <textarea rows="1" readonly>abcd1234</textarea>
                             <button class="edit-btn">수정</button>
                         </div>
                    </div>
                </section>
            </div>
        </section>
    </section>
</main>
<%@include file="../common/footer.jsp" %>
</body>
</html>
