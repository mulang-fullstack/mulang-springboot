<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" href="/img/favicon.svg" type="image/png">
    <title>페이지를 찾을 수 없습니다</title>
    <link rel="stylesheet" href="/css/pages/error/error.css">
</head>
<body class="bg-blue">
<div class="error-container">
    <div class="error-icon">🔍</div>
    <h1 class="error-code blue">404</h1>
    <h2 class="error-title">페이지를 찾을 수 없습니다</h2>
    <p class="error-message">
        요청하신 페이지가 존재하지 않습니다.<br>
        URL을 확인해 주세요.
    </p>
    <a href="${pageContext.request.contextPath}/" class="btn-home blue">홈으로 돌아가기</a>
</div>
</body>
</html>