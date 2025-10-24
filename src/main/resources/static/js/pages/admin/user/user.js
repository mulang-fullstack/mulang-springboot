// ==================== 필터 및 검색 관련 ====================

// 검색 파라미터 수집
function collectSearchParams() {
    const startDate = document.querySelector('.date-filter input[type="date"]:first-child').value;
    const endDate = document.querySelector('.date-filter input[type="date"]:last-child').value;
    const role = document.querySelector('input[name="role"]:checked')?.value;
    const status = document.querySelector('input[name="status"]:checked')?.value;
    const keyword = document.getElementById('searchKeyword').value.trim();
    const sortValue = document.getElementById('sortSelect').value;

    // 정렬 파라미터 변환
    let sortBy = 'createdAt';
    let sortDirection = 'DESC';

    switch(sortValue) {
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
        page: 0, // 검색 시 첫 페이지로
        size: 10
    };

    // startDate와 endDate 처리
    if (startDate) {
        params.startDate = startDate + 'T00:00:00';
    }
    if (endDate) {
        params.endDate = endDate + 'T23:59:59';
    }

    // role 처리 (ALL이 아닌 경우만)
    if (role && role !== 'ALL') {
        params.role = role;
    }

    // status 처리 (ALL이 아닌 경우만)
    if (status && status !== 'ALL') {
        params.userStatus = status === 'ACTIVE' ? 'ACTIVE' : 'INACTIVE';
    }

    // keyword 처리
    if (keyword) {
        params.keyword = keyword;
    }

    // 정렬
    params.sortBy = sortBy;
    params.sortDirection = sortDirection;

    return params;
}

// 비동기 요청 보내기
async function fetchUserList(params) {
    try {
        const queryString = new URLSearchParams(params).toString();
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
        return data;
    } catch (error) {
        console.error('Error fetching user list:', error);
        alert('데이터를 불러오는데 실패했습니다.');
        return null;
    }
}

// 테이블 렌더링
function renderUserTable(users, currentPage, pageSize) {
    const tbody = document.querySelector('#memberTable tbody');
    if (!users || users.length === 0) {
        tbody.innerHTML = '<tr><td colspan="9" class="no-data">등록된 회원이 없습니다.</td></tr>';
        return;
    }

    tbody.innerHTML = users.map((user, index) => {
        const rowNumber = currentPage * pageSize + index + 1;
        const statusClass = user.userStatus === 'ACTIVE' ? 'success' : 'inactive';
        const statusText = user.userStatus === 'ACTIVE' ? '활성' : '비활성';
        const statusValue = user.userStatus === 'ACTIVE'? 'ACTIVE' : 'INACTIVE';

        let roleBadge = '';
        if (user.role === 'TEACHER') {
            roleBadge = '<span class="role-badge tutor">강사</span>';
        } else if (user.role === 'STUDENT') {
            roleBadge = '<span class="role-badge student">학생</span>';
        } else {
            roleBadge = '<span class="role-badge admin">관리자</span>';
        }

        return `
            <tr data-id="${user.id}"
                data-role="${user.role}"
                data-status="${statusValue}"
                data-date="${user.createdAt}">
                <td>${rowNumber}</td>
                <td>
                    <span class="status-dot ${statusClass}"></span>
                </td>
                <td>${user.username}</td>
                <td>${user.nickname}</td>
                <td>${user.email}</td>
                <td>${user.createdAt}</td>
                <td>${roleBadge}</td>
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

// 페이지네이션 업데이트
function updatePagination(currentPage, totalPages) {
    window.paginationData = {
        currentPage: currentPage,
        totalPages: totalPages,
        baseUrl: '/admin/user',
        asyncMode: true
    };

    if (typeof renderPagination === 'function') {
        renderPagination();
    }
}


// 검색 실행
async function performSearch() {
    const params = collectSearchParams();
    const data = await fetchUserList(params);

    if (data) {
        renderUserTable(data.users, data.currentPage, data.size);
        updatePagination(data.currentPage, data.totalPages);
    }
}

// 페이지 변경 (pagination.js에서 호출될 함수)
window.changePage = async function(page) {
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
    // 날짜 초기화 (현재 월의 1일 ~ 오늘)
    const today = new Date();
    const firstDay = new Date(today.getFullYear(), today.getMonth(), 1);

    const dateInputs = document.querySelectorAll('.date-filter input[type="date"]');
    dateInputs[0].value = formatDate(firstDay);
    dateInputs[1].value = formatDate(today);

    // 라디오 버튼 초기화
    document.querySelector('input[name="role"][value="ALL"]').checked = true;
    document.querySelector('input[name="status"][value="ALL"]').checked = true;

    // 검색어 초기화
    document.getElementById('searchKeyword').value = '';

    // 정렬 초기화
    document.getElementById('sortSelect').value = 'LATEST';

    // 검색 실행
    performSearch();
}

// 날짜 포맷팅 함수
function formatDate(date) {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
}

// ==================== 이벤트 리스너 ====================

document.addEventListener('DOMContentLoaded', function() {
    // 검색 버튼 클릭
    document.querySelector('.search-btn').addEventListener('click', performSearch);

    // 검색어 입력 시 엔터키
    document.getElementById('searchKeyword').addEventListener('keypress', function(e) {
        if (e.key === 'Enter') {
            e.preventDefault();
            performSearch();
        }
    });

    // 날짜 필터 변경
    document.querySelectorAll('.date-filter input[type="date"]').forEach(input => {
        input.addEventListener('change', performSearch);
    });

    // 회원 구분 라디오 버튼
    document.querySelectorAll('input[name="role"]').forEach(radio => {
        radio.addEventListener('change', performSearch);
    });

    // 상태 라디오 버튼
    document.querySelectorAll('input[name="status"]').forEach(radio => {
        radio.addEventListener('change', performSearch);
    });

    // 정렬 선택
    document.getElementById('sortSelect').addEventListener('change', performSearch);

    // 초기화 버튼은 이미 onclick으로 연결되어 있음
});

// ==================== 기존 함수들 (모달 등) ====================

let userToDelete = null;

function confirmDelete(userId, nickname) {
    userToDelete = userId;
    document.getElementById('deleteMemberName').textContent = nickname;
    document.getElementById('deleteModal').style.display = 'flex';
}

function closeDeleteModal() {
    document.getElementById('deleteModal').style.display = 'none';
    userToDelete = null;
}

async function executeDelete() {
    if (!userToDelete) return;

    try {
        const response = await fetch(`/admin/user/${userToDelete}`, {
            method: 'DELETE',
            headers: {
                'X-CSRF-TOKEN': document.querySelector('meta[name="csrf-token"]')?.content
            }
        });

        if (response.ok) {
            alert('회원이 삭제되었습니다.');
            closeDeleteModal();
            performSearch(); // 목록 새로고침
        } else {
            alert('삭제에 실패했습니다.');
        }
    } catch (error) {
        console.error('Delete error:', error);
        alert('삭제 중 오류가 발생했습니다.');
    }
}

function editStatus(userId) {
    // 수정 페이지로 이동 또는 모달 표시
    window.location.href = `/admin/user/edit/${userId}`;
}

// 모달 외부 클릭 시 닫기
window.onclick = function(event) {
    const modal = document.getElementById('deleteModal');
    if (event.target === modal) {
        closeDeleteModal();
    }
};