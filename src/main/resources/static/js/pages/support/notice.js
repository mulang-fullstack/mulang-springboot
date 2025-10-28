// ==================== API 요청 ====================

// 비동기 요청 보내기
async function fetchNoticeList(params) {
    try {
        const queryString = new URLSearchParams(params).toString();
        const response = await fetch(`/support/notice/api?${queryString}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': document.querySelector('meta[name="csrf-token"]')?.content
            }
        });

        if (!response.ok) {
            throw new Error('데이터를 불러오는데 실패했습니다.');
        }

        const data = await response.json();
        return data;
    } catch (error) {
        console.error('Error fetching notice list:', error);
        alert('데이터를 불러오는데 실패했습니다.');
        return null;
    }
}

// ==================== 파라미터 수집 ====================

// 검색 파라미터 수집
function collectSearchParams() {
    const keyword = document.getElementById('searchKeyword')?.value.trim() || '';

    const params = {
        page: 0,
        size: 10,
        sortBy: 'createdAt',
        sortDirection: 'DESC'
    };

    // keyword 처리
    if (keyword) {
        params.keyword = keyword;
    }

    return params;
}

// ==================== 렌더링 ====================

// 공지사항 목록 렌더링
function renderNoticeList(notices, currentPage, pageSize) {
    const container = document.getElementById('noticeList');

    if (!notices || notices.length === 0) {
        container.innerHTML = `
            <div class="no-data">
                <p>등록된 공지사항이 없습니다.</p>
            </div>
        `;
        return;
    }

    container.innerHTML = notices.map((notice) => {
        // 중요 공지 여부에 따른 뱃지
        const badgeClass = notice.important ? 'important' : 'general';
        const badgeText = notice.important ? '중요' : '일반';

        // 날짜 포맷팅
        const formattedDate = formatDateTime(notice.createdAt);

        // 내용 미리보기 (HTML 태그 제거)
        const contentPreview = stripHtml(notice.content || '').substring(0, 100);

        return `
            <div class="notice-item" onclick="goToNoticeDetail(${notice.id})">
                <div class="notice-item-header">
                    <span class="notice-badge ${badgeClass}">${badgeText}</span>
                </div>
                <h3 class="notice-title">${notice.title}</h3>
                <p class="notice-content">${contentPreview}${contentPreview.length >= 100 ? '...' : ''}</p>
                <div class="notice-meta">
                    <span>
                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            <circle cx="12" cy="12" r="10"></circle>
                            <polyline points="12 6 12 12 16 14"></polyline>
                        </svg>
                        ${formattedDate}
                    </span>
                    <span>
                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            <path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"></path>
                            <circle cx="12" cy="12" r="3"></circle>
                        </svg>
                        조회 ${notice.viewCount || 0}
                    </span>
                </div>
            </div>
        `;
    }).join('');
}

// 페이지네이션 업데이트
function updatePagination(currentPage, totalPages) {
    window.paginationData = {
        currentPage: currentPage,
        totalPages: totalPages,
        baseUrl: '/support/notice',
        asyncMode: true
    };

    if (typeof renderPagination === 'function') {
        renderPagination();
    }
}

// ==================== 검색 및 필터 ====================

// 검색 실행
async function performSearch() {
    const params = collectSearchParams();
    const data = await fetchNoticeList(params);

    if (data) {
        renderNoticeList(data.notices, data.currentPage, data.size);
        updatePagination(data.currentPage, data.totalPages);
    }
}

// 페이지 변경
window.changePage = async function(page) {
    const params = collectSearchParams();
    params.page = page;

    const data = await fetchNoticeList(params);

    if (data) {
        renderNoticeList(data.notices, data.currentPage, data.size);
        updatePagination(data.currentPage, data.totalPages);

        // 페이지 변경 시 스크롤 상단으로
        window.scrollTo({ top: 0, behavior: 'smooth' });
    }
};

// ==================== 유틸리티 ====================

// 날짜 포맷팅
function formatDateTime(dateTimeString) {
    if (!dateTimeString) return '-';

    const date = new Date(dateTimeString);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');

    return `${year}.${month}.${day}`;
}

// HTML 태그 제거
function stripHtml(html) {
    const tmp = document.createElement('div');
    tmp.innerHTML = html;
    return tmp.textContent || tmp.innerText || '';
}

// 공지사항 상세 페이지로 이동
function goToNoticeDetail(noticeId) {
    window.location.href = `/support/notice/${noticeId}`;
}

// ==================== 이벤트 리스너 ====================

document.addEventListener('DOMContentLoaded', function() {
    // 페이지 로드 시 초기 데이터 요청
    performSearch();

    // 검색 버튼 클릭
    const searchBtn = document.querySelector('.search-btn');
    if (searchBtn) {
        searchBtn.addEventListener('click', performSearch);
    }

    // 검색어 입력 시 엔터키
    const searchKeyword = document.getElementById('searchKeyword');
    if (searchKeyword) {
        searchKeyword.addEventListener('keypress', function(e) {
            if (e.key === 'Enter') {
                e.preventDefault();
                performSearch();
            }
        });
    }
});