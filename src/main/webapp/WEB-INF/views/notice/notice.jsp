<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <meta name="csrf-token" content="${_csrf.token}">
    <link rel="icon" href="/img/favicon.svg" type="image/png">
    <link rel="stylesheet" href="/css/global.css"/>
    <link rel="stylesheet" href="/css/pages/support/notice.css"/>
    <title>Mulang | 공지사항</title>
</head>
<body>
<%@include file="../common/header.jsp" %>

<main>
    <div class="contents">
        <div class="notice-header-row">
            <div class="page-header">
                <h1>공지사항</h1>
                <p>중요 공지 및 이벤트 소식을 확인하세요.</p>
            </div>

            <div class="search-wrapper">
                <i class="search-icon bi bi-search"></i>
                <input type="text" id="searchKeyword" placeholder="검색어를 입력하세요" />
                <button class="search-btn">검색</button>
            </div>
        </div>

        <!-- 공지사항 목록 -->
        <section class="notice-list">
            <div class="notice-table">
                <table>
                    <thead>
                    <tr>
                        <th>번호</th>
                        <th>구분</th>
                        <th>제목</th>
                        <th>등록일</th>
                    </tr>
                    </thead>
                    <tbody id="noticeList">
                    <!-- 로딩 메시지 -->
                    <tr>
                        <td colspan="4" style="text-align: center; padding: 80px 20px;">
                            <p style="color: var(--text-tertiary);">공지사항을 불러오는 중...</p>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </section>

        <!-- 페이지네이션 -->
        <div class="pagination" id="pagination">
            <!-- JavaScript로 동적 생성 -->
        </div>
    </div>
</main>

<%@include file="../common/footer.jsp" %>

<script src="/js/common/pagination.js"></script>
<script src="/js/pages/support/notice.js"></script>
</body>
</html>