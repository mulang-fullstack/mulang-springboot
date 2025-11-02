// ==================== 전역 변수 ====================
let isSearching = false; // 중복 요청 방지
const USE_API_MODE = true; // 항상 비동기 모드
const API_ENDPOINT = '/admin/system/notice/api'; // 실제 컨트롤러 엔드포인트

// ==================== API 요청 ====================
async function fetchNoticeList(params) {
    if (isSearching) return null;

    try {
        isSearching = true;
        const queryString = new URLSearchParams(params).toString();
        console.log('📤 API 요청:', queryString);

        const response = await fetch(`${API_ENDPOINT}?${queryString}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': document.querySelector('meta[name="csrf-token"]')?.content
            }
        });

        if (!response.ok) throw new Error('데이터를 불러오는데 실패했습니다.');

        const data = await response.json();
        console.log('📥 API 응답:', data);
        return data;
    } catch (error) {
        console.error('❌ Error fetching notice list:', error);
        alert('데이터를 불러오는데 실패했습니다.');
        return null;
    } finally {
        isSearching = false;
    }
}

// 공지사항 상세 조회
async function fetchNoticeDetail(noticeId) {
    try {
        console.log(`📤 공지 상세 조회: ${noticeId}`);
        const response = await fetch(`/support/notice/${noticeId}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': document.querySelector('meta[name="csrf-token"]')?.content
            }
        });

        if (!response.ok) throw new Error('공지사항을 불러오는데 실패했습니다.');

        const data = await response.json();
        console.log('📥 공지 상세:', data);
        return data;
    } catch (error) {
        console.error('❌ Error fetching notice detail:', error);
        alert('공지사항을 불러오는데 실패했습니다.');
        return null;
    }
}

// ==================== 파라미터 수집 ====================
function collectSearchParams() {
    const startDateInput = document.getElementById('startDate');
    const endDateInput = document.getElementById('endDate');
    const noticeTypeRadio = document.querySelector('input[name="noticeType"]:checked');
    const statusRadio = document.querySelector('input[name="status"]:checked');
    const keywordInput = document.getElementById('searchKeyword');
    const sortSelect = document.getElementById('sortSelect');

    const startDate = startDateInput?.value || '';
    const endDate = endDateInput?.value || '';
    const noticeType = noticeTypeRadio?.value || 'ALL';
    const status = statusRadio?.value || 'ALL';
    const keyword = keywordInput?.value.trim() || '';
    const sortValue = sortSelect?.value || 'LATEST';

    let sortBy = 'createdAt';
    let sortDirection = 'DESC';
    switch (sortValue) {
        case 'OLDEST': sortDirection = 'ASC'; break;
        case 'TITLE_ASC': sortBy = 'title'; sortDirection = 'ASC'; break;
        case 'TITLE_DESC': sortBy = 'title'; break;
    }

    const params = { page: 0, size: 10, sortBy, sortDirection };
    if (startDate) params.startDate = `${startDate}T00:00:00`;
    if (endDate) params.endDate = `${endDate}T23:59:59`;
    if (noticeType !== 'ALL') params.type = noticeType;
    if (status !== 'ALL') params.status = status;
    if (keyword) params.keyword = keyword;

    console.log('📋 최종 파라미터:', params);
    return params;
}

// ==================== 렌더링 ====================
function renderNoticeTable(notices, currentPage, pageSize) {
    const tbody = document.querySelector('table tbody');
    if (!tbody) return console.error('❌ tbody 없음');

    if (!notices || notices.length === 0) {
        tbody.innerHTML = '<tr><td colspan="7" class="no-data">등록된 공지사항이 없습니다.</td></tr>';
        return;
    }

    tbody.innerHTML = notices.map((notice, i) => {
        const rowNumber = currentPage * pageSize + i + 1;
        const typeLabel = {
            GENERAL: '일반',
            UPDATE: '업데이트',
            SYSTEM: '시스템'
        }[notice.type] || '일반';
        const statusText = notice.status === 'PUBLIC' ? '공개' : '비공개';
        const statusClass = notice.status === 'PUBLIC' ? 'active' : 'inactive';

        return `
            <tr data-id="${notice.id}">
                <td>${rowNumber}</td>
                <td><span class="type-badge ${notice.type?.toLowerCase() || 'general'}">${typeLabel}</span></td>
                <td class="title-cell">
                    <a href="/admin/content/notice/${notice.id}" class="notice-title">${notice.title}</a>
                </td>
                <td>${notice.userNickname || '관리자'}</td>
                <td>${notice.createdAt}</td>
                <td><span class="status-badge ${statusClass}">${statusText}</span></td>
                <td class="actions">
                    <button class="btn-edit" onclick="editNotice(${notice.id})">정보 수정</button>
                </td>
            </tr>
        `;
    }).join('');
}

