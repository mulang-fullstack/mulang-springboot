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
    <link rel="stylesheet" href="/css/pages/admin/content/course.css"/>
    <title>관리자 | 강의 신청 관리</title>
</head>
<body>
<div class="main-container">
    <%@ include file="../adminSidebar.jsp" %>
    <div class="right-container">
        <header>
            <h1>콘텐츠 관리 - 강의 신청 관리</h1>
            <div class="header-info">
                <div class="info-box"><p>안녕하세요 <span>관리자</span>님</p></div>
                <a class="logout" href="#">로그아웃</a>
            </div>
        </header>

        <div class="content-wrap">
            <div class="content-header">
                <p class="date-range" id="current-time">2025.10.19 18:03:24</p>
            </div>

            <section class="course-section">
                <div class="filter-bar">
                    <div class="filter-container">
                        <!-- 언어 -->
                        <div class="filter-group">
                            <span class="filter-label">언어</span>
                            <div class="radio-group">
                                <label><input type="radio" name="language" value="ALL" checked> 전체</label>
                                <label><input type="radio" name="language" value="1"> 영어</label>
                                <label><input type="radio" name="language" value="2"> 중국어</label>
                                <label><input type="radio" name="language" value="3"> 일본어</label>
                            </div>
                        </div>

                        <!-- 신청 상태 -->
                        <div class="filter-group">
                            <span class="filter-label">신청 상태</span>
                            <div class="radio-group">
                                <label><input type="radio" name="status" value="PENDING" checked> 심사대기</label>
                                <label><input type="radio" name="status" value="REVIEW"> 심사중</label>
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
                            <input type="text" id="searchKeyword" placeholder="강사 또는 강좌명 검색">
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
                                <option value="COURSE_NAME_ASC">강좌명순</option>
                                <option value="COURSE_NAME_DESC">강좌명순(역순)</option>
                                <option value="TEACHER_NAME_ASC">강사이름순</option>
                                <option value="TEACHER_NAME_DESC">강사이름순(역순)</option>
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

                <!-- 강의 신청 테이블 -->
                <div class="table-wrap">
                    <table>
                        <thead>
                        <tr>
                            <th>번호</th>
                            <th>강좌명</th>
                            <th>언어</th>
                            <th>강사</th>
                            <th>닉네임</th>
                            <th>신청일</th>
                            <th>상태</th>
                            <th>관리</th>
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
    // 초기 페이지네이션 데이터 (JavaScript가 업데이트함)
    window.paginationData = {
        currentPage: 0,
        totalPages: 1,
        baseUrl: '/admin/content/request'
    };
</script>

<script src="/js/common/currentTime.js"></script>
<script src="/js/pages/admin/pagination.js"></script>
<script src="/js/pages/admin/content/pendingCourse.js"></script>
</body>
</html>