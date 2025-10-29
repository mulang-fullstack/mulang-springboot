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
    <link rel="stylesheet" href="/css/pages/admin/content/course.css"/>
    <title>관리자 | 강좌 심사</title>
</head>
<body>
<div class="main-container">
    <%@ include file="../adminSidebar.jsp" %>
    <div class="right-container">
        <header>
            <h1>콘텐츠 관리 - 강좌 심사</h1>
            <div class="header-info">
                <div class="info-box"><p>안녕하세요 <span>관리자</span>님</p></div>
                <a class="logout" href="#">로그아웃</a>
            </div>
        </header>

        <div class="content-wrap">
            <div class="content-header">
                <p class="date-range" id="current-time">2025.10.19 18:03:24</p>
            </div>

            <section class="course-section">
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
                        <!-- 언어 -->
                        <div class="filter-group">
                            <span class="filter-label">언어</span>
                            <div class="radio-group">
                                <label><input type="radio" name="language" value="ALL" checked> 전체</label>
                                <label><input type="radio" name="language" value="1"> 영어</label>
                                <label><input type="radio" name="language" value="2"> 중국어</label>
                                <label><input type="radio" name="language" value="3"> 일본어</label>
                            </div>
                        </div>

                        <!-- 신청 상태 -->
                        <div class="filter-group">
                            <span class="filter-label">신청 상태</span>
                            <div class="radio-group">
                                <label><input type="radio" name="status" value="PENDING" checked> 심사대기</label>
                                <label><input type="radio" name="status" value="REJECTED"> 심사거절</label>
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
                            <input type="text" id="searchKeyword" placeholder="강사 또는 강좌명 검색">
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
                                <option value="COURSE_NAME_ASC">강좌명순</option>
                                <option value="COURSE_NAME_DESC">강좌명순(역순)</option>
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

                <!-- 강의 신청 테이블 -->
                <div class="table-wrap">
                    <table>
                        <thead>
                        <tr>
                            <th>번호</th>
                            <th>강좌명</th>
                            <th>언어</th>
                            <th>강사</th>
                            <th>닉네임</th>
                            <th>신청일</th>
                            <th>상태</th>
                            <th>관리</th>
                        </tr>
                        </thead>
                        <tbody>
                        <!-- JavaScript로 동적 생성 -->
                        <tr>
                            <td colspan="8" class="no-data">데이터를 불러오는 중...</td>
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
<!-- 강좌 승인 확인 모달 -->
<div id="approveModal" class="modal" style="display: none;">
    <div class="modal-overlay" onclick="closeApproveModal()"></div>
    <div class="modal-content modal-approve">
        <div class="modal-header">
            <h2>강좌 승인 확인</h2>
            <button class="modal-close" onclick="closeApproveModal()">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <line x1="18" y1="6" x2="6" y2="18"></line>
                    <line x1="6" y1="6" x2="18" y2="18"></line>
                </svg>
            </button>
        </div>

        <div class="modal-body">
            <input type="hidden" id="approveCourseId">

            <div class="approve-icon">
                <svg xmlns="http://www.w3.org/2000/svg" width="64" height="64" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"></path>
                    <polyline points="22 4 12 14.01 9 11.01"></polyline>
                </svg>
            </div>

            <div class="approve-info">
                <p class="approve-course-title">
                    <strong id="approveCourseTitle"></strong>
                </p>
                <p class="approve-message">
                    이 강좌를 승인하시겠습니까?<br>
                    승인 후 강좌가 공개되며 사용자들이 수강할 수 있습니다.
                </p>
            </div>
        </div>

        <div class="modal-footer">
            <button type="button" class="btn-cancel-modal" onclick="closeApproveModal()">취소</button>
            <button type="button" class="btn-approve-confirm" onclick="executeApprove()">승인하기</button>
        </div>
    </div>
</div>

<!-- 강좌 거절 사유 모달 -->
<div id="rejectModal" class="modal" style="display: none;">
    <div class="modal-overlay" onclick="closeRejectModal()"></div>
    <div class="modal-content">
        <div class="modal-header">
            <h2>강좌 심사 거절</h2>
            <button class="modal-close" onclick="closeRejectModal()">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <line x1="18" y1="6" x2="6" y2="18"></line>
                    <line x1="6" y1="6" x2="18" y2="18"></line>
                </svg>
            </button>
        </div>

        <div class="modal-body">
            <input type="hidden" id="rejectCourseId">

            <div class="reject-info">
                <p class="reject-course-title">
                    <strong>강좌명:</strong> <span id="rejectCourseTitle"></span>
                </p>
                <p class="reject-warning">
                    이 강좌를 거절하시겠습니까? 거절 사유는 강사에게 전달됩니다.
                </p>
            </div>

            <div class="form-group">
                <label for="rejectionReason">거절 사유 <span class="required">*</span></label>
                <textarea
                        id="rejectionReason"
                        class="form-textarea"
                        rows="5"
                        placeholder="거절 사유를 최소 10자 이상 입력해주세요."
                        required
                ></textarea>
                <p class="input-hint">강사가 수정할 수 있도록 구체적인 사유를 입력해주세요.</p>
            </div>
        </div>

        <div class="modal-footer">
            <button type="button" class="btn-cancel-modal" onclick="closeRejectModal()">취소</button>
            <button type="button" class="btn-reject-confirm" onclick="executeReject()">거절하기</button>
        </div>
    </div>
</div>

<script src="/js/common/utils.js"></script>
<script src="/js/pages/admin/pagination.js"></script>
<script src="/js/pages/admin/content/pendingCourse.js"></script>
<script src="/js/pages/admin/content/pendingCourse-action.js"></script>
</body>
</html>