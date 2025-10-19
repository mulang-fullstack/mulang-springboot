<%@ page contentType="text/html;charset=utf-8" %>
<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" href="/img/favicon.svg" type="image/png">
    <link rel="stylesheet" href="/css/global.css"/>
    <link rel="stylesheet" href="/css/pages/admin/userManage/userLog.css"/>
    <title>관리자 | 사용자 로그</title>
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
                <a class="logout" href="#">로그아웃</a>
            </div>
        </header>

        <!-- -------------------- 메인 콘텐츠 -------------------- -->
        <div class="content-wrap">
            <div class="content-header">
                <h2>사용자 로그</h2>
                <p class="date-range" id="current-time">2025.10.19 17:33:42</p>
            </div>

            <!-- -------------------- 필터/검색 -------------------- -->
            <section class="log-section">
                <div class="filter-bar">
                    <div class="filter-container">
                        <div class="filter-group">
                            <span class="filter-label">로그 타입</span>
                            <div class="radio-group">
                                <label><input type="radio" name="logType" checked> 전체</label>
                                <label><input type="radio" name="logType"> 로그인</label>
                                <label><input type="radio" name="logType"> 로그아웃</label>
                            </div>
                        </div>

                        <div class="filter-group">
                            <span class="filter-label">기간</span>
                            <div class="date-filter">
                                <input type="date" value="2025-10-13">
                                <span class="date-separator">~</span>
                                <input type="date" value="2025-10-19">
                            </div>
                        </div>

                        <div class="filter-group">
                            <span class="filter-label">빠른 선택</span>
                            <div class="quick-select">
                                <button>오늘</button>
                                <button>1주일</button>
                                <button>1개월</button>
                                <button>3개월</button>
                            </div>
                        </div>
                    </div>

                    <div class="search-section">
                        <form class="search-form">
                            <input type="text" placeholder="사용자명, 이메일, IP 검색">
                            <button type="submit">검색</button>
                        </form>
                        <button class="btn-export">엑셀 다운로드</button>
                    </div>
                </div>

                <!-- -------------------- 로그 테이블 -------------------- -->
                <div class="table-wrap">
                    <table>
                        <thead>
                        <tr>
                            <th>번호</th>
                            <th>로그 타입</th>
                            <th>사용자명</th>
                            <th>이메일</th>
                            <th>IP 주소</th>
                            <th>브라우저</th>
                            <th>일시</th>
                            <th>세션 시간</th>
                            <th>상태</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>10</td>
                            <td><span class="log-badge login">로그인</span></td>
                            <td>김보카</td>
                            <td>boka@mulang.com</td>
                            <td>192.168.0.21</td>
                            <td>Chrome</td>
                            <td>2025-10-19 16:25:13</td>
                            <td>25분</td>
                            <td><span class="status-dot success"></span></td>
                        </tr>
                        <tr>
                            <td>9</td>
                            <td><span class="log-badge logout">로그아웃</span></td>
                            <td>이현주</td>
                            <td>hjlee@mulang.com</td>
                            <td>192.168.0.22</td>
                            <td>Edge</td>
                            <td>2025-10-19 15:50:42</td>
                            <td>1시간 2분</td>
                            <td><span class="status-dot success"></span></td>
                        </tr>
                        <tr>
                            <td>8</td>
                            <td><span class="log-badge login">로그인</span></td>
                            <td>박성민</td>
                            <td>smpark@mulang.com</td>
                            <td>192.168.0.32</td>
                            <td>Safari</td>
                            <td>2025-10-19 14:11:03</td>
                            <td>-</td>
                            <td><span class="status-dot fail"></span></td>
                        </tr>
                        </tbody>
                    </table>
                </div>

                <!-- -------------------- 페이징 -------------------- -->
                <div class="pagination">
                    <button disabled>«</button>
                    <button class="active">1</button>
                    <button>2</button>
                    <button>3</button>
                    <button>»</button>
                </div>
            </section>
        </div>
    </div>
</div>

<!-- JS -->
<script src="/js/common/currentTime.js"></script>
<script src="/js/pages/admin/userLog.js"></script>
</body>
</html>
