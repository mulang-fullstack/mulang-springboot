<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="icon" href="/img/favicon.svg" type="image/png">
    <link rel="stylesheet" href="/css/global.css"/>
    <link rel="stylesheet" href="/css/pages/admin/admin.css"/>
    <link rel="stylesheet" href="/css/pages/admin/dashboard/sales.css"/>
    <script src="/js/common/currentTime.js"></script>
    <title>관리자 | 매출 현황</title>
</head>
<body>
<div class="main-container">
    <!-- left-container -->
    <%@include file="../adminSidebar.jsp" %>

    <!-- right-container -->
    <div class="right-container">
        <header>
            <h1>대시보드 - 매출현황</h1>
            <div class="header-info">
                <div class="info-box"><p>안녕하세요 <span>관리자</span>님</p></div>
                <a class="logout" href="/auth/logout">로그아웃</a>
            </div>
        </header>

        <div class="content-wrap">
            <div class="content-header">
                <p class="date-range" id="current-time"></p>
            </div>

            <!-- -------------------- 통계 카드 -------------------- -->
            <div class="stats-container">
                <!-- 월 매출 -->
                <div class="stat-card">
                    <div class="stat-icon total-sales">
                        <!-- 월 매출: 상승 차트 -->
                        <svg width="24" height="24" viewBox="0 0 24 24" fill="none"
                             stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                            <polyline points="23 6 13.5 15.5 8.5 10.5 1 18"/>
                            <polyline points="17 6 23 6 23 12"/>
                        </svg>
                    </div>
                    <div class="stat-info">
                        <h3>월 매출</h3>
                        <p class="stat-number">₩0</p>
                        <span class="stat-trend up">데이터 로딩 중...</span>
                    </div>
                </div>

                <!-- 오늘 매출 -->
                <div class="stat-card">
                    <div class="stat-icon today-sales">
                        <!-- 오늘 매출: 달러 기호 -->
                        <svg width="24" height="24" viewBox="0 0 24 24" fill="none"
                             stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                            <line x1="12" y1="1" x2="12" y2="23"/>
                            <path d="M17 5H9.5a3.5 3.5 0 0 0 0 7h5a3.5 3.5 0 0 1 0 7H6"/>
                        </svg>
                    </div>
                    <div class="stat-info">
                        <h3>오늘 매출</h3>
                        <p class="stat-number">₩0</p>
                        <span class="stat-trend up">데이터 로딩 중...</span>
                    </div>
                </div>

                <!-- 오늘 결제 건수 -->
                <div class="stat-card">
                    <div class="stat-icon today-payment">
                        <!-- 오늘 결제 건수: 신용카드 -->
                        <svg width="24" height="24" viewBox="0 0 24 24" fill="none"
                             stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                            <rect x="1" y="4" width="22" height="16" rx="2" ry="2"/>
                            <line x1="1" y1="10" x2="23" y2="10"/>
                        </svg>
                    </div>
                    <div class="stat-info">
                        <h3>오늘 결제 건수</h3>
                        <p class="stat-number">0</p>
                        <span class="stat-trend up">데이터 로딩 중...</span>
                    </div>
                </div>

                <!-- 환불 건수 -->
                <div class="stat-card">
                    <div class="stat-icon refund">
                        <!-- 환불 건수: 환불/반환 아이콘 -->
                        <svg width="24" height="24" viewBox="0 0 24 24" fill="none"
                             stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                            <polyline points="1 4 1 10 7 10"/>
                            <polyline points="23 20 23 14 17 14"/>
                            <path d="M20.49 9A9 9 0 0 0 5.64 5.64L1 10m22 4l-4.64 4.36A9 9 0 0 1 3.51 15"/>
                        </svg>
                    </div>
                    <div class="stat-info">
                        <h3>환불 건수</h3>
                        <p class="stat-number">0</p>
                        <span class="stat-trend up">데이터 로딩 중...</span>
                    </div>
                </div>
            </div>

            <div class="chart-table">
                <section class="chart-section">
                    <div class="chart-header"><h3>최근 7일 매출 추이</h3></div>
                    <div class="chart-body">
                        <canvas id="salesChart"></canvas>
                    </div>
                </section>

                <section class="table-section">
                    <div class="table-header"><h3>일자별 매출 내역</h3></div>
                    <div class="table-body">
                        <table>
                            <thead>
                            <tr>
                                <th>날짜</th>
                                <th>매출액</th>
                                <th>결제 건수</th>
                                <th>환불 건수</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td colspan="4" style="text-align: center;">데이터 로딩 중...</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </section>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script src="/js/pages/admin/dashboard/salesChart.js"></script>
</body>
</html>
