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
    <link rel="stylesheet" href="/css/pages/admin/system/inquiryManage.css"/>
    <title>관리자 | 1대1 문의 관리</title>
</head>
<body>
<div class="main-container">
    <%@ include file="../adminSidebar.jsp" %>
    <div class="right-container">
        <header>
            <h1>시스템 관리 - 1대1 문의</h1>
            <div class="header-info">
                <div class="info-box"><p>안녕하세요 <span>관리자</span>님</p></div>
                <a class="logout" href="/logout">로그아웃</a>
            </div>
        </header>

        <div class="content-wrap">
            <div class="content-header">
                <p class="date-range" id="current-time">2025.10.19 18:03:24</p>
            </div>

            <section class="inquiry-section">
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

                        <!-- 카테고리 -->
                        <div class="filter-group">
                            <span class="filter-label">카테고리</span>
                            <div class="radio-group">
                                <label><input type="radio" name="category" value="ALL" checked> 전체</label>
                                <label><input type="radio" name="category" value="ACCOUNT"> 계정</label>
                                <label><input type="radio" name="category" value="COURSE"> 강좌</label>
                                <label><input type="radio" name="category" value="PAYMENT"> 결제</label>
                                <label><input type="radio" name="category" value="TECHNICAL"> 기술지원</label>
                                <label><input type="radio" name="category" value="ETC"> 기타</label>
                            </div>
                        </div>

                        <!-- 답변 상태 -->
                        <div class="filter-group">
                            <span class="filter-label">답변 상태</span>
                            <div class="radio-group">
                                <label><input type="radio" name="status" value="WAITING" checked> 답변대기</label>
                                <label><input type="radio" name="status" value="COMPLETED"> 답변완료</label>
                            </div>
                        </div>
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
                            <input type="text" id="searchKeyword" placeholder="제목, 내용 또는 작성자 검색">
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
                                <option value="LATEST">최신순</option>
                                <option value="OLDEST">오래된순</option>
                                <option value="WAITING_FIRST">답변대기 우선</option>
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

                <!-- 문의 테이블 -->
                <div class="table-wrap">
                    <table>
                        <thead>
                        <tr>
                            <th style="width: 60px;">번호</th>
                            <th style="width: 100px;">카테고리</th>
                            <th style="width: auto;">제목</th>
                            <th style="width: 100px;">작성자</th>
                            <th style="width: 110px;">작성일</th>
                            <th style="width: 110px;">답변일</th>
                            <th style="width: 100px;">답변상태</th>
                            <th style="width: 140px;">관리</th>
                        </tr>
                        </thead>
                        <tbody>
                        <!-- JavaScript로 동적 생성 -->
                        <tr>
                            <td colspan="8" class="no-data">데이터를 불러오는 중...</td>
                        </tr>
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

<script>
    // 초기 페이지네이션 데이터
    window.paginationData = {
        currentPage: 0,
        totalPages: 1,
        baseUrl: '/admin/support/inquiry'
    };
</script>

<script src="/js/common/currentTime.js"></script>
<script src="/js/pages/admin/pagination.js"></script>
<script src="/js/pages/admin/system/inquiryManage.js"></script>
</body>
</html>