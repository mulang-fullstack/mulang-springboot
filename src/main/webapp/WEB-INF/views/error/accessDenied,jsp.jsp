<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>접근 거부</title>
    <link rel="stylesheet" href="/css/pages/error/error.css">
</head>
<body class="bg-purple">
<div class="error-container">
    <div class="error-icon">🚫</div>
    <h1 class="error-code purple">403</h1>
    <h2 class="error-title">접근 권한이 없습니다</h2>
    <p class="error-message">
        요청하신 페이지에 접근할 권한이 없습니다.<br>
        로그인이 필요하거나 권한이 부족합니다.
    </p>
    <a href="${pageContext.request.contextPath}/" class="btn-home purple">홈으로 돌아가기</a>
</div>
</body>
</html>