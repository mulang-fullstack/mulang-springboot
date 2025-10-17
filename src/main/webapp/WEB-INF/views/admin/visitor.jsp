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
    <link rel="stylesheet" href="/css/pages/admin/visitor.css"/>
    <title>Mulang?</title>
</head>
<body>
<div class="main-container">
    <!-- left-container -->
    <%@include file="adminSidebar.jsp" %>

    <!-- right-container -->
    <div class="right-container">
        <header>
            <h2>대시보드</h2>
        </header>
        <main>
            <div class="content-header">
                <h2>방문자 현황</h2>
                <p class="date-range">2025.10.10 - 2025.10.16</p>
            </div>
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
    </div>

</div>
</body>
</html>