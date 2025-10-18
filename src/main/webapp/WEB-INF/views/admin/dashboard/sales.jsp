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
    <link rel="stylesheet" href="/css/pages/admin/dashboard/visitor.css"/>
    <script src="/js/common/currentTime.js"></script>
    <title>관리자 | 매출 현황</title>
</head>
<body>
<div class="main-container"> <!-- left-container -->
    <%@include file="../adminSidebar.jsp" %> <!-- right-container -->
    <div class="right-container">
        <header>
            <h1>대시보드</h1>
            <div class="header-info">
                <div class="info-box"><p>안녕하세요 <span>관리자</span>님</p></div>
                <a class="logout" href="/auth/logout"> 로그아웃 </a></div>
        </header>
        <div class="content-wrap">
            <div class="content-header">
                <h2>매출 현황</h2>
                <p class="date-range" id="current-time"></p>
            </div>

            <section class="summary">
                <div class="card"><h3>오늘 매출</h3><p class="value">60,000원</p></div>
                <div class="card"><h3>전체 매출</h3><p class="value">1,215,000원</p></div>
                <div class="card"><h3>오늘 결제 건수</h3><p class="value">12건</p></div>
                <div class="card"><h3>총 결제 건수</h3><p class="value">403건</p></div>
            </section>

            <div class="chart-table">
                <section class="chart-section">
                    <div class="chart-header"><h3>최근 7일 매출 추이</h3></div>
                    <div class="chart-body"><canvas id="salesChart"></canvas></div>
                </section>

                <section class="table-section">
                    <div class="table-header"><h3>일자별 매출 내역</h3></div>
                    <div class="table-body">
                        <table>
                            <thead>
                            <tr><th>날짜</th><th>매출액</th><th>결제 건수</th><th>환불 건수</th></tr>
                            </thead>
                            <tbody>
                            <tr><td>2025-10-10</td><td>50,000원</td><td>9건</td><td>0건</td></tr>
                            <tr><td>2025-10-11</td><td>80,000원</td><td>12건</td><td>1건</td></tr>
                            <tr><td>2025-10-12</td><td>65,000원</td><td>10건</td><td>0건</td></tr>
                            <tr><td>2025-10-13</td><td>90,000원</td><td>15건</td><td>1건</td></tr>
                            <tr><td>2025-10-14</td><td>110,000원</td><td>18건</td><td>0건</td></tr>
                            <tr><td>2025-10-15</td><td>120,000원</td><td>21건</td><td>2건</td></tr>
                            <tr><td>2025-10-16</td><td>60,000원</td><td>12건</td><td>0건</td></tr>
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