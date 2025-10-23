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
    <link rel="stylesheet" href="/css/pages/admin/user/userLog.css"/>
    <title>관리자 | 사용자 로그</title>
</head>
<body>
<div class="main-container">
    <%@ include file="../adminSidebar.jsp" %>
    <div class="right-container">
        <!-- -------------------- 헤더 -------------------- -->
        <header>
            <h1>사용자 관리 - 사용자 로그</h1>
            <div class="header-info">
                <div class="info-box"><p>안녕하세요 <span>관리자</span>님</p></div>
                <a class="logout" href="#">로그아웃</a>
            </div>
        </header>

        <!-- -------------------- 메인 콘텐츠 -------------------- -->
        <div class="content-wrap">
            <div class="content-header">
                <p class="date-range" id="current-time">2025.10.19 17:33:42</p>
            </div>

            <!-- -------------------- 필터/검색 -------------------- -->
            <section class="log-section">
                <div class="filter-bar">
                    <div class="filter-container">
                        <div class="filter-group">
                            <span class="filter-label">기간</span>
                            <div class="date-filter">
                                <input type="date" value="2025-10-13">
                                <span class="date-separator">~</span>
                                <input type="date" value="2025-10-19">
                            </div>
                        </div>
                        <div class="filter-group">
                            <span class="filter-label">로그 타입</span>
                            <div class="radio-group">
                                <label><input type="radio" name="logType" checked> 전체</label>
                                <label><input type="radio" name="logType"> 로그인</label>
                                <label><input type="radio" name="logType"> 로그아웃</label>
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
                            <input type="text" id="searchKeyword" name="keyword" value="${param.keyword}"
                                   placeholder="이름 또는 이메일 또는 IP 검색">
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
                                <option value="LATEST" <c:if test="${param.sort == 'LATEST'}">selected</c:if>>최신순
                                </option>
                                <option value="OLDEST" <c:if test="${param.sort == 'OLDEST'}">selected</c:if>>오래된순
                                </option>
                                <option value="NAME_ASC" <c:if test="${param.sort == 'NAME_ASC'}">selected</c:if>>이름순
                                    (가나다)
                                </option>
                                <option value="NAME_DESC" <c:if test="${param.sort == 'NAME_DESC'}">selected</c:if>>이름순
                                    (역순)
                                </option>
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

                <!-- -------------------- 로그 테이블 -------------------- -->
                <div class="table-wrap">
                    <table>
                        <thead>
                        <tr>
                            <th>번호</th>
                            <th>사용자명</th>
                            <th>이메일</th>
                            <th>IP 주소</th>
                            <th>브라우저</th>
                            <th>일시</th>
                            <th>로그 타입</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>10</td>
                            <td>김보카</td>
                            <td>boka@mulang.com</td>
                            <td>192.168.0.21</td>
                            <td>Chrome</td>
                            <td>2025-10-19 16:25:13</td>
                            <td><span class="log-badge login">로그인</span></td>
                        </tr>
                        <tr>
                            <td>9</td>
                            <td>이현주</td>
                            <td>hjlee@mulang.com</td>
                            <td>192.168.0.22</td>
                            <td>Edge</td>
                            <td>2025-10-19 15:50:42</td>
                            <td><span class="log-badge logout">로그아웃</span></td>
                        </tr>
                        <tr>
                            <td>8</td>
                            <td>박성민</td>
                            <td>smpark@mulang.com</td>
                            <td>192.168.0.32</td>
                            <td>Safari</td>
                            <td>2025-10-19 14:11:03</td>
                            <td><span class="log-badge login">로그인</span></td>
                        </tr>
                        <%--
                        <c:forEach var="userLogs" items="${userLog}">
                            <tr>

                            </tr>
                        </c:forEach>

                        <c:if test="${empty userLog}">
                            <tr>
                                <td colspan="9" class="no-data">발생한 로그가 없습니다.</td>
                            </tr>
                        </c:if>
                        --%>
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

<!-- 서버 데이터 전달 -->
<script>
    // 🔧 수정: 기본값 설정으로 undefined 오류 방지
    window.paginationData = {
        currentPage: ${not empty currentPage ? currentPage : 1},
        totalPages: ${not empty totalPages ? totalPages : 1},
        baseUrl: '/admin/user/log'
    };

    console.log('Pagination Data:', window.paginationData); // 디버깅용
</script>

<!-- JS -->
<script src="/js/common/currentTime.js"></script>
<script src="/js/pages/admin/pagination.js"></script>
<script src="/js/pages/admin/userLog.js"></script>
</body>
</html>
