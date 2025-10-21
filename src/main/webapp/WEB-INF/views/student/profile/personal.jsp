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
    <link rel="stylesheet" href="/css/pages/mypage/profile/passwordcheck.css"/>
    <link rel="stylesheet" href="/css/pages/mypage/profile/personal.css"/>

    <title>강사 프로필 | Mulang?</title>
</head>
<body>
<%@include file="../../common/header.jsp" %>
<!-- 비밀번호 확인 모달 -->
<div id="passwordCheckModal" class="password-modal">
        <div class="password-modal-content">
            <h3>비밀번호 확인</h3>
            <c:if test="${empty passwordError}">
                <p>본인 확인을 위해 비밀번호를 입력해주세요.</p>
            </c:if>

            <c:if test="${not empty passwordError}">
                <div class="error-message">${passwordError}</div>
            </c:if>

            <form action="/student/check-password" method="post" id="passwordCheckForm">
                <input type="password" name="password" placeholder="비밀번호 입력" required>
                <div class="password-modal-buttons">
                    <button type="submit" class="confirm-btn">확인</button>
                    <button type="button" class="cancel-btn" onclick="closePasswordModal()">취소</button>
                </div>

        </form>
    </div>
</div>

<main>
    <div class="contents">

        <section class="dashboard">
            <%@ include file="../sidebar.jsp" %>

            <section class="content">
                <div class="profile-main">
                    <h2>강사 프로필</h2>

                    <section class="profile-section">
                        <!-- 강사명 -->
                        <div class="field">
                            <label>이름</label>
                            <div class="field-content">
                                <span class="text-value">${user.username}</span>
                            </div>
                        </div>

                        <!-- 프로필 이미지 -->
                        <div class="field">
                            <label>프로필 이미지</label>
                            <div class="field-content">
                                <div class="profile-img-wrap">
                                    <img src="/img/dummy/profile.jpg" alt="프로필 이미지">
                                </div>
                            </div>
                        </div>

                        <!-- 닉네임 -->
                        <div class="field">
                            <label>닉네임</label>
                            <div class="field-content">
                                <span class="text-value">${user.nickname}</span>
                            </div>
                        </div>


                        <!-- 이력 -->
                        <div class="field">
                            <label>이메일</label>
                            <div class="field-content">
                                <p class="text-area">${user.email}</p>
                            </div>
                        </div>

                        <!-- 클래스 장소 -->
                        <div class="field">
                            <label>비밀번호</label>
                            <div class="field-content">
                                <p class="text-area">***********</p>
                            </div>
                        </div>

                        <form id="editForm">
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

<script src = "/js/pages/mypage/personal.js"></script>
<script src = "/js/pages/mypage/passwordcheck.js"></script>
<script>
    document.addEventListener("DOMContentLoaded", function() {
        let hasError = false;
        <c:if test="${not empty passwordError}">
        hasError = true;
        </c:if>

        if (hasError) {
            const modal = document.getElementById("passwordCheckModal");
            modal.style.display = "flex";
        }
    });
</script>

<%@include file="../../common/footer.jsp" %>

</body>
</html>