function updatePagination(currentPage, totalPages) {
    window.paginationData = { currentPage, totalPages };
    if (typeof window.renderPagination === 'function') window.renderPagination();
}

// ==================== 수정 기능 ====================
async function editNotice(noticeId) {
    console.log(`✏️ 수정 모달 열기: ${noticeId}`);

    // 1. 상세 정보 가져오기
    const notice = await fetchNoticeDetail(noticeId);
    if (!notice) return;

    // 2. 수정 모달에 데이터 채우기
    document.getElementById('editNoticeId').value = notice.id;
    document.getElementById('editNoticeType').value = notice.type;
    document.getElementById('editNoticeTitle').value = notice.title;
    document.getElementById('editNoticeContent').value = notice.content;
    document.getElementById('editCharCount').textContent = notice.content?.length || 0;

    // 라디오 버튼 설정
    if (notice.status === 'PUBLIC') {
        document.getElementById('editStatusPublic').checked = true;
    } else {
        document.getElementById('editStatusPrivate').checked = true;
    }

    // 3. 모달 열기
    openEditModal();
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

window.changePage = async function (page) {
    const params = collectSearchParams();
    params.page = page;
    const data = await fetchNoticeList(params);
    if (data) {
        renderNoticeTable(data.notices, data.currentPage, data.size);
        updatePagination(data.currentPage, data.totalPages);
    }
};

function resetFilters() {
    console.log('🔄 필터 초기화 시작');

    // 1. 날짜 초기화
    const startDateInput = document.getElementById('startDate');
    const endDateInput = document.getElementById('endDate');
    if (startDateInput) startDateInput.value = '';
    if (endDateInput) endDateInput.value = '';

    // 2. 라디오 버튼 초기화 (공지 유형, 상태)
    const allTypeRadio = document.querySelector('input[name="noticeType"][value="ALL"]');
    const allStatusRadio = document.querySelector('input[name="status"][value="ALL"]');
    if (allTypeRadio) allTypeRadio.checked = true;
    if (allStatusRadio) allStatusRadio.checked = true;

    // 3. 검색어 초기화
    const keywordInput = document.getElementById('searchKeyword');
    if (keywordInput) keywordInput.value = '';

    // 4. 정렬 초기화
    const sortSelect = document.getElementById('sortSelect');
    if (sortSelect) sortSelect.value = 'LATEST';

    // 5. 내부 상태 로그 확인용
    console.log('✅ 필터 초기화 완료, 새 검색 실행');

    // 6. 즉시 검색 갱신
    performSearch();
}

// ==================== 초기화 및 이벤트 ====================
function initializeDateFilters() {
    const s = document.getElementById('startDate');
    const e = document.getElementById('endDate');
    if (s) s.value = '';
    if (e) e.value = '';
}

document.addEventListener('DOMContentLoaded', function () {
    console.log('🚀 공지사항 페이지 초기화');
    initializeDateFilters();

    document.querySelector('.search-btn')?.addEventListener('click', e => {
        e.preventDefault(); performSearch();
    });

    document.getElementById('searchKeyword')?.addEventListener('keypress', e => {
        if (e.key === 'Enter') { e.preventDefault(); performSearch(); }
    });

    document.getElementById('sortSelect')?.addEventListener('change', performSearch);

    ['startDate', 'endDate'].forEach(id => {
        document.getElementById(id)?.addEventListener('change', performSearch);
    });

    document.querySelectorAll('input[name="noticeType"]').forEach(r => r.addEventListener('change', performSearch));
    document.querySelectorAll('input[name="status"]').forEach(r => r.addEventListener('change', performSearch));

    // 페이지 로드 시 무조건 비동기 요청 1회 실행
    performSearch();
});