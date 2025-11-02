<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<div id="noticeModal" class="modal-overlay hidden">
    <div class="modal-content">
        <!-- 모달 헤더 -->
        <div class="modal-header">
            <h2>공지사항 작성</h2>
            <button type="button" class="modal-close" onclick="closeNoticeModal()">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"
                     fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                    <line x1="18" y1="6" x2="6" y2="18"></line>
                    <line x1="6" y1="6" x2="18" y2="18"></line>
                </svg>
            </button>
        </div>

        <!-- 모달 바디 -->
        <form id="noticeForm">
            <div class="modal-body">
                <!-- 공지 유형 -->
                <div class="form-group">
                    <label for="noticeType">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24"
                             fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                            <path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z"></path>
                        </svg>
                        공지 유형
                    </label>
                    <select id="noticeType" name="type" required>
                        <option value="">유형을 선택하세요</option>
                        <option value="GENERAL">일반</option>
                        <option value="UPDATE">업데이트</option>
                        <option value="SYSTEM">시스템</option>
                    </select>
                </div>

                <!-- 제목 -->
                <div class="form-group">
                    <label for="noticeTitle">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24"
                             fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                            <line x1="17" y1="10" x2="3" y2="10"></line>
                            <line x1="21" y1="6" x2="3" y2="6"></line>
                            <line x1="21" y1="14" x2="3" y2="14"></line>
                            <line x1="17" y1="18" x2="3" y2="18"></line>
                        </svg>
                        제목
                    </label>
                    <input type="text" id="noticeTitle" name="title" placeholder="공지사항 제목을 입력하세요" required>
                </div>

                <!-- 내용 -->
                <div class="form-group">
                    <label for="noticeContent">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24"
                             fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                            <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path>
                            <polyline points="14 2 14 8 20 8"></polyline>
                            <line x1="16" y1="13" x2="8" y2="13"></line>
                            <line x1="16" y1="17" x2="8" y2="17"></line>
                            <polyline points="10 9 9 9 8 9"></polyline>
                        </svg>
                        내용
                    </label>
                    <textarea id="noticeContent" name="content" placeholder="공지사항 내용을 입력하세요" rows="8" required></textarea>
                    <span class="char-count"><span id="charCount">0</span> / 1000</span>
                </div>

                <!-- 공개 여부 -->
                <div class="form-group">
                    <label>
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24"
                             fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                            <path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"></path>
                            <circle cx="12" cy="12" r="3"></circle>
                        </svg>
                        공개 여부
                    </label>
                    <div class="radio-toggle">
                        <label class="radio-option">
                            <input type="radio" name="status" value="PUBLIC" checked>
                            <span class="radio-label">
                                <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 24 24"
                                     fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                    <polyline points="20 6 9 17 4 12"></polyline>
                                </svg>
                                공개
                            </span>
                        </label>
                        <label class="radio-option">
                            <input type="radio" name="status" value="PRIVATE">
                            <span class="radio-label">
                                <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 24 24"
                                     fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                    <line x1="18" y1="6" x2="6" y2="18"></line>
                                    <line x1="6" y1="6" x2="18" y2="18"></line>
                                </svg>
                                비공개
                            </span>
                        </label>
                    </div>
                </div>
            </div>

            <!-- 모달 푸터 -->
            <div class="modal-footer">
                <button type="button" class="btn-modal-cancel" onclick="closeNoticeModal()">
                    취소
                </button>
                <button type="submit" class="btn-modal-submit">
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24"
                         fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                        <polyline points="20 6 9 17 4 12"></polyline>
                    </svg>
                    등록하기
                </button>
            </div>
        </form>
    </div>
</div>
<script src="/js/pages/admin/system/noticeModal.js"></script>
