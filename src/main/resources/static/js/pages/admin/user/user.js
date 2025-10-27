// ==================== 전역 변수 ====================
let isSearching = false; // 중복 요청 방지

// ==================== API 요청 ====================
async function fetchUserList(params) {
    if (isSearching) return null;

    try {
        isSearching = true;
        const queryString = new URLSearchParams(params).toString();
        console.log('📤 API 요청:', queryString);

        const response = await fetch(`/admin/user/api?${queryString}`, {
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
        console.log('📥 API 응답:', data);
        return data;
    } catch (error) {
        console.error('❌ Error fetching user list:', error);
        alert('데이터를 불러오는데 실패했습니다.');
        return null;
    } finally {
        isSearching = false;
    }
}

// ==================== 파라미터 수집 ====================
function collectSearchParams() {
    const startDateInput = document.querySelector('.date-filter input[type="date"]:first-child');
    const endDateInput = document.querySelector('.date-filter input[type="date"]:last-child');
    const roleRadio = document.querySelector('input[name="role"]:checked');
    const statusRadio = document.querySelector('input[name="status"]:checked');
    const keywordInput = document.getElementById('searchKeyword');
    const sortSelect = document.getElementById('sortSelect');

    const startDate = startDateInput?.value || '';
    const endDate = endDateInput?.value || '';
    const role = roleRadio?.value || 'STUDENT';
    const status = statusRadio?.value || 'ALL';
    const keyword = keywordInput?.value.trim() || '';
    const sortValue = sortSelect?.value || 'LATEST';

    // 정렬 파라미터 변환
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
        case 'NAME_ASC':
            sortBy = 'username';
            sortDirection = 'ASC';
            break;
        case 'NAME_DESC':
            sortBy = 'username';
            sortDirection = 'DESC';
            break;
    }

    const params = {
        page: 0,
        size: 10,
        sortBy: sortBy,
        sortDirection: sortDirection
    };

    // 날짜 필터 - 값이 있을 때만 추가
    if (startDate) {
        params.startDate = startDate + 'T00:00:00';
    }
    if (endDate) {
        params.endDate = endDate + 'T23:59:59';
    }

    // 회원 구분 - ALL이 아닐 때만 추가
    if (role && role !== 'ALL') {
        params.role = role;
    }

    // 상태 - ALL이 아닐 때만 추가
    if (status && status !== 'ALL') {
        params.userStatus = status;
    }

    // 검색어 - 값이 있을 때만 추가
    if (keyword) {
        params.keyword = keyword;
    }

    console.log('📋 최종 파라미터:', params);
    return params;
}

// ==================== 렌더링 ====================
function renderUserTable(users, currentPage, pageSize) {
    const tbody = document.querySelector('#memberTable tbody');

    if (!tbody) {
        console.error('❌ 테이블 tbody를 찾을 수 없습니다.');
        return;
    }

    if (!users || users.length === 0) {
        tbody.innerHTML = '<tr><td colspan="8" class="no-data">등록된 회원이 없습니다.</td></tr>';
        return;
    }

    tbody.innerHTML = users.map((user, index) => {
        const rowNumber = currentPage * pageSize + index + 1;
        const statusText = user.userStatus === 'ACTIVE' ? '활성' : '비활성';

        let roleBadge = '';
        if (user.role === 'TEACHER') {
            roleBadge = '<span class="role-badge tutor">강사</span>';
        } else {
            roleBadge = '<span class="role-badge student">학생</span>';
        }

        return `
            <tr data-id="${user.id}">
                <td>${rowNumber}</td>
                <td>${roleBadge}</td>
                <td>${user.username}</td>
                <td>${user.nickname}</td>
                <td>${user.email}</td>
                <td>${user.createdAt}</td>
                <td class="status-cell">
                    <span class="status-badge ${user.userStatus === 'ACTIVE' ? 'active' : 'inactive'}">
                        ${statusText}
                    </span>
                </td>
                <td class="actions">
                    <button class="btn-edit" onclick="editStatus(${user.id})">수정</button>
                    <button class="btn-delete" onclick="confirmDelete(${user.id}, '${user.nickname}')">삭제</button>
                </td>
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
    const data = await fetchUserList(params);

    if (data) {
        renderUserTable(data.users, data.currentPage, data.size);
        updatePagination(data.currentPage, data.totalPages);
    }
}

// 페이지 변경
window.changePage = async function (page) {
    const params = collectSearchParams();
    params.page = page;

    const data = await fetchUserList(params);

    if (data) {
        renderUserTable(data.users, data.currentPage, data.size);
        updatePagination(data.currentPage, data.totalPages);
    }
};

// 필터 초기화
function resetFilters() {
    // 날짜 초기화 (값 비우기)
    const startDateInput = document.querySelector('.date-filter input[type="date"]:first-child');
    const endDateInput = document.querySelector('.date-filter input[type="date"]:last-child');

    if (startDateInput) startDateInput.value = '';
    if (endDateInput) endDateInput.value = '';

    // 라디오 버튼 초기화
    const studentRadio = document.querySelector('input[name="role"][value="STUDENT"]');
    const allStatusRadio = document.querySelector('input[name="status"][value="ALL"]');

    if (studentRadio) studentRadio.checked = true;
    if (allStatusRadio) allStatusRadio.checked = true;

    // 검색어 초기화
    const keywordInput = document.getElementById('searchKeyword');
    if (keywordInput) keywordInput.value = '';

    // 정렬 초기화
    const sortSelect = document.getElementById('sortSelect');
    if (sortSelect) sortSelect.value = 'LATEST';

    // 검색 실행
    performSearch();
}

// ==================== 유틸리티 ====================
function initializeDateFilters() {
    // 날짜 필터를 빈 상태로 초기화 (null로 전송되도록)
    const startDateInput = document.querySelector('.date-filter input[type="date"]:first-child');
    const endDateInput = document.querySelector('.date-filter input[type="date"]:last-child');

    if (startDateInput) startDateInput.value = '';
    if (endDateInput) endDateInput.value = '';
}

// ==================== 이벤트 리스너 ====================
document.addEventListener('DOMContentLoaded', function () {
    console.log('🚀 페이지 초기화');

    // 날짜 필터 빈 상태로 초기화
    initializeDateFilters();

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

    // 정렬 선택
    const sortSelect = document.getElementById('sortSelect');
    if (sortSelect) {
        sortSelect.addEventListener('change', performSearch);
    }

    // 날짜 필터
    document.querySelectorAll('.date-filter input[type="date"]').forEach(input => {
        input.addEventListener('change', performSearch);
    });

    // 회원 구분 라디오
    document.querySelectorAll('input[name="role"]').forEach(radio => {
        radio.addEventListener('change', performSearch);
    });

    // 상태 라디오
    document.querySelectorAll('input[name="status"]').forEach(radio => {
        radio.addEventListener('change', performSearch);
    });

    // 초기 데이터 로드
    performSearch();
});