<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="icon" href="/img/favicon.svg" type="image/png">

    <link rel="stylesheet" href="/css/global.css"/>
    <link rel="stylesheet" href="/css/pages/mypage/profile/edit.css"/>

    <title>강사 프로필 관리 | Mulang?</title>
</head>
<body>

<%@ include file="../../common/header.jsp" %>

<main>
    <div class="contents">

        <section class="dashboard">
            <%@ include file="../sidebar.jsp" %>

            <section class="content">
                <div class="profile-main">
                    <h2>강사 프로필 관리</h2>

                    <section class="profile-section">
                        <!-- 프로필 이미지 -->
                        <div class="field">
                            <label>프로필 이미지</label>
                            <div class="field-content profile-img-area">
                                <div class="profile-img-wrap">
                                    <img id="profile-preview" src="/img/dummy/profile.jpg" alt="프로필 이미지">
                                </div>

                                <!-- 카메라 버튼 -->
                                <button type="button" class="camera-btn" onclick="document.getElementById('fileInput').click();">
                                    <img src="/img/icon/bx-camera.svg" alt="사진 변경">
                                </button>

                                <!-- 숨겨진 파일 업로드 input -->
                                <input type="file" id="fileInput" name="file" accept="image/*" style="display:none;">
                            </div>
                        </div>


                    <form action="/student/edit" method="post">

                        <!-- 강사명 -->
                        <div class="field">
                            <label>이름</label>
                            <div class="input-wrap">
                                <span>${user.username}</span>
                            </div>
                        </div>

                        <!-- 닉네임 -->
                        <div class="field">
                            <label>닉네임</label>
                            <div class="input-wrap">
                                <input type="text" name="nickname" value="${user.nickname}">
                            </div>
                        </div>


                        <!-- 이메일 -->
                        <div class="field">
                            <label>이메일</label>
                            <div class="input-wrap">
                                <input type="text" name="email" value="${user.email}">

                                <!-- 유효성 검사 에러 메시지 -->
                                <c:if test="${not empty emailerror}">
                                    <p class="error">${emailerror}</p>
                                </c:if>
                            </div>
                        </div>






                        <!-- 수정 버튼 -->
                        <div class="submit-wrap">
                            <button type="submit" class="submit-btn">수정하기</button>
                        </div>
                    </form>
                    </section>

                </div>
            </section>
        </section>

    </div>
</main>
<script src = "/js/pages/mypage/edit.js"></script>
<%@ include file="../../common/footer.jsp" %>
</body>
</html>
