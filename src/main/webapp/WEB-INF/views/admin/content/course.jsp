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
    <title>관리자 | 강좌 관리</title>
</head>
<body>
<div class="main-container">
    <%@ include file="../adminSidebar.jsp" %>
    <div class="right-container">
        <!-- -------------------- 헤더 -------------------- -->
        <header>
            <h1>콘텐츠 관리 - 강좌 조회</h1>
            <div class="header-info">
                <div class="info-box"><p>안녕하세요 <span>관리자</span>님</p></div>
                <a class="logout" href="#">로그아웃</a>
            </div>
        </header>

        <!-- -------------------- 메인 콘텐츠 -------------------- -->
        <div class="content-wrap">
            <div class="content-header">
                <p class="date-range" id="current-time">2025.10.19 18:03:24</p>
            </div>

            <!-- -------------------- 필터 / 검색 -------------------- -->
            <section class="course-section">
                <div class="filter-bar">

                    <div class="filter-container">
                        <!-- 기간 -->
                        <div class="filter-group">
                            <span class="filter-label">기간</span>
                            <div class="date-filter">
                                <input type="date" value="2025-10-13">
                                <span class="date-separator">~</span>
                                <input type="date" value="2025-10-19">
                            </div>
                        </div>
                        <!-- 언어 -->
                        <div class="filter-group">
                            <span class="filter-label">언어</span>
                            <div class="radio-group">
                                <label><input type="radio" name="language" checked> 전체</label>
                                <label><input type="radio" name="language"> 영어</label>
                                <label><input type="radio" name="language"> 일본어</label>
                                <label><input type="radio" name="language"> 중국어</label>
                            </div>
                        </div>

                        <div class="filter-group">
                            <span class="filter-label">강좌 상태</span>
                            <div class="radio-group">
                                <label><input type="radio" name="status" checked> 전체</label>
                                <label><input type="radio" name="status"> 공개</label>
                                <label><input type="radio" name="status"> 비공개</label>
                                <label><input type="radio" name="status"> 심사중</label>
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
                                   placeholder="강사 또는 강좌명 검색">
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

                <!-- -------------------- 강좌 테이블 -------------------- -->
                <div class="table-wrap">
                    <table>
                        <thead>
                        <tr>
                            <th>번호</th>
                            <th>강좌명</th>
                            <th>언어</th>
                            <th>강사</th>
                            <th>닉네임</th>
                            <th>등록일</th>
                            <th>상태</th>
                            <th>관리</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>5</td>
                            <td>영어회화 기초 마스터</td>
                            <td>영어</td>
                            <td>김보카</td>
                            <td>영어마스터</td>
                            <td>2025-10-14</td>
                            <td><span class="status-badge active">공개</span></td>
                            <td>
                                <button class="btn-edit">수정</button>
                                <button class="btn-delete">삭제</button>
                            </td>
                        </tr>
                        <tr>
                            <td>4</td>
                            <td>JLPT N2 실전 대비</td>
                            <td>일본어</td>
                            <td>이현주</td>
                            <td>사무라이</td>
                            <td>2025-10-11</td>
                            <td><span class="status-badge review">심사중</span></td>
                            <td>
                                <button class="btn-edit">수정</button>
                                <button class="btn-delete">삭제</button>
                            </td>
                        </tr>
                        <tr>
                            <td>3</td>
                            <td>HSK 5급 듣기 특강</td>
                            <td>중국어</td>
                            <td>박성민</td>
                            <td>짜장마스터</td>
                            <td>2025-09-28</td>
                            <td><span class="status-badge hidden">비공개</span></td>
                            <td>
                                <button class="btn-edit">수정</button>
                                <button class="btn-delete">삭제</button>
                            </td>
                        </tr>
                        <%--
                        <c:forEach var="courses" items="${course}">
                            <tr>

                            </tr>
                        </c:forEach>

                        <c:if test="${empty course}">
                            <tr>
                                <td colspan="9" class="no-data">등록된 강좌가 없습니다.</td>
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
<!-- 서버 데이터 전달 (수정됨) -->
<script>
    // 🔧 수정: 기본값 설정으로 undefined 오류 방지
    window.paginationData = {
        currentPage: ${not empty currentPage ? currentPage : 1},
        totalPages: ${not empty totalPages ? totalPages : 1},
        baseUrl: '/admin/user'
    };

    console.log('Pagination Data:', window.paginationData); // 디버깅용
</script>

<!-- JavaScript 파일 로드 -->
<script src="/js/common/currentTime.js"></script>
<script src="/js/pages/admin/pagination.js"></script>
<script src="/js/pages/admin/content/course.js"></script>
</body>
</html>
