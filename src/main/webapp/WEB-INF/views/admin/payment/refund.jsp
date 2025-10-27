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
    <link rel="stylesheet" href="/css/pages/admin/payment/refund.css"/>
    <title>관리자 | 환불 신청 관리</title>
</head>
<body>
<div class="main-container">
    <%@ include file="../adminSidebar.jsp" %>
    <div class="right-container">
        <header>
            <h1>결제 관리 - 환불 신청 관리</h1>
            <div class="header-info">
                <div class="info-box"><p>안녕하세요 <span>관리자</span>님</p></div>
                <a class="logout" href="/auth/logout">로그아웃</a>
            </div>
        </header>

        <div class="content-wrap">
            <div class="content-header">
                <p class="date-range" id="current-time"></p>
            </div>

            <section class="refund-section">
                <div class="filter-bar">
                    <div class="filter-container">
                        <!-- 기간 -->
                        <div class="filter-group">
                            <span class="filter-label">신청기간</span>
                            <div class="date-filter">
                                <input type="date" id="startDate">
                                <span class="date-separator">~</span>
                                <input type="date" id="endDate">
                            </div>
                        </div>

                        <!-- 처리 상태 -->
                        <div class="filter-group">
                            <span class="filter-label">처리 상태</span>
                            <div class="radio-group">
                                <label><input type="radio" name="status" value="ALL" checked> 전체</label>
                                <label><input type="radio" name="status" value="PENDING"> 대기중</label>
                                <label><input type="radio" name="status" value="APPROVED"> 승인됨</label>
                                <label><input type="radio" name="status" value="REJECTED"> 거절됨</label>
                                <label><input type="radio" name="status" value="COMPLETED"> 완료됨</label>
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
                            <input type="text" id="searchKeyword" placeholder="주문번호, 구매자, 강좌명 검색">
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
                                <option value="AMOUNT_DESC">금액 높은순</option>
                                <option value="AMOUNT_ASC">금액 낮은순</option>
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

                <!-- 환불 신청 테이블 -->
                <div class="table-wrap">
                    <table id="refundTable">
                        <thead>
                        <tr>
                            <th style="width: 60px;">번호</th>
                            <th style="width: 130px;">주문번호</th>
                            <th style="width: auto;">강좌명</th>
                            <th style="width: 120px;">구매자</th>
                            <th style="width: 200px;">환불 사유</th>
                            <th style="width: 100px;">환불금액</th>
                            <th style="width: 120px;">신청일시</th>
                            <th style="width: 100px;">상태</th>
                            <th style="width: 180px;">관리</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td colspan="9" class="no-data">데이터를 불러오는 중...</td>
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

<!-- 환불 상세 모달 -->
<div id="detailModal" class="modal-overlay">
    <div class="modal-content">
        <div class="modal-header">
            <h3>환불 신청 상세</h3>
        </div>
        <div class="modal-body" id="detailModalBody">
            <!-- JavaScript로 동적 생성 -->
        </div>
        <div class="modal-footer">
            <button class="modal-btn-cancel" onclick="closeDetailModal()">닫기</button>
        </div>
    </div>
</div>

<!-- 환불 승인 모달 -->
<div id="approveModal" class="modal-overlay">
    <div class="modal-content">
        <div class="modal-header">
            <h3>환불 승인</h3>
        </div>
        <div class="modal-body">
            <p style="margin-bottom: 12px; color: #4b5563;">
                <strong id="approveCourseTitle"></strong> 강좌의 환불을 승인하시겠습니까?
            </p>
            <div class="detail-row">
                <span class="detail-label">환불 금액</span>
                <span class="detail-value" style="color: #dc2626; font-weight: 700; font-size: 16px;" id="approveAmount"></span>
            </div>
            <div style="margin-top: 16px;">
                <label class="detail-label" style="display: block; margin-bottom: 8px;">관리자 메모 (선택)</label>
                <textarea id="approveAdminMemo" class="modal-textarea" placeholder="승인 시 메모를 입력하세요..."></textarea>
            </div>
        </div>
        <div class="modal-footer">
            <button class="modal-btn-cancel" onclick="closeApproveModal()">취소</button>
            <button class="modal-btn-approve" onclick="executeApprove()">승인</button>
        </div>
    </div>
</div>

<!-- 환불 거절 모달 -->
<div id="rejectModal" class="modal-overlay">
    <div class="modal-content">
        <div class="modal-header">
            <h3>환불 거절</h3>
        </div>
        <div class="modal-body">
            <p style="margin-bottom: 12px; color: #4b5563;">
                <strong id="rejectCourseTitle"></strong> 강좌의 환불을 거절하시겠습니까?
            </p>
            <div style="margin-top: 16px;">
                <label class="detail-label" style="display: block; margin-bottom: 8px;">거절 사유 (필수)</label>
                <textarea id="rejectAdminMemo" class="modal-textarea" placeholder="거절 사유를 입력하세요..." required></textarea>
            </div>
        </div>
        <div class="modal-footer">
            <button class="modal-btn-cancel" onclick="closeRejectModal()">취소</button>
            <button class="modal-btn-reject" onclick="executeReject()">거절</button>
        </div>
    </div>
</div>

<script>
    // 초기 페이지네이션 데이터
    window.paginationData = {
        currentPage: 0,
        totalPages: 1,
        baseUrl: '/admin/payment/refund'
    };

    // 날짜 초기값 설정
    document.addEventListener('DOMContentLoaded', function() {
        const startDateInput = document.getElementById('startDate');
        const endDateInput = document.getElementById('endDate');

        const today = new Date();
        const firstDay = new Date(today.getFullYear(), today.getMonth(), 1);

        startDateInput.value = formatDate(firstDay);
        endDateInput.value = formatDate(today);
    });

    function formatDate(date) {
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');
        return `${year}-${month}-${day}`;
    }
</script>

<script src="/js/common/currentTime.js"></script>
<script src="/js/pages/admin/pagination.js"></script>
<script src="/js/pages/admin/payment/refund.js"></script>
</body>
</html>