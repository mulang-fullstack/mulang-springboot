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
    <title>Mulang?</title>
</head>
<body data-active-menu="${activeMenu}" data-active-submenu="${activeSubmenu}">
<div class="main-container"> <!-- left-container -->
    <%@include file="../adminSidebar.jsp" %> <!-- right-container -->
    <div class="right-container">
        <header><h1>대시보드</h1>
            <div class="header-info">
                <div class="info-box"><p>안녕하세요 <span>관리자</span>님</p></div>
                <a class="logout" href="/auth/logout"> 로그아웃 </a></div>
        </header>
        <div class="content-wrap">
            <div class="content-header"><h2>매출 현황</h2>
                <p class="date-range">2025.10.10 - 2025.10.16</p></div>
            <div class="main-content"> <!-- 요약 카드 -->
                <section class="summary">
                    <div class="card"><h3>오늘 방문자 수</h3>
                        <p class="value">1,245</p></div>
                    <div class="card"><h3>신규 가입자</h3>
                        <p class="value">57</p></div>
                    <div class="card"><h3>총 누적 방문자</h3>
                        <p class="value">82,134</p></div>
                    <div class="card"><h3>활성 사용자</h3>
                        <p class="value">403</p></div>
                </section>
                <div class="chart-table"> <!-- 차트 -->
                    <section class="chart"><h2>최근 7일 방문 추이</h2>
                        <canvas id="visitorChart"></canvas>
                    </section> <!-- 테이블 -->
                    <section class="table-section"><h2>일자별 방문자 통계</h2>
                        <table>
                            <thead>
                            <tr>
                                <th>날짜</th>
                                <th>방문자 수</th>
                                <th>로그인 사용자</th>
                                <th>신규 가입자</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>2025-10-10</td>
                                <td>120</td>
                                <td>45</td>
                                <td>5</td>
                            </tr>
                            <tr>
                                <td>2025-10-11</td>
                                <td>180</td>
                                <td>70</td>
                                <td>8</td>
                            </tr>
                            <tr>
                                <td>2025-10-12</td>
                                <td>240</td>
                                <td>92</td>
                                <td>11</td>
                            </tr>
                            <tr>
                                <td>2025-10-13</td>
                                <td>200</td>
                                <td>80</td>
                                <td>10</td>
                            </tr>
                            <tr>
                                <td>2025-10-14</td>
                                <td>260</td>
                                <td>110</td>
                                <td>15</td>
                            </tr>
                            <tr>
                                <td>2025-10-15</td>
                                <td>300</td>
                                <td>120</td>
                                <td>17</td>
                            </tr>
                            <tr>
                                <td>2025-10-16</td>
                                <td>250</td>
                                <td>98</td>
                                <td>13</td>
                            </tr>
                            </tbody>
                        </table>
                    </section>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script> document.addEventListener('DOMContentLoaded', () => {
    const ctx = document.getElementById('visitorChart').getContext('2d');
    new Chart(ctx, {
        type: 'line',
        data: {
            labels: ['10/10', '10/11', '10/12', '10/13', '10/14', '10/15', '10/16'],
            datasets: [{
                label: '방문자 수',
                data: [120, 180, 240, 200, 260, 300, 250],
                borderColor: '#4B7BEC',
                backgroundColor: 'rgba(75,123,236,0.1)',
                borderWidth: 2,
                pointRadius: 4,
                pointBackgroundColor: '#4B7BEC',
                tension: 0.3
            }]
        },
        options: {
            maintainAspectRatio: false,
            responsive: true,
            scales: {
                x: {grid: {display: false}, ticks: {color: '#666', font: {size: 12}}},
                y: {beginAtZero: true, grid: {color: '#eef1f4'}, ticks: {color: '#666', stepSize: 50}}
            },
            plugins: {
                legend: {display: false},
                tooltip: {backgroundColor: '#333', titleFont: {size: 13, weight: '600'}, bodyFont: {size: 12}}
            }
        }
    });
}); </script>
</body>
</html>