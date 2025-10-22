<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" href="/img/favicon.svg" type="image/png">

    <link rel="stylesheet" href="/css/global.css"/>
    <link rel="stylesheet" href="/css/pages/teacher/profile.css"/>

    <title>강사 프로필 | Mulang?</title>
</head>
<body>
<%@ include file="../common/header.jsp" %>

<main>
    <div class="contents">
        <section class="dashboard">
            <%@ include file="sidebar.jsp" %>

            <section class="content">
                <div class="profile-main">
                    <h2>강사 프로필</h2>

                    <section class="profile-section">
                        <!-- 이름 -->
                        <div class="field">
                            <label>이름</label>
                            <div class="field-content">
                                <span class="text-value">${teacher.user.username}</span>
                            </div>
                        </div>

                        <!-- 프로필 이미지 -->
                        <div class="field">
                            <label>프로필 이미지</label>
                            <div class="field-content">
                                <div class="profile-img-wrap">
                                    <img id="profileImg"
                                         src="${teacher.user.file != null ? teacher.user.file.url : '/img/dummy/default-profile.png'}">
                                </div>
                            </div>
                        </div>

                        <!-- 닉네임 -->
                        <div class="field">
                            <label>닉네임</label>
                            <div class="field-content">
                                <span class="text-value">${teacher.user.nickname}</span>
                            </div>
                        </div>

                        <!-- 이메일 -->
                        <div class="field">
                            <label>이메일</label>
                            <div class="field-content">
                                <span class="text-value">${teacher.user.email}</span>
                            </div>
                        </div>

                        <!-- 소개 -->
                        <div class="field">
                            <label>소개</label>
                            <div class="field-content">
                                <p class="text-area">${teacher.introduction}</p>
                            </div>
                        </div>

                        <!-- 경력 -->
                        <div class="field">
                            <label>경력</label>
                            <div class="field-content">
                                <p class="text-area">${teacher.carreer}</p>
                            </div>
                        </div>

                        <div class="submit-wrap">
                            <button type="button" class="submit-btn">수정하기</button>
                        </div>
                    </section>
                </div>
            </section>
        </section>
    </div>
</main>
<script src="/js/pages/teacher/profile.js"></script>
<%@ include file="../common/footer.jsp" %>
</body>
</html>
