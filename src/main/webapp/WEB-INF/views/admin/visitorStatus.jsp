<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>방문자 현황 | 관리자 대시보드</title>
    <link rel="stylesheet" href="/css/pages/admin/visitorStatus.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>

    <%@include file="adminSidebar.jsp" %>
<main class="visitor-dashboard">

    <header class="header">
        <h1>방문자 현황</h1>
        <p class="date-range">2025.10.10 - 2025.10.16</p>
    </header>

    <!-- 요약 카드 -->
    <section class="summary">
        <div class="card">
            <h3>오늘 방문자 수</h3>
            <p class="value">1,245</p>
        </div>
        <div class="card">
            <h3>신규 가입자</h3>
            <p class="value">57</p>
        </div>
        <div class="card">
            <h3>총 누적 방문자</h3>
            <p class="value">82,134</p>
        </div>
        <div class="card">
            <h3>활성 사용자</h3>
            <p class="value">403</p>
        </div>
    </section>

    <!-- 차트 -->
    <section class="chart">
        <h2>최근 7일 방문 추이</h2>
        <canvas id="visitorChart"></canvas>
    </section>

    <!-- 테이블 -->
    <section class="table-section">
        <h2>일자별 방문자 통계</h2>
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
            <tr><td>2025-10-10</td><td>120</td><td>45</td><td>5</td></tr>
            <tr><td>2025-10-11</td><td>180</td><td>70</td><td>8</td></tr>
            <tr><td>2025-10-12</td><td>240</td><td>92</td><td>11</td></tr>
            <tr><td>2025-10-13</td><td>200</td><td>80</td><td>10</td></tr>
            <tr><td>2025-10-14</td><td>260</td><td>110</td><td>15</td></tr>
            <tr><td>2025-10-15</td><td>300</td><td>120</td><td>17</td></tr>
            <tr><td>2025-10-16</td><td>250</td><td>98</td><td>13</td></tr>
            </tbody>
        </table>
    </section>

</main>

<script>
    fetch('/api/admin/visitors')
        .then(res => res.json())
        .then(data => {
            const labels = data.map(d => d.date);
            const values = data.map(d => d.totalVisits);
            new Chart(document.getElementById('visitorChart'), {
                type: 'line',
                data: {
                    labels,
                    datasets: [{
                        label: '방문자 수',
                        data: values,
                        borderColor: '#0066FF',
                        borderWidth: 2,
                        tension: 0.25,
                        fill: false
                    }]
                },
                options: {
                    responsive: true,
                    scales: { y: { beginAtZero: true } }
                }
            });
        });
</script>

</body>
</html>
