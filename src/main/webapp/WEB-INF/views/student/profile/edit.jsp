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
                    <h2>프로필 수정</h2>

                    <section class="profile-section">

                        <!-- 이메일 인증 form (action 수정) -->
                        <form id="emailVerifyForm" action="/student/editemail" method="post" style="display:none;">
                            <input type="hidden" name="email" id="hiddenEmail">
                        </form>

                        <!-- 프로필 수정 form -->
                        <form action="/student/edit" method="post" enctype="multipart/form-data" id="editForm">

                            <!-- 프로필 이미지 -->;',;;'
                            <div class="field">
                                <label>프로필 이미지</label>
                                <div class="field-content profile-img-area">
                                    <div class="profile-img-wrap">
                                        <img id="profile-preview"
                                             src="${not empty user.photoUrl ? user.photoUrl : '/img/dummy/profile.jpg'}"
                                             alt="프로필 이미지">
                                    </div>

                                    <!-- 카메라 버튼 -->
                                    <button type="button" class="camera-btn" onclick="document.getElementById('fileInput').click()">
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
                                <!--  닉네임 에러 메시지 -->
                                <c:if test="${not empty nicknameError}">
                                    <p class="error-text">${nicknameError}</p>
                                </c:if>
                            </div>

                            <!-- 이메일 -->
                            <div class="field">
                                <label>이메일</label>
                                <div class="input-wrap email-verify-wrap">
                                    <div class="email-input-group">
                                        <!-- 인증 완료 시에도 readonly로 변경 (disabled 대신) -->
                                        <input type="text"
                                               id="email"
                                               name="email"
                                               value="${user.email}"
                                        ${not empty emailVerified and emailVerified ? 'readonly style="background-color: #f5f5f5; cursor: not-allowed;"' : ''}>

                                        <!-- 인증 완료 시 버튼 변경 -->
                                        <c:choose>
                                            <c:when test="${not empty emailVerified and emailVerified}">
                                                <button type="button"
                                                        class="verify-btn verified"
                                                        disabled
                                                        style="background-color: #4CAF50; color: white; cursor: not-allowed;">
                                                    ✓ 인증완료
                                                </button>
                                            </c:when>
                                            <c:otherwise>
                                                <button type="button"
                                                        class="verify-btn"
                                                        id="verifyEmailBtn"
                                                        onclick="verifyEmailWithLoading()">
                                                    <span class="btn-text">인증하기</span>
                                                    <span class="spinner" style="display: none;">
                                                        <svg class="spinner-icon" viewBox="0 0 50 50">
                                    <circle class="path" cx="25" cy="25" r="20" fill="none" stroke-width="5"></circle>
                                    </svg>
                                 </span>
                                                </button>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>

                                    <!-- ✅ 이메일 에러 메시지 -->
                                    <c:if test="${not empty emailerror}">
                                        <p class="error-text">${emailerror}</p>
                                    </c:if>

                                    <!-- ✅ 이메일 전송 성공 메시지 (인증 완료 전) -->
                                    <c:if test="${not empty message and (empty emailVerified or not emailVerified)}">
                                        <p class="success-text">${message}</p>
                                    </c:if>

                                    <!-- 인증코드 입력창: 인증 완료되면 완전히 숨김 -->
                                    <c:if test="${empty emailVerified or not emailVerified}">
                                        <div class="email-code-group ${not empty showCodeInput ? 'show' : 'hidden'}">
                                            <input type="text"
                                                   id="emailCode"
                                                   placeholder="인증코드 6자리"
                                                   maxlength="6"
                                                ${empty showCodeInput ? 'disabled' : ''}>
                                            <button type="button"
                                                    class="verify-code-btn"
                                                    onclick="submitVerifyCode()">
                                                확인
                                            </button>
                                        </div>
                                    </c:if>

                                    <!-- ✅ 인증 완료 메시지 -->
                                    <c:if test="${not empty emailVerified and emailVerified}">
                                        <p class="success-text">✓ 이메일 인증이 완료되었습니다.</p>
                                    </c:if>
                                </div>

                                <!-- 인증된 이메일 hidden input (수정 시 검증용) -->
                                <c:if test="${not empty emailVerified and emailVerified}">
                                    <input type="hidden" name="emailVerified" value="true">
                                    <input type="hidden" name="verifiedEmail" value="${verifiedEmail}">
                                </c:if>
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
