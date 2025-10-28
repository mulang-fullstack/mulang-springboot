<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" href="/img/favicon.svg" type="image/png">
    <link rel="stylesheet" href="/css/global.css"/>
    <link rel="stylesheet" href="/css/pages/admin/admin.css"/>
    <link rel="stylesheet" href="/css/pages/admin/system/noticeManage.css"/>
    <title>관리자 | 공지사항 관리</title>
</head>
<body>
<div class="main-container">
    <%@ include file="../adminSidebar.jsp" %>
    <div class="right-container">
        <header>
            <h1>시스템 관리 - 공지사항</h1>
            <div class="header-info">
                <div class="info-box"><p>안녕하세요 <span>관리자</span>님</p></div>
                <a class="logout" href="/logout">로그아웃</a>
            </div>
        </header>

        <div class="content-wrap">
            <div class="content-header">
                <p class="date-range" id="current-time">2025.10.19 18:03:24</p>
            </div>

            <section class="notice-section">
                <div class="filter-bar">
                    <div class="filter-container">
                        <!-- 기간 -->
                        <div class="filter-group">
                            <span class="filter-label">기간</span>
                            <div class="date-filter">
                                <input type="date" id="startDate">
                                <span class="date-separator">~</span>
                                <input type="date" id="endDate">
                            </div>
                        </div>

                        <!-- 공지 유형 -->
                        <div class="filter-group">
                            <span class="filter-label">공지 유형</span>
                            <div class="radio-group">
                                <label><input type="radio" name="noticeType" value="ALL" checked> 전체</label>
                                <label><input type="radio" name="noticeType" value="GENERAL"> 일반</label>
                                <label><input type="radio" name="noticeType" value="EVENT"> 이벤트</label>
                                <label><input type="radio" name="noticeType" value="UPDATE"> 업데이트</label>
                                <label><input type="radio" name="noticeType" value="SYSTEM"> 시스템</label>
                            </div>
                        </div>

                        <!-- 상태 -->
                        <div class="filter-group">
                            <span class="filter-label">상태</span>
                            <div class="radio-group">
                                <label><input type="radio" name="status" value="ALL" checked> 전체</label>
                                <label><input type="radio" name="status" value="PUBLIC"> 공개</label>
                                <label><input type="radio" name="status" value="PRIVATE"> 비공개</label>
                            </div>
                        </div>

                        <button class="btn-create"  onclick="openNoticeModal()">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24"
                                 fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                <path d="M5 12h14"></path>
                                <path d="M12 5v14"></path>
                            </svg>
                            공지사항 작성
                        </button>
                    </div>

                    <!-- 검색 영역 -->
                    <div class="search-section">
                        <div class="search-input-wrapper">
                            <svg class="search-icon" xmlns="http://www.w3.org/2000/svg" width="18" height="18"
                                 viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"
                                 stroke-linecap="round" stroke-linejoin="round">
                                <circle cx="11" cy="11" r="8"></circle>
                                <path d="m21 21-4.35-4.35"></path>
                            </svg>
                            <input type="text" id="searchKeyword" placeholder="제목 또는 내용 검색">
                        </div>
                        <button class="search-btn" type="button">검색</button>

                        <!-- 정렬 -->
                        <div class="sort-wrapper">
                            <svg class="sort-icon" xmlns="http://www.w3.org/2000/svg" width="16" height="16"
                                 viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"
                                 stroke-linecap="round" stroke-linejoin="round">
                                <path d="m3 16 4 4 4-4"></path>
                                <path d="M7 20V4"></path>
                                <path d="m21 8-4-4-4 4"></path>
                                <path d="M17 4v16"></path>
                            </svg>
                            <select id="sortSelect">
                                <option value="LATEST" selected>최신순</option>
                                <option value="OLDEST">오래된순</option>
                                <option value="TITLE_ASC">제목순</option>
                                <option value="TITLE_DESC">제목순(역순)</option>
                            </select>
                        </div>

                        <button class="filter-reset" type="button" onclick="resetFilters()">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24"
                                 fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"
                                 stroke-linejoin="round">
                                <path d="M3 12a9 9 0 0 1 9-9 9.75 9.75 0 0 1 6.74 2.74L21 8"></path>
                                <path d="M21 3v5h-5"></path>
                                <path d="M21 12a9 9 0 0 1-9 9 9.75 9.75 0 0 1-6.74-2.74L3 16"></path>
                                <path d="M3 21v-5h5"></path>
                            </svg>
                            초기화
                        </button>
                    </div>
                </div>

                <!-- 공지사항 테이블 -->
                <div class="table-wrap">
                    <table>
                        <thead>
                        <tr>
                            <th >번호</th>
                            <th >유형</th>
                            <th >제목</th>
                            <th >작성자</th>
                            <th >등록일</th>
                            <th >상태</th>
                            <th >관리</th>
                        </tr>
                        </thead>
                        <tbody>
                        <!-- JavaScript로 동적 생성 -->
                        </tbody>
                    </table>
                </div>

                <!-- 페이징 -->
                <div class="pagination" id="pagination">
                    <!-- JavaScript로 동적 생성 -->
                </div>
            </section>
        </div>
    </div>
</div>
<%@ include file="noticeModal.jsp" %>

<script src="/js/common/currentTime.js"></script>
<script src="/js/pages/admin/pagination.js"></script>
<script src="/js/pages/admin/system/noticeManage.js"></script>
</body>
</html>