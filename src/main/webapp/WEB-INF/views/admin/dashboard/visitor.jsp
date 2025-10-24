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
    <link rel="stylesheet" href="/css/pages/admin/dashboard/visitor.css"/>
    <title>관리자 | 방문자 현황</title>
</head>
<body>
<div class="main-container"> <!-- left-container -->
    <%@include file="../adminSidebar.jsp" %> <!-- right-container -->
    <div class="right-container">
        <header>
            <h1>대시보드 - 방문자 현황</h1>
            <div class="header-info">
                <div class="info-box"><p>안녕하세요 <span>관리자</span>님</p></div>
                <a class="logout" href="/auth/logout"> 로그아웃 </a></div>
        </header>
        <div class="content-wrap">
            <div class="content-header">
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
                            <p class="stat-number">${todayLogins}</p>
                            <span class="stat-trend ${loginChangeRate >= 0 ? 'up' : 'down'}">
                                ${loginChangeRate >= 0 ? '↑' : '↓'} ${loginChangeRate}% 어제 대비
                            </span>
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
                            <p class="stat-number">${activeSessions}</p>
                            <span class="stat-trend">실시간</span>
                        </div>
                    </div>

                    <div class="stat-card">
                        <div class="stat-icon new-user">
                            <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor"
                                 stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                <circle cx="9" cy="7" r="4"/>
                                <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/>
                                <line x1="19" y1="8" x2="19" y2="14"/>
                                <line x1="16" y1="11" x2="22" y2="11"/>
                            </svg>
                        </div>
                        <div class="stat-info">
                            <h3>신규 사용자</h3>
                            <p class="stat-number">${todayNewUsers}</p>
                            <span class="stat-trend">${signupChangeRate >= 0 ? '↑' : '↓'} ${signupChangeRate}% 어제 대비</span>

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
                            <p class="stat-number">${totalUsers}</p>
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
                                <c:forEach var="login" items="${weeklyLogins}" varStatus="i">
                                    <tr>
                                        <td>${login.date}</td>
                                        <td>
                                            <c:set var="signup" value="${weeklyNewUsers[i.index]}"/>
                                                ${login.count + signup.count}
                                        </td>
                                        <td>${login.count}</td>
                                        <td>${weeklyNewUsers[i.index].count}</td>
                                    </tr>
                                </c:forEach>
                                <c:if test="${empty weeklyLogins}">
                                    <tr>
                                        <td colspan="4" class="no-data">최근 7일간 데이터가 없습니다.</td>
                                    </tr>
                                </c:if>
                                </tbody>
                            </table>
                        </div>
                    </section>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="/js/common/currentTime.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script src="/js/pages/admin/dashboard/visitorChart.js"></script>
</body>
</html>