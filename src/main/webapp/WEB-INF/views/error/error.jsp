<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" href="/img/favicon.svg" type="image/png">
    <title>오류 발생</title>
    <link rel="stylesheet" href="/css/pages/error/error.css">
</head>
<body class="bg-pink">
<div class="error-container">
    <div class="error-icon">⚠️</div>
    <h1 class="error-code pink">ERROR</h1>
    <h2 class="error-title">잘못된 요청입니다</h2>
    <p class="error-message">
        요청을 처리하는 중 오류가 발생했습니다.<br>
        잠시 후 다시 시도해 주세요.
    </p>
    <a href="${pageContext.request.contextPath}/" class="btn-home pink">홈으로 돌아가기</a>
</div>
</body>
</html>