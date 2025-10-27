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
    <link rel="stylesheet" href="/css/pages/admin/payment/payment.css"/>
    <title>관리자 | 결제 내역 관리</title>
</head>
<body>
<div class="main-container">
    <%@ include file="../adminSidebar.jsp" %>
    <div class="right-container">
        <header>
            <h1>결제 관리 - 결제 내역 조회</h1>
            <div class="header-info">
                <div class="info-box"><p>안녕하세요 <span>관리자</span>님</p></div>
                <a class="logout" href="/logout">로그아웃</a>
            </div>
        </header>

        <div class="content-wrap">
            <div class="content-header">
                <p class="date-range" id="current-time">2025.10.19 18:03:24</p>
            </div>

            <section class="payment-section">
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

                        <!-- 결제 수단 -->
                        <div class="filter-group">
                            <span class="filter-label">결제 수단</span>
                            <div class="radio-group">
                                <label><input type="radio" name="paymentMethod" value="ALL" checked> 전체</label>
                                <label><input type="radio" name="paymentMethod" value="CARD"> 카드</label>
                                <label><input type="radio" name="paymentMethod" value="BANK"> 계좌이체</label>
                                <label><input type="radio" name="paymentMethod" value="KAKAO"> 카카오페이</label>
                                <label><input type="radio" name="paymentMethod" value="NAVER"> 네이버페이</label>
                            </div>
                        </div>

                        <!-- 결제 상태 -->
                        <div class="filter-group">
                            <span class="filter-label">결제 상태</span>
                            <div class="radio-group">
                                <label><input type="radio" name="status" value="ALL" checked> 전체</label>
                                <label><input type="radio" name="status" value="SUCCESS"> 완료</label>
                                <label><input type="radio" name="status" value="PENDING"> 대기</label>
                                <label><input type="radio" name="status" value="FAILED"> 실패</label>
                                <label><input type="radio" name="status" value="REFUND"> 환불</label>
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
                                <option value="BUYER_ASC">구매자순</option>
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

                <!-- 결제 내역 테이블 -->
                <div class="table-wrap">
                    <table>
                        <thead>
                        <tr>
                            <th style="width: 80px;">번호</th>
                            <th style="width: auto;">강좌명</th>
                            <th style="width: 100px;">구매자</th>
                            <th style="width: 110px;">결제금액</th>
                            <th style="width: 100px;">결제수단</th>
                            <th style="width: 120px;">결제일시</th>
                            <th style="width: 100px;">상태</th>
                            <th style="width: 120px;">관리</th>
                        </tr>
                        </thead>
                        <tbody>
                        <!-- JavaScript로 동적 생성 -->
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

<script>
    // 초기 페이지네이션 데이터
    window.paginationData = {
        currentPage: 0,
        totalPages: 1,
        baseUrl: '/admin/payment'
    };
</script>

<script src="/js/common/currentTime.js"></script>
<script src="/js/pages/admin/pagination.js"></script>
<script src="/js/pages/admin/payment/payment.js"></script>
</body>
</html>