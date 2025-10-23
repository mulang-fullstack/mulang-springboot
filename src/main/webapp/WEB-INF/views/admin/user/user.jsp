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
                                <input type="date" value="2025-10-13">
                                <span class="date-separator">~</span>
                                <input type="date" value="2025-10-19">
                            </div>
                        </div>
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
                        <!-- 테스트용 예시 데이터 -->
                        <tr data-id="999" data-role="TUTOR" data-status="INACTIVE" data-date="2025-10-16">
                            <td>1</td>
                            <td><span class="status-dot success"></span></td>
                            <td>김보카</td>
                            <td>테스트강사</td>
                            <td>test_tutor@mulang.com</td>
                            <td>2025-10-16 07:17:52</td>
                            <td><span class="role-badge tutor">강사</span></td>
                            <td class="status-cell"><span class="status-badge inactive">비활성</span></td>
                            <td class="actions">
                                <button class="btn-edit" onclick="editStatus(999)">수정</button>
                                <button class="btn-delete" onclick="confirmDelete(999, '테스트강사')">삭제</button>
                            </td>
                        </tr>

                        <tr data-id="1000" data-role="TUTOR" data-status="ACTIVE" data-date="2025-10-17">
                            <td>2</td>
                            <td><span class="status-dot success"></span></td>
                            <td>이서준</td>
                            <td>영어강사</td>
                            <td>english_tutor@mulang.com</td>
                            <td>2025-10-17 09:12:03</td>
                            <td><span class="role-badge tutor">강사</span></td>
                            <td class="status-cell"><span class="status-badge active">활성</span></td>
                            <td class="actions">
                                <button class="btn-edit" onclick="editStatus(1000)">수정</button>
                                <button class="btn-delete" onclick="confirmDelete(1000, '영어강사')">삭제</button>
                            </td>
                        </tr>

                        <tr data-id="1001" data-role="STUDENT" data-status="ACTIVE" data-date="2025-10-15">
                            <td>3</td>
                            <td><span class="status-dot success"></span></td>
                            <td>박하늘</td>
                            <td>수강생1</td>
                            <td>student1@mulang.com</td>
                            <td>2025-10-15 18:22:44</td>
                            <td><span class="role-badge student">학생</span></td>
                            <td class="status-cell"><span class="status-badge active">활성</span></td>
                            <td class="actions">
                                <button class="btn-edit" onclick="editStatus(1001)">수정</button>
                                <button class="btn-delete" onclick="confirmDelete(1001, '수강생1')">삭제</button>
                            </td>
                        </tr>

                        <tr data-id="1002" data-role="STUDENT" data-status="INACTIVE" data-date="2025-10-14">
                            <td>4</td>
                            <td><span class="status-dot fail"></span></td>
                            <td>최예준</td>
                            <td>수강생2</td>
                            <td>student2@mulang.com</td>
                            <td>2025-10-14 20:11:37</td>
                            <td><span class="role-badge student">학생</span></td>
                            <td class="status-cell"><span class="status-badge inactive">비활성</span></td>
                            <td class="actions">
                                <button class="btn-edit" onclick="editStatus(1002)">수정</button>
                                <button class="btn-delete" onclick="confirmDelete(1002, '수강생2')">삭제</button>
                            </td>
                        </tr>

                        <tr data-id="1003" data-role="ADMIN" data-status="ACTIVE" data-date="2025-10-18">
                            <td>5</td>
                            <td><span class="status-dot success"></span></td>
                            <td>한유진</td>
                            <td>일본냐옹이</td>
                            <td>cat@mulang.com</td>
                            <td>2025-10-18 08:42:19</td>
                            <td><span class="role-badge student">학생</span></td>
                            <td class="status-cell"><span class="status-badge active">활성</span></td>
                            <td class="actions">
                                <button class="btn-edit" onclick="editStatus(1003)">수정</button>
                                <button class="btn-delete" onclick="confirmDelete(1003, '시스템관리자')">삭제</button>
                            </td>
                        </tr>

                        <tr data-id="1004" data-role="TUTOR" data-status="INACTIVE" data-date="2025-10-19">
                            <td>6</td>
                            <td><span class="status-dot fail"></span></td>
                            <td>정민호</td>
                            <td>중국어강사</td>
                            <td>chinese_tutor@mulang.com</td>
                            <td>2025-10-19 13:15:28</td>
                            <td><span class="role-badge tutor">강사</span></td>
                            <td class="status-cell"><span class="status-badge inactive">비활성</span></td>
                            <td class="actions">
                                <button class="btn-edit" onclick="editStatus(1004)">수정</button>
                                <button class="btn-delete" onclick="confirmDelete(1004, '중국어강사')">삭제</button>
                            </td>
                        </tr>

                        <tr data-id="1005" data-role="STUDENT" data-status="ACTIVE" data-date="2025-10-13">
                            <td>7</td>
                            <td><span class="status-dot success"></span></td>
                            <td>서하은</td>
                            <td>수강생3</td>
                            <td>student3@mulang.com</td>
                            <td>2025-10-13 15:44:02</td>
                            <td><span class="role-badge student">학생</span></td>
                            <td class="status-cell"><span class="status-badge active">활성</span></td>
                            <td class="actions">
                                <button class="btn-edit" onclick="editStatus(1005)">수정</button>
                                <button class="btn-delete" onclick="confirmDelete(1005, '수강생3')">삭제</button>
                            </td>
                        </tr>

                        <tr data-id="1006" data-role="TUTOR" data-status="ACTIVE" data-date="2025-10-20">
                            <td>8</td>
                            <td><span class="status-dot success"></span></td>
                            <td>문서준</td>
                            <td>프랑스어강사</td>
                            <td>french_tutor@mulang.com</td>
                            <td>2025-10-20 09:50:43</td>
                            <td><span class="role-badge tutor">강사</span></td>
                            <td class="status-cell"><span class="status-badge active">활성</span></td>
                            <td class="actions">
                                <button class="btn-edit" onclick="editStatus(1006)">수정</button>
                                <button class="btn-delete" onclick="confirmDelete(1006, '프랑스어강사')">삭제</button>
                            </td>
                        </tr>

                        <tr data-id="1007" data-role="STUDENT" data-status="INACTIVE" data-date="2025-10-10">
                            <td>9</td>
                            <td><span class="status-dot fail"></span></td>
                            <td>배유진</td>
                            <td>수강생4</td>
                            <td>student4@mulang.com</td>
                            <td>2025-10-10 22:05:31</td>
                            <td><span class="role-badge student">학생</span></td>
                            <td class="status-cell"><span class="status-badge inactive">비활성</span></td>
                            <td class="actions">
                                <button class="btn-edit" onclick="editStatus(1007)">수정</button>
                                <button class="btn-delete" onclick="confirmDelete(1007, '수강생4')">삭제</button>
                            </td>
                        </tr>

                        <tr data-id="1008" data-role="TUTOR" data-status="ACTIVE" data-date="2025-10-12">
                            <td>10</td>
                            <td><span class="status-dot success"></span></td>
                            <td>조현우</td>
                            <td>일본어강사</td>
                            <td>japan_tutor@mulang.com</td>
                            <td>2025-10-12 12:31:45</td>
                            <td><span class="role-badge tutor">강사</span></td>
                            <td class="status-cell"><span class="status-badge active">활성</span></td>
                            <td class="actions">
                                <button class="btn-edit" onclick="editStatus(1008)">수정</button>
                                <button class="btn-delete" onclick="confirmDelete(1008, '일본어강사')">삭제</button>
                            </td>
                        </tr>

                        <%--
                        <c:forEach var="users" items="${user}">
                            <tr>

                            </tr>
                        </c:forEach>

                        <c:if test="${empty user}">
                            <tr>
                                <td colspan="9" class="no-data">등록된 회원이 없습니다.</td>
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
<script src="/js/pages/admin/user/user.js"></script>

</body>
</html>