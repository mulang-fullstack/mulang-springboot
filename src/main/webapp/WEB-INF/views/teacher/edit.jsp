<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" href="/img/favicon.svg" type="image/png">

    <link rel="stylesheet" href="/css/global.css"/>
    <link rel="stylesheet" href="/css/pages/teacher/profileEdit.css"/>

    <title>강사 프로필 관리 | Mulang?</title>
</head>
<body>
<%@ include file="../common/header.jsp" %>

<main>
    <div class="contents">
        <section class="dashboard">
            <%@ include file="sidebar.jsp" %>

            <section class="content">
                <div class="profile-main">
                    <h2>강사 프로필 관리</h2>

                    <form action="/teacher/mypage/profile/update"
                          method="post"
                          enctype="multipart/form-data"
                          id="editForm">

                        <!-- 이름 -->
                        <div class="field">
                            <label>이름</label>
                            <div class="field-content">
                                <span>${teacher.user.username}</span>
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
                                <button type="button" class="camera-btn" onclick="document.getElementById('fileInput').click();">
                                    <img src="/img/icon/bx-camera.svg" alt="사진 변경">
                                </button>
                                <input type="file" id="fileInput" name="photo" accept="image/*" hidden>
                            </div>

                        </div>

                        <!-- 닉네임 -->
                        <div class="field">
                            <label>닉네임</label>
                            <div class="input-wrap">
                                <input type="text" name="nickname" value="${teacher.user.nickname}">
                            </div>
                        </div>

                        <!-- 이메일 -->
                        <div class="field">
                            <label>이메일</label>
                            <div class="input-wrap">
                                <input type="text" name="email" value="${teacher.user.email}">
                            </div>
                        </div>

                        <!-- 담당 언어 -->
                        <div class="field">
                            <label>담당 언어</label>
                            <div class="input-wrap">
                                <select name="language.id">
                                    <c:forEach var="lang" items="${languages}">
                                        <option value="${lang.id}"
                                                <c:if test="${teacher.language.id == lang.id}">selected</c:if>>
                                                ${lang.name}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                        <!-- 소개 -->
                        <div class="field">
                            <label>소개</label>
                            <div class="input-wrap">
                                <textarea name="introduction" rows="4">${teacher.introduction}</textarea>
                            </div>
                        </div>

                        <!-- 경력 -->
                        <div class="field">
                            <label>경력</label>
                            <div class="input-wrap">
                                <textarea name="carreer" rows="3">${teacher.carreer}</textarea>
                            </div>
                        </div>

                        <div class="submit-wrap">
                            <button type="submit" class="submit-btn">수정 완료</button>
                        </div>
                    </form>
                </div>
            </section>
        </section>
    </div>
</main>

<script src="/js/pages/teacher/profileEditImage.js"></script>
<%@ include file="../common/footer.jsp" %>
</body>
</html>
