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
                                <input type="date" id="startDate" value="2025-06-18">
                                <span class="date-separator">~</span>
                                <input type="date" id="endDate" value="${search.endDate != null ? search.endDate.toLocalDate() : ''}">
                            </div>
                        </div>
                        <!-- JSP 하단에 초기 날짜 설정 스크립트 추가 -->
                        <script>
                            // 날짜 초기값 설정
                            document.addEventListener('DOMContentLoaded', function() {
                                const startDateInput = document.getElementById('startDate');
                                const endDateInput = document.getElementById('endDate');

                                // 서버에서 값이 없으면 기본값 설정
                                if (!startDateInput.value) {
                                    const today = new Date();
                                    const firstDay = new Date(today.getFullYear(), today.getMonth(), 1);
                                    startDateInput.value = formatDate(firstDay);
                                }

                                if (!endDateInput.value) {
                                    endDateInput.value = formatDate(new Date());
                                }
                            });

                            function formatDate(date) {
                                const year = date.getFullYear();
                                const month = String(date.getMonth() + 1).padStart(2, '0');
                                const day = String(date.getDate()).padStart(2, '0');
                                return `${year}-${month}-${day}`;
                            }
                        </script>
                        <!-- 회원 구분 -->
                        <div class="filter-group">
                            <span class="filter-label">회원 구분</span>
                            <div class="radio-group">
                                <label>
                                    <input type="radio" name="role" value="ALL"
                                           <c:if test="${empty param.status || param.status == 'ALL'}">checked</c:if>>
                                    전체
                                </label>
                                <label>
                                    <input type="radio" name="role" value="STUDENT"
                                           <c:if test="${param.role == 'STUDENT'}">checked</c:if>>
                                    학생
                                </label>
                                <label>
                                    <input type="radio" name="role" value="TEACHER"
                                           <c:if test="${param.role == 'TEACHER'}">checked</c:if>>
                                    강사
                                </label>
                            </div>
                        </div>

                        <!-- 상태 -->
                        <div class="filter-group">
                            <span class="filter-label">상태</span>
                            <div class="radio-group">
                                <label>
                                    <input type="radio" name="status" value="ALL"
                                           <c:if test="${empty param.status || param.status == 'ALL'}">checked</c:if>>
                                    전체
                                </label>
                                <label>
                                    <input type="radio" name="status" value="ACTIVE"
                                           <c:if test="${param.status == 'ACTIVE'}">checked</c:if>>
                                    활성
                                </label>
                                <label>
                                    <input type="radio" name="status" value="INACTIVE"
                                           <c:if test="${param.status == 'INACTIVE'}">checked</c:if>>
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
                            <input type="text" id="searchKeyword" name="keyword" value="${param.keyword}"
                                   placeholder="이름 또는 이메일 검색">
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

                <!-- -------------------- 회원 테이블 -------------------- -->
                <div class="table-wrap">
                    <table id="memberTable">
                        <thead>
                        <tr>
                            <th>번호</th>
                            <th>상태</th>
                            <th>이름</th>
                            <th>닉네임</th>
                            <th>이메일</th>
                            <th>가입일</th>
                            <th>구분</th>
                            <th>계정상태</th>
                            <th>관리</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="user" items="${users}" varStatus="status">
                            <tr data-id="${user.id}"
                                data-role="${user.role}"
                                data-status="${user.userStatus == "ACTIVE" ? 'active' : 'inactive'}"
                                data-date="${user.createdAt}">
                                <td>${currentPage * size + status.index + 1}</td>
                                <td>
                                    <span class="status-dot ${user.userStatus ? 'success' : 'inactive'}"></span>
                                </td>
                                <td>${user.username}</td>
                                <td>${user.nickname}</td>
                                <td>${user.email}</td>
                                <td>${user.createdAt}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${user.role == 'TEACHER'}">
                                            <span class="role-badge tutor">강사</span>
                                        </c:when>
                                        <c:when test="${user.role == 'STUDENT'}">
                                            <span class="role-badge student">학생</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="role-badge admin">관리자</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td class="status-cell">
                                    <span class="status-badge ${user.userStatus == "ACTIVE" ? 'active' : 'inactive'}">
                                            ${user.userStatus == "ACTIVE" ? '정상' : '정지'}
                                    </span>
                                </td>
                                <td class="actions">
                                    <button class="btn-edit" onclick="editStatus(${user.id})">수정</button>
                                    <button class="btn-delete"
                                            onclick="confirmDelete(${user.id}, '${user.nickname}')">삭제
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>

                        <c:if test="${empty users}">
                            <tr>
                                <td colspan="9" class="no-data">등록된 회원이 없습니다.</td>
                            </tr>
                        </c:if>
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

<!-- 삭제 확인 모달 -->
<div id="deleteModal" class="modal-overlay">
    <div class="modal-content">
        <div class="modal-header">
            <h3>회원 영구 삭제</h3>
        </div>
        <div class="modal-body">
            <p><strong id="deleteMemberName"></strong>님을 정말 삭제하시겠습니까?</p>
            <p class="warning">⚠️ 이 작업은 되돌릴 수 없습니다.</p>
        </div>
        <div class="modal-footer">
            <button class="modal-btn-cancel" onclick="closeDeleteModal()">취소</button>
            <button class="modal-btn-confirm" onclick="executeDelete()">삭제</button>
        </div>
    </div>
</div>

<!-- 서버 데이터 전달 (수정됨) -->
<script>
    // 기본값 처리로 undefined 방지
    window.paginationData = {
        currentPage: ${currentPage},       // Page.number는 0-based → 1-based로 보정
        totalPages: ${totalPages},        // Page.totalPages로 전체 페이지 수
        baseUrl: '/admin/user'
    };

    console.log('Pagination Data:', window.paginationData);
</script>



<!-- JavaScript 파일 로드 -->
<script src="/js/common/currentTime.js"></script>
<script src="/js/pages/admin/pagination.js"></script>
<script src="/js/pages/admin/user/user.js"></script>

</body>
</html>