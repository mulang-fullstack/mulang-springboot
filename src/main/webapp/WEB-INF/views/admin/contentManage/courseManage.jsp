<%@ page contentType="text/html;charset=utf-8" %>
<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" href="/img/favicon.svg" type="image/png">
    <link rel="stylesheet" href="/css/global.css"/>
    <link rel="stylesheet" href="/css/pages/admin/contentManage/courseManage.css"/>
    <title>관리자 | 강좌 관리</title>
</head>
<body>
<div class="main-container">
    <%@ include file="../adminSidebar.jsp" %>
    <div class="right-container">
        <!-- -------------------- 헤더 -------------------- -->
        <header>
            <h1>콘텐츠 관리</h1>
            <div class="header-info">
                <div class="info-box"><p>안녕하세요 <span>관리자</span>님</p></div>
                <a class="logout" href="#">로그아웃</a>
            </div>
        </header>

        <!-- -------------------- 메인 콘텐츠 -------------------- -->
        <div class="content-wrap">
            <div class="content-header">
                <h2>강좌 관리</h2>
                <p class="date-range" id="current-time">2025.10.19 18:03:24</p>
            </div>

            <!-- -------------------- 필터 / 검색 -------------------- -->
            <section class="course-section">
                <div class="filter-bar">
                    <div class="filter-container">
                        <div class="filter-group">
                            <span class="filter-label">언어</span>
                            <div class="select-group">
                                <select>
                                    <option selected>전체</option>
                                    <option>영어</option>
                                    <option>일본어</option>
                                    <option>중국어</option>
                                    <option>스페인어</option>
                                </select>
                            </div>
                        </div>

                        <div class="filter-group">
                            <span class="filter-label">강좌 상태</span>
                            <div class="radio-group">
                                <label><input type="radio" name="status" checked> 전체</label>
                                <label><input type="radio" name="status"> 공개</label>
                                <label><input type="radio" name="status"> 비공개</label>
                                <label><input type="radio" name="status"> 심사중</label>
                            </div>
                        </div>

                        <div class="filter-group">
                            <span class="filter-label">정렬</span>
                            <div class="select-group">
                                <select>
                                    <option selected>최신순</option>
                                    <option>오래된순</option>
                                    <option>제목순 (가나다)</option>
                                    <option>조회수순</option>
                                </select>
                            </div>
                        </div>
                    </div>

                    <!-- 검색 영역 -->
                    <div class="search-section">
                        <form action="/admin/user/memberList" method="get" class="search-form">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                            <input type="text" name="keyword" value="${param.keyword}" placeholder="이름 또는 이메일 검색">
                            <button type="submit">검색</button>
                        </form>
                        <button class="filter-reset" onclick="resetFilters()">필터 초기화</button>
                    </div>
                </div>

                <!-- -------------------- 강좌 테이블 -------------------- -->
                <div class="table-wrap">
                    <table>
                        <thead>
                        <tr>
                            <th>번호</th>
                            <th>썸네일</th>
                            <th>강좌명</th>
                            <th>언어</th>
                            <th>강사</th>
                            <th>등록일</th>
                            <th>상태</th>
                            <th>관리</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>5</td>
                            <td><img src="/img/sample/course1.webp" alt="썸네일" class="thumb"></td>
                            <td>영어회화 기초 마스터</td>
                            <td>영어</td>
                            <td>김보카</td>
                            <td>2025-10-14</td>
                            <td><span class="status-badge active">공개</span></td>
                            <td>
                                <button class="btn-edit">수정</button>
                                <button class="btn-delete">삭제</button>
                            </td>
                        </tr>
                        <tr>
                            <td>4</td>
                            <td><img src="/img/sample/course2.webp" alt="썸네일" class="thumb"></td>
                            <td>JLPT N2 실전 대비</td>
                            <td>일본어</td>
                            <td>이현주</td>
                            <td>2025-10-11</td>
                            <td><span class="status-badge review">심사중</span></td>
                            <td>
                                <button class="btn-edit">수정</button>
                                <button class="btn-delete">삭제</button>
                            </td>
                        </tr>
                        <tr>
                            <td>3</td>
                            <td><img src="/img/sample/course3.webp" alt="썸네일" class="thumb"></td>
                            <td>HSK 5급 듣기 특강</td>
                            <td>중국어</td>
                            <td>박성민</td>
                            <td>2025-09-28</td>
                            <td><span class="status-badge hidden">비공개</span></td>
                            <td>
                                <button class="btn-edit">수정</button>
                                <button class="btn-delete">삭제</button>
                            </td>
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
<script src="/js/pages/admin/contentManage/courseManage.js"></script>
</body>
</html>
