// ==================== 전역 변수 ====================
let isSearching = false; // 중복 요청 방지

// ==================== API 요청 ====================
async function fetchNoticeList(params) {
    if (isSearching) return null;

    try {
        isSearching = true;
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
        alert('데이터를 불러오는데 실패했습니다.');
        return null;
    } finally {
        isSearching = false;
    }
}

// ==================== 파라미터 수집 ====================
function collectSearchParams() {
    const keywordInput = document.getElementById('searchKeyword');

    const keyword = keywordInput?.value.trim() || '';

    const params = {
        page: 0,
        size: 10,
        sortBy: 'createdAt',
        sortDirection: 'DESC'
    };

    // 검색어 - 값이 있을 때만 추가
    if (keyword) {
        params.keyword = keyword;
    }

    console.log('📋 최종 파라미터:', params);
    return params;
}

// ==================== 렌더링 ====================
function renderNoticeTable(notices, currentPage, pageSize) {
    const tbody = document.getElementById('noticeList');

    if (!tbody) {
        return;
    }

    if (!notices || notices.length === 0) {
        tbody.innerHTML = '<tr><td colspan="4" class="no-data">등록된 공지사항이 없습니다.</td></tr>';
        return;
    }

    tbody.innerHTML = notices.map((notice, index) => {
        const rowNumber = currentPage * pageSize + index + 1;

        // 중요 공지 여부
        const isImportant = notice.important === true;
        const importantClass = isImportant ? 'important-notice' : '';

        // 공지 유형 텍스트 & 클래스 매핑
        let badgeClass = 'general';
        let badgeText = '';

        switch (notice.type) {
            case 'GENERAL':
                badgeText = '일반';
                break;
            case 'EVENT':
                badgeText = '이벤트';
                break;
            case 'SYSTEM':
                badgeText = '시스템';
                break;
            case 'UPDATE':
                badgeText = '업데이트';
                break;
            default:
                badgeClass = 'general';
                badgeText = '기타';
                break;
        }

        // 날짜 포맷팅
        const formattedDate = formatDateTime(notice.createdAt);

        // 내용 미리보기
        const contentPreview = stripHtml(notice.content || '').substring(0, 100);

        return `
            <tr class="${importantClass}" onclick="goToNoticeDetail(${notice.id})">
                <td class="notice-number">${rowNumber}</td>
                <td style="text-align: center;">
                    <span class="notice-badge ${badgeClass}">${badgeText}</span>
                </td>
                <td class="notice-title-cell">
                    <div class="notice-title-wrapper">
                        <div class="notice-title">${notice.title}</div>
                        ${contentPreview ? `<div class="notice-preview">${contentPreview}${contentPreview.length >= 100 ? '...' : ''}</div>` : ''}
                    </div>
                </td>
                <td class="notice-date">${formattedDate}</td>
            </tr>
        `;
    }).join('');
}

function updatePagination(currentPage, totalPages) {
    window.paginationData = {
        currentPage: currentPage,
        totalPages: totalPages
    };

    if (typeof window.renderPagination === 'function') {
        window.renderPagination();
    }
}

// ==================== 검색 및 필터 ====================
async function performSearch() {
    const params = collectSearchParams();
    const data = await fetchNoticeList(params);

    if (data) {
        renderNoticeTable(data.notices, data.currentPage, data.size);
        updatePagination(data.currentPage, data.totalPages);
    }
}

// 페이지 변경
window.changePage = async function (page) {
    const params = collectSearchParams();
    params.page = page;

    const data = await fetchNoticeList(params);

    if (data) {
        renderNoticeTable(data.notices, data.currentPage, data.size);
        updatePagination(data.currentPage, data.totalPages);

        // 페이지 변경 시 스크롤 상단으로
        window.scrollTo({ top: 0, behavior: 'smooth' });
    }
};

// ==================== 유틸리티 ====================
function formatDateTime(dateTimeString) {
    if (!dateTimeString) return '-';

    const date = new Date(dateTimeString);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');

    return `${year}.${month}.${day}`;
}

function stripHtml(html) {
    const tmp = document.createElement('div');
    tmp.innerHTML = html;
    return tmp.textContent || tmp.innerText || '';
}

function goToNoticeDetail(noticeId) {
    window.location.href = `/support/notice/${noticeId}`;
}

// ==================== 이벤트 리스너 ====================
document.addEventListener('DOMContentLoaded', function () {
    console.log('🚀 페이지 초기화');

    // 검색 버튼
    const searchBtn = document.querySelector('.search-btn');
    if (searchBtn) {
        searchBtn.addEventListener('click', function(e) {
            e.preventDefault();
            performSearch();
        });
    }

    // 검색어 엔터키
    const searchKeyword = document.getElementById('searchKeyword');
    if (searchKeyword) {
        searchKeyword.addEventListener('keypress', function (e) {
            if (e.key === 'Enter') {
                e.preventDefault();
                performSearch();
            }
        });
    }

    // 초기 데이터 로드
    performSearch();
});