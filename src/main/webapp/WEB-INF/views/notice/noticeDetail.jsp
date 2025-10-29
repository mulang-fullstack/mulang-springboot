<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="icon" href="/img/favicon.svg" type="image/png">
    <link rel="stylesheet" href="/css/global.css"/>
    <link rel="stylesheet" href="/css/pages/support/noticeDetail.css"/>
    <title>Mulang | 공지사항 상세</title>
</head>
<body>
<%@include file="../common/header.jsp" %>

<main>
    <div class="contents">
        <!-- 상단 네비게이션 -->
        <div class="detail-nav">
            <button class="btn-back" onclick="goBack()">
                <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <path d="M19 12H5M12 19l-7-7 7-7"/>
                </svg>
                목록으로
            </button>
        </div>

        <!-- 공지사항 상세 -->
        <div class="notice-detail">
            <!-- 헤더 -->
            <div class="detail-header">
                <div class="badge-wrapper">
                    <span class="notice-badge general">
                        ${notice.type == 'GENERAL' ? '일반'
                                : notice.type == 'EVENT' ? '이벤트'
                                : notice.type == 'SYSTEM' ? '시스템'
                                : notice.type == 'UPDATE' ? '업데이트'
                                : '-'}
                    </span>
                </div>
                <h1 class="detail-title">${notice.title}</h1>
                <div class="detail-meta">
                    <div class="meta-item">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            <circle cx="12" cy="12" r="10"></circle>
                            <polyline points="12 6 12 12 16 14"></polyline>
                        </svg>
                        <span>${notice.createdAt}</span>
                    </div>
                </div>
            </div>

            <!-- 본문 -->
            <div class="detail-content">
                ${notice.content}
            </div>
        </div>
    </div>
</main>

<%@include file="../common/footer.jsp" %>

<script>
    function goBack() {
        window.location.href = '/support/notice';
    }
</script>
</body>
</html>