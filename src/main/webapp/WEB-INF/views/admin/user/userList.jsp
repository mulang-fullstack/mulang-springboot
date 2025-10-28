<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="csrf-token" content="${_csrf.token}">
    <link rel="icon" href="/img/favicon.svg" type="image/png">
    <link rel="stylesheet" href="/css/global.css"/>
    <link rel="stylesheet" href="/css/pages/admin/admin.css"/>
    <link rel="stylesheet" href="/css/pages/admin/user/user.css"/>
    <title>관리자 | 회원 조회</title>
</head>
<body>
<div class="main-container">
    <%@ include file="../adminSidebar.jsp" %>

    <div class="right-container">
        <!-- -------------------- 헤더 -------------------- -->
        <header>
            <h1>사용자 관리 - 회원조회</h1>
            <div class="header-info">
                <div class="info-box"><p>안녕하세요 <span>관리자</span>님</p></div>
                <a class="logout" href="/auth/logout">로그아웃</a>
            </div>
        </header>

        <!-- -------------------- 메인 콘텐츠 -------------------- -->
        <div class="content-wrap">
            <!-- 페이지 헤더 -->
            <div class="content-header">
                <p class="date-range" id="current-time"></p>
            </div>
            <!-- -------------------- 필터/검색 영역 -------------------- -->
            <section class="member-section">
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
                        <!-- 회원 구분 -->
                        <div class="filter-group">
                            <span class="filter-label">회원 구분</span>
                            <div class="radio-group">
                                <label>
                                    <input type="radio" name="role" value="STUDENT" checked>
                                    학생
                                </label>
                                <label>
                                    <input type="radio" name="role" value="TEACHER">
                                    강사
                                </label>
                            </div>
                        </div>

                        <!-- 상태 -->
                        <div class="filter-group">
                            <span class="filter-label">상태</span>
                            <div class="radio-group">
                                <label>
                                    <input type="radio" name="status" value="ALL" checked>
                                    전체
                                </label>
                                <label>
                                    <input type="radio" name="status" value="ACTIVE">
                                    활성
                                </label>
                                <label>
                                    <input type="radio" name="status" value="INACTIVE">
                                    비활성
                                </label>
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
                            <input type="text" id="searchKeyword" name="keyword" placeholder="이름 또는 이메일 검색">
                        </div>
                        <button class="search-btn" type="submit">검색</button>
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
                            <select id="sortSelect" name="sort">
                                <option value="LATEST" selected>최신순</option>
                                <option value="OLDEST">오래된순</option>
                                <option value="NAME_ASC">이름순 (가나다)</option>
                                <option value="NAME_DESC">이름순 (역순)</option>
                            </select>
                        </div>
                        <button class="filter-reset" onclick="resetFilters()">
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

                <!-- -------------------- 회원 테이블 -------------------- -->
                <div class="table-wrap">
                    <table id="memberTable">
                        <thead>
                        <tr>
                            <th>번호</th>
                            <th>구분</th>
                            <th>이름</th>
                            <th>닉네임</th>
                            <th>이메일</th>
                            <th>가입일</th>
                            <th>계정상태</th>
                            <th>관리</th>
                        </tr>
                        </thead>
                        <tbody>
                        <!-- JavaScript로 동적 생성 -->
                        </tbody>
                    </table>
                </div>

                <!-- -------------------- 페이징 -------------------- -->
                <div class="pagination" id="pagination">
                    <!-- JavaScript로 동적 생성 -->
                </div>
            </section>
        </div>
    </div>
</div>

<!-- JavaScript 파일 로드 -->
<script src="/js/common/currentTime.js"></script>
<script src="/js/pages/admin/pagination.js"></script>
<script src="/js/pages/admin/user/user.js"></script>

</body>
</html>