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

    <title>강사 프로필 | Mulang</title>
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
                                <span class="text-value"></span>
                            </div>
                        </div>

                        <div class="field">
                            <label>프로필 이미지</label>
                            <div class="field-content">
                                <div class="profile-img-wrap">
                                    <c:choose>
                                        <c:when test="">
                                            <img src="" alt="프로필 이미지">
                                        </c:when>
                                        <c:otherwise>
                                            <img src="/img/default_profile.png" alt="기본 이미지">
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </div>

                        <div class="field">
                            <label>이메일</label>
                            <div class="field-content">
                                <span class="text-value"></span>
                            </div>
                        </div>

                        <!-- 강사 소개 -->
                        <div class="field">
                            <label>강사 소개</label>
                            <div class="field-content">
                                <p class="text-area"></p>
                            </div>
                        </div>
                        <!-- 경력 -->
                        <div class="field">
                            <label>경력</label>
                            <div class="field-content">
                                <p class="text-area"></p>
                            </div>
                        </div>
                        <!-- 언어 -->
                        <div class="field">
                            <label>언어</label>
                            <div class="field-content">
                                <span class="text-value"></span>
                            </div>
                        </div>
                        <!-- 수정 버튼 -->
                        <div class="submit-wrap">
                            <form action="/teacher/mypage/profile" method="get">
                                <button type="submit" class="submit-btn">수정하기</button>
                            </form>
                        </div>
                    </section>
                </div>
            </section>
        </section>
    </div>
</main>
<script src="/js/pages/teacher/profile.js"></script>
<%@include file="../common/footer.jsp" %>
</body>

</html>
