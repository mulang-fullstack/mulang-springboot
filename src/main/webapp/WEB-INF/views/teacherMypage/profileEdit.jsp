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

                    <section class="profile-section">
                        <!-- 강사명 -->
                        <div class="field">
                            <label>강사명</label>
                            <div class="field-content">
                                <span>최윤서</span>
                            </div>
                        </div>

                        <!-- 프로필 이미지 -->
                        <div class="field">
                            <label>프로필 이미지</label>
                            <div class="field-content profile-img-area">
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
                                <input type="text" value="010-9593-8009">
                                <span class="check-icon">✔</span>
                            </div>
                        </div>

                        <!-- 강사 소개 -->
                        <div class="field">
                            <label>강사 소개</label>
                            <div class="input-wrap">
                                <textarea rows="2">나는 귀여운 윤서양</textarea>
                            </div>
                        </div>

                        <!-- 이력 -->
                        <div class="field">
                            <label>이력 (권장사항)</label>
                            <div class="input-wrap">
                                <input type="text" placeholder="+ 이력 입력">
                            </div>
                        </div>

                        <!-- 클래스 장소 -->
                        <div class="field">
                            <label>클래스 장소</label>
                            <div class="input-wrap">
                                <input type="text" placeholder="+ 장소 입력">
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

<%@ include file="../common/footer.jsp" %>
</body>
</html>
