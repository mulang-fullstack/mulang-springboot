// ==================== 필터 및 검색 관련 ====================

// 검색 파라미터 수집
function collectSearchParams() {
    const startDate = document.querySelector('.date-filter input[type="date"]:first-child').value;
    const endDate = document.querySelector('.date-filter input[type="date"]:last-child').value;
    const type = document.querySelector('input[name="type"]:checked')?.value;
    const status = document.querySelector('input[name="status"]:checked')?.value;
    const keyword = document.getElementById('searchKeyword').value.trim();
    const sortValue = document.getElementById('sortSelect').value;

    let sortBy = 'createdAt';
    let sortDirection = 'DESC';

    switch (sortValue) {
        case 'LATEST':
            sortBy = 'createdAt';
            sortDirection = 'DESC';
            break;
        case 'OLDEST':
            sortBy = 'createdAt';
            sortDirection = 'ASC';
            break;
        case 'TITLE_ASC':
            sortBy = 'title';
            sortDirection = 'ASC';
            break;
        case 'TITLE_DESC':
            sortBy = 'title';
            sortDirection = 'DESC';
            break;
    }

    const params = { page: 0, size: 10 };

    if (startDate) params.startDate = startDate + 'T00:00:00';
    if (endDate) params.endDate = endDate + 'T23:59:59';
    if (type && type !== 'ALL') params.type = type;
    if (status && status !== 'ALL') params.status = status;
    if (keyword) params.keyword = keyword;

    params.sortBy = sortBy;
    params.sortDirection = sortDirection;

    return params;
}

// 비동기 요청
async function fetchNoticeList(params) {
    try {
        const query = new URLSearchParams(params).toString();
        const res = await fetch(`/admin/system/api/notice?${query}`, {
            method: 'GET',
            headers: { 'Content-Type': 'application/json' },
            credentials: 'include'
        });

        if (!res.ok) throw new Error('공지 데이터를 불러오지 못했습니다.');
        return await res.json();
    } catch (err) {
        console.error(err);
        showMessage('데이터 로드 실패', 'error');
        return null;
    }
}

// 테이블 렌더링
function renderNoticeTable(notices, currentPage, pageSize) {
    const tbody = document.querySelector('#noticeTable tbody');
    if (!notices || notices.length === 0) {
        tbody.innerHTML = '<tr><td colspan="8" class="no-data">등록된 공지사항이 없습니다.</td></tr>';
        return;
    }

    tbody.innerHTML = notices.map((notice, i) => {
        const rowNum = currentPage * pageSize + i + 1;
        const statusClass = notice.status === 'PUBLIC' ? 'public' : 'private';
        const statusText = notice.status === 'PUBLIC' ? '공개' : '비공개';

        let typeLabel = '';
        switch (notice.type) {
            case 'EVENT': typeLabel = '이벤트'; break;
            case 'SYSTEM': typeLabel = '시스템'; break;
            case 'UPDATE': typeLabel = '업데이트'; break;
            default: typeLabel = '일반';
        }

        return `
            <tr data-id="${notice.id}">
                <td>${rowNum}</td>
                <td>${typeLabel}</td>
                <td>${notice.title}</td>
                <td>${notice.userNickname || '-'}</td>
                <td>${notice.createdAt}</td>
                <td>
                    <span class="status-badge ${statusClass}">${statusText}</span>
                </td>
                <td class="actions">
                    <button class="btn-edit" onclick="openEditModal(${notice.id})">수정</button>
                    <button class="btn-delete" onclick="confirmDelete(${notice.id}, '${notice.title}')">삭제</button>
                </td>
            </tr>
        `;
    }).join('');
}

// 페이지네이션
function updatePagination(currentPage, totalPages) {
    window.paginationData = {
        currentPage,
        totalPages,
        baseUrl: '/admin/system/notice/manage',
        asyncMode: true
    };
    if (typeof renderPagination === 'function') renderPagination();
}

// 검색 실행
async function performSearch() {
    const params = collectSearchParams();
    const data = await fetchNoticeList(params);
    if (data) {
        renderNoticeTable(data.notices, data.currentPage, data.size);
        updatePagination(data.currentPage, data.totalPages);
    }
}

// 페이지 변경
window.changePage = async function(page) {
    const params = collectSearchParams();
    params.page = page;
    const data = await fetchNoticeList(params);
    if (data) {
        renderNoticeTable(data.notices, data.currentPage, data.size);
        updatePagination(data.currentPage, data.totalPages);
    }
};

// 필터 초기화
function resetFilters() {
    const today = new Date();
    const firstDay = new Date(today.getFullYear(), today.getMonth(), 1);
    const dateInputs = document.querySelectorAll('.date-filter input[type="date"]');
    dateInputs[0].value = formatDate(firstDay);
    dateInputs[1].value = formatDate(today);

    document.querySelector('input[name="type"][value="ALL"]').checked = true;
    document.querySelector('input[name="status"][value="ALL"]').checked = true;
    document.getElementById('searchKeyword').value = '';
    document.getElementById('sortSelect').value = 'LATEST';

    performSearch();
}

function formatDate(date) {
    const y = date.getFullYear();
    const m = String(date.getMonth() + 1).padStart(2, '0');
    const d = String(date.getDate()).padStart(2, '0');
    return `${y}-${m}-${d}`;
}

// ==================== 삭제 모달 ====================

let noticeToDelete = null;

function confirmDelete(id, title) {
    noticeToDelete = id;
    document.getElementById('deleteNoticeTitle').textContent = title;
    document.getElementById('deleteModal').style.display = 'flex';
}

function closeDeleteModal() {
    document.getElementById('deleteModal').style.display = 'none';
    noticeToDelete = null;
}

async function executeDelete() {
    if (!noticeToDelete) return;

    try {
        const res = await fetch(`/admin/system/notice/${noticeToDelete}`, {
            method: 'DELETE',
            credentials: 'include'
        });
        if (res.ok) {
            showMessage('공지 삭제 완료', 'success');
            closeDeleteModal();
            performSearch();
        } else {
            showMessage('삭제 실패', 'error');
        }
    } catch (err) {
        console.error('Delete error:', err);
        showMessage('삭제 중 오류 발생', 'error');
    }
}

function openEditModal(id) {
    // TODO: 수정 모달 구현 (필요 시)
    showMessage('수정 기능 준비 중', 'info');
}

// 외부 클릭 닫기
window.onclick = e => {
    const modal = document.getElementById('deleteModal');
    if (e.target === modal) closeDeleteModal();
};

// ==================== 이벤트 ====================

document.addEventListener('DOMContentLoaded', () => {
    document.querySelector('.search-btn').addEventListener('click', performSearch);
    document.getElementById('searchKeyword').addEventListener('keypress', e => {
        if (e.key === 'Enter') {
            e.preventDefault();
            performSearch();
        }
    });
    document.querySelectorAll('.date-filter input[type="date"]').forEach(i =>
        i.addEventListener('change', performSearch)
    );
    document.querySelectorAll('input[name="type"]').forEach(r =>
        r.addEventListener('change', performSearch)
    );
    document.querySelectorAll('input[name="status"]').forEach(r =>
        r.addEventListener('change', performSearch)
    );
    document.getElementById('sortSelect').addEventListener('change', performSearch);
});
