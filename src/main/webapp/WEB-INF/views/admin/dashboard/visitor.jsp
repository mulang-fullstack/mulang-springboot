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
    <title>관리자 | 방문자 현황</title>
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
                <h2>방문자 현황</h2>
                <p class="date-range" id="current-time"></p>
            </div>
            <div class="main-content">
                <!-- -------------------- 통계 카드 -------------------- -->
                <div class="stats-container">
                    <div class="stat-card">
                        <div class="stat-icon login">
                            <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor"
                                 stroke-width="2">
                                <path d="M15 3h4a2 2 0 0 1 2 2v14a2 2 0 0 1-2 2h-4"/>
                                <polyline points="10 17 15 12 10 7"/>
                                <line x1="15" y1="12" x2="3" y2="12"/>
                            </svg>
                        </div>
                        <div class="stat-info">
                            <h3>오늘 로그인</h3>
                            <p class="stat-number">132</p>
                            <span class="stat-trend up">↑ 12% 어제 대비</span>
                        </div>
                    </div>

                    <div class="stat-card">
                        <div class="stat-icon active">
                            <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor"
                                 stroke-width="2">
                                <circle cx="12" cy="12" r="10"/>
                                <circle cx="12" cy="12" r="3" fill="currentColor"/>
                            </svg>
                        </div>
                        <div class="stat-info">
                            <h3>현재 활성 사용자</h3>
                            <p class="stat-number">54</p>
                            <span class="stat-trend">실시간</span>
                        </div>
                    </div>

                    <div class="stat-card">
                        <div class="stat-icon logout">
                            <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor"
                                 stroke-width="2">
                                <path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"/>
                                <polyline points="16 17 21 12 16 7"/>
                                <line x1="21" y1="12" x2="9" y2="12"/>
                            </svg>
                        </div>
                        <div class="stat-info">
                            <h3>신규 사용자</h3>
                            <p class="stat-number">117</p>
                            <span class="stat-trend">정상 종료</span>
                        </div>
                    </div>

                    <div class="stat-card">
                        <div class="stat-icon total">
                            <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor"
                                 stroke-width="2">
                                <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/>
                                <circle cx="9" cy="7" r="4"/>
                                <path d="M23 21v-2a4 4 0 0 0-3-3.87"/>
                                <path d="M16 3.13a4 4 0 0 1 0 7.75"/>
                            </svg>
                        </div>
                        <div class="stat-info">
                            <h3>전체 사용자</h3>
                            <p class="stat-number">1,024</p>
                            <span class="stat-trend">등록된 회원</span>
                        </div>
                    </div>
                </div>
                <div class="chart-table">
                    <!-- 차트 -->
                    <section class="chart-section">
                        <div class="chart-header">
                            <h3>최근 7일 방문 추이</h3>
                        </div>
                        <div class="chart-body">
                            <canvas id="visitorChart"></canvas>
                        </div>
                    </section>
                    <!-- 테이블 -->
                    <section class="table-section">
                        <div class="table-header">
                            <h3>일자별 방문자 통계</h3>
                        </div>
                        <div class="table-body">
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
                        </div>
                    </section>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script src="/js/pages/admin/dashboard/visitorChart.js"></script>
</body>
</html>