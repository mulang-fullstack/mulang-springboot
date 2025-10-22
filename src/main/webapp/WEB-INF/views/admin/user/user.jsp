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
            <h1>사용자 관리</h1>
            <div class="header-info">
                <div class="info-box"><p>안녕하세요 <span>관리자</span>님</p></div>
                <a class="logout" href="/auth/logout">로그아웃</a>
            </div>
        </header>

        <!-- -------------------- 메인 콘텐츠 -------------------- -->
        <div class="content-wrap">
            <!-- 페이지 헤더 -->
            <div class="content-header">
                <h2>회원 조회</h2>
                <p class="date-range" id="current-time"></p>
            </div>

            <!-- -------------------- 필터/검색 영역 -------------------- -->
            <section class="member-section">
                <div class="filter-bar">
                    <div class="filter-container">
                        <!-- 회원 구분 -->
                        <div class="filter-group">
                            <span class="filter-label">회원 구분</span>
                            <div class="checkbox-group">
                                <label>
                                    <input type="checkbox" name="role" value="USER"
                                           <c:if test="${empty param.roles || param.roles.contains('USER')}">checked</c:if>>
                                    학생
                                </label>
                                <label>
                                    <input type="checkbox" name="role" value="TUTOR"
                                           <c:if test="${empty param.roles || param.roles.contains('TUTOR')}">checked</c:if>>
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

                        <!-- 정렬 -->
                        <div class="filter-group">
                            <span class="filter-label">정렬</span>
                            <div class="select-wrapper">
                                <select id="sortSelect" name="sort">
                                    <option value="LATEST" <c:if test="${param.sort == 'LATEST'}">selected</c:if>>최신순</option>
                                    <option value="OLDEST" <c:if test="${param.sort == 'OLDEST'}">selected</c:if>>오래된순</option>
                                    <option value="NAME_ASC" <c:if test="${param.sort == 'NAME_ASC'}">selected</c:if>>이름순 (가나다)</option>
                                    <option value="NAME_DESC" <c:if test="${param.sort == 'NAME_DESC'}">selected</c:if>>이름순 (역순)</option>
                                </select>
                            </div>
                        </div>
                    </div>

                    <!-- 검색 영역 -->
                    <div class="search-section">
                        <form action="/admin/user/memberList" method="get" class="search-form">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                            <input type="text" name="keyword" value="${param.keyword}" placeholder="이름 또는 이메일 검색">
                            <button type="submit">검색</button>
                        </form>
                        <button class="filter-reset" onclick="resetFilters()">필터 초기화</button>
                    </div>
                </div>

                <!-- -------------------- 회원 테이블 -------------------- -->
                <div class="table-wrap">
                    <table id="memberTable">
                        <thead>
                        <tr>
                            <th>번호</th>
                            <th>이름</th>
                            <th>이메일</th>
                            <th>가입일</th>
                            <th>권한</th>
                            <th>상태</th>
                            <th>관리</th>
                        </tr>
                        </thead>
                        <tbody>
                        <!-- 테스트용 예시 데이터 -->
                        <tr data-id="999" data-role="TUTOR" data-status="INACTIVE" data-date="2025-10-16">
                            <td>999</td>
                            <td>테스트강사</td>
                            <td>test_tutor@mulang.com</td>
                            <td>2025-10-16</td>
                            <td>
                                <span class="role-badge tutor">강사</span>
                            </td>
                            <td class="status-cell">
                                <span class="status-badge inactive">비활성</span>
                            </td>
                            <td class="actions">
                                <button class="btn-edit" onclick="editStatus(999)">상태 수정</button>
                                <button class="btn-delete" onclick="confirmDelete(999, '테스트강사')">영구 삭제</button>
                            </td>
                        </tr>
                        <tr data-id="998" data-role="USER" data-status="INACTIVE" data-date="2025-10-17">
                            <td>998</td>
                            <td>테스트학생</td>
                            <td>test_tutor@mulang.com</td>
                            <td>2025-10-16</td>
                            <td>
                                <span class="role-badge user">학생</span>
                            </td>
                            <td class="status-cell">
                                <span class="status-badge inactive">비활성</span>
                            </td>
                            <td class="actions">
                                <button class="btn-edit" onclick="editStatus(998)">상태 수정</button>
                                <button class="btn-delete" onclick="confirmDelete(998, '테스트학생')">영구 삭제</button>
                            </td>
                        </tr>
                        <c:forEach var="member" items="${members}">
                            <tr data-id="${member.id}"
                                data-role="${member.role}"
                                data-status="${member.active ? 'ACTIVE' : 'INACTIVE'}"
                                data-date="${member.createdAt}">
                                <td>${member.id}</td>
                                <td>${member.name}</td>
                                <td>${member.email}</td>
                                <td>${member.createdAt}</td>
                                <td>
                                    <span class="role-badge ${member.role == 'USER' ? 'user' : 'tutor'}">
                                        <c:choose>
                                            <c:when test="${member.role == 'USER'}">학생</c:when>
                                            <c:when test="${member.role == 'TUTOR'}">강사</c:when>
                                            <c:otherwise>${member.role}</c:otherwise>
                                        </c:choose>
                                    </span>
                                </td>
                                <td class="status-cell">
                                    <span class="status-badge ${member.active ? 'active' : 'inactive'}">
                                        <c:choose>
                                            <c:when test="${member.active}">활성</c:when>
                                            <c:otherwise>비활성</c:otherwise>
                                        </c:choose>
                                    </span>
                                </td>
                                <td class="actions">
                                    <button class="btn-edit" onclick="editStatus(${member.id})">상태 수정</button>
                                    <button class="btn-delete" onclick="confirmDelete(${member.id}, '${member.name}')">영구 삭제</button>
                                </td>
                            </tr>
                        </c:forEach>

                        <c:if test="${empty members}">
                            <tr>
                                <td colspan="7" class="no-data">등록된 회원이 없습니다.</td>
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

<!-- 서버 데이터 전달 -->
<script>
    window.serverData = {
        currentPage: ${empty currentPage ? 1 : currentPage},
        totalPages: ${empty totalPages ? 1 : totalPages},
        totalCount: ${empty totalCount ? 0 : totalCount}
    };
</script>

<!-- JavaScript 파일 로드 -->
<script src="/js/common/currentTime.js"></script>
<script src="/js/pages/admin/userManage/member.js"></script>

</body>
</html>