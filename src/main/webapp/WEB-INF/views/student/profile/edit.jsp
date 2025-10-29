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

                        <!-- 이메일 인증 form 추가 -->
                        <form id="emailVerifyForm" action="/student/editemail" method="post" style="display:none;">
                            <input type="hidden" name="email" id="hiddenEmail">
                        </form>

                        <!-- form 시작 위치 변경 + enctype 추가 -->
                        <form action="/student/edit" method="post" enctype="multipart/form-data" id="editForm">

                            <!-- 프로필 이미지 -->
                            <div class="field">
                                <label>프로필 이미지</label>
                                <div class="field-content profile-img-area">
                                    <div class="profile-img-wrap">
                                        <img id="profile-preview"
                                             src="${not empty user.photoUrl ? user.photoUrl : '/img/dummy/profile.jpg'}"
                                             alt="프로필 이미지">
                                    </div>

                                    <!-- 카메라 버튼 -->
                                    <button type="button" class="camera-btn" onclick="document.getElementById('fileInput').click();">
                                        <img src="/img/icon/bx-camera.svg" alt="사진 변경">
                                    </button>

                                    <!-- 숨겨진 파일 업로드 input -->
                                    <input type="file" id="fileInput" name="photo" accept="image/*" style="display:none;">
                                </div>
                            </div>

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
                                <div class="input-wrap email-verify-wrap">
                                    <div class="email-input-group">
                                        <input type="text" id="email" name="email" value="${user.email}">
                                        <button type="button" class="verify-btn" onclick="document.getElementById('hiddenEmail').value = document.getElementById('email').value; document.getElementById('emailVerifyForm').submit();">인증하기</button>
                                    </div>

                                    <!-- 이메일 관련 메시지 (인증코드 전송 성공/실패) -->
                                    <c:if test="${not empty message}">
                                        <p class="success-message">${message}</p>
                                    </c:if>

                                    <c:if test="${not empty errorMessage}">
                                        <p class="error-message">${errorMessage}</p>
                                    </c:if>

                                    <!-- 인증코드 입력창 -->
                                    <div class="email-code-group ${not empty showCodeInput ? 'show' : 'hidden'}">
                                        <input type="text"
                                               id="emailCode"
                                               placeholder="인증코드 6자리"
                                               maxlength="6"
                                        ${empty showCodeInput ? 'disabled' : ''}>
                                        <button type="button" class="verify-code-btn" onclick="submitVerifyCode()">확인</button>
                                    </div>

                                    <!-- 인증코드 확인 메시지 (인증 성공/실패) -->
                                    <c:if test="${not empty emailVerified and emailVerified}">
                                        <p class="success-message">이메일 인증이 완료되었습니다.</p>
                                    </c:if>

                                    <c:if test="${not empty emailerror}">
                                        <p class="error-message">${emailerror}</p>
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

<!-- 원본 이메일 값을 JS에서 사용하기 위해 hidden input 추가 -->
<input type="hidden" id="originalEmail" value="${user.email}">

<script src="/js/pages/mypage/edit.js"></script>
<%@ include file="../../common/footer.jsp" %>
</body>
</html>
