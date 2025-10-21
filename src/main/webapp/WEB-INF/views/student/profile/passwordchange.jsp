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
    <link rel="stylesheet" href="/css/pages/mypage/profile/passwordchange.css"/>

    <title>비밀번호 변경 | Mulang?</title>
</head>
<body>
<%@include file="../../common/header.jsp" %>

<main>
    <div class="contents">
        <section class="dashboard">
            <%@ include file="../sidebar.jsp" %>

            <section class="content">
                <div class="password-change-main">
                    <h2>비밀번호 변경</h2>

                    <section class="password-change-section">
                        <form id="passwordChangeForm" method="post" action="/student/passwordchange">

                            <!-- 새 비밀번호 -->
                            <div class="form-field">
                                <label for="newpassword">새 비밀번호</label>
                                <div class="input-wrapper">
                                    <input
                                            type="password"
                                            id="newpassword"
                                            name="newpassword"
                                            placeholder="새 비밀번호를 입력하세요"
                                            required
                                    >
                                    <span class="input-description">6자 이상, 영문/숫자/특수문자 조합</span>
                                </div>
                            </div>

                            <!-- 새 비밀번호 확인 -->
                            <div class="form-field">
                                <label for="confirmpassword">새 비밀번호 확인</label>
                                <div class="input-wrapper">
                                    <input
                                            type="password"
                                            id="confirmpassword"
                                            name="confirmpassword"
                                            placeholder="새 비밀번호를 다시 입력하세요"
                                            required
                                    >
                                </div>
                                    <span class="error-message" id="passworderror">
                                        <c:if test="${not empty passworderror}">
                                            ${passworderror}
                                    </c:if>
                                    </span>
                                </div>

                            <!-- 버튼 영역 -->
                            <div class="button-group">
                                <button type="button" class="cancel-btn" onclick="history.back()">취소</button>
                                <button type="submit" class="submit-btn">변경하기</button>
                            </div>

                        </form>
                    </section>
                </div>
            </section>
        </section>
    </div>
</main>

<script src="/js/pages/mypage/passwordchange.js"></script>

<%@include file="../../common/footer.jsp" %>

</body>
</html>
