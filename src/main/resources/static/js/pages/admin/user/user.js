/**
 * 회원 관리 페이지 JavaScript
 * Path: /js/pages/admin/user/user.js
 */

// ========================================
// 전역 변수
// ========================================
let editingRow = null;
let deleteTargetId = null;

/**
 * 서버에서 페이지 데이터 로드
 */
function loadPageData(page) {
    const params = new URLSearchParams(window.location.search);
    params.set('page', page);

    // 현재 필터 상태 추가
    const roleFilters = Array.from(document.querySelectorAll('input[name="role"]:checked'))
        .map(cb => cb.value);
    const statusFilter = document.querySelector('input[name="status"]:checked')?.value;
    const sortOrder = document.getElementById('sortSelect')?.value;
    const keyword = document.querySelector('input[name="keyword"]')?.value;

    if (roleFilters.length > 0) {
        params.set('roles', roleFilters.join(','));
    }
    if (statusFilter) {
        params.set('status', statusFilter);
    }
    if (sortOrder) {
        params.set('sort', sortOrder);
    }
    if (keyword) {
        params.set('keyword', keyword);
    }

    // 페이지 이동 (SSR 방식)
    window.location.href = `${window.location.pathname}?${params.toString()}`;
}


// ========================================
// 회원 상태 관리 함수
// ========================================

/**
 * 상태 수정 모드 진입
 */
function editStatus(memberId) {
    // 이미 수정 중인 행이 있으면 취소
    if (editingRow) {
        cancelEdit();
    }

    const row = document.querySelector(`tr[data-id="${memberId}"]`);
    if (!row) return;

    editingRow = row;
    row.classList.add('editing');

    const statusCell = row.querySelector('.status-cell');
    const currentStatus = row.dataset.status;

    // 상태 선택 드롭다운 생성
    const selectHtml = `
        <select class="status-select" id="status-select-${memberId}">
            <option value="ACTIVE" ${currentStatus === 'ACTIVE' ? 'selected' : ''}>활성</option>
            <option value="INACTIVE" ${currentStatus === 'INACTIVE' ? 'selected' : ''}>비활성</option>
        </select>
    `;
    statusCell.innerHTML = selectHtml;

    // 버튼 변경
    const actionsCell = row.querySelector('.actions');
    actionsCell.innerHTML = `
        <button class="btn-save" onclick="saveStatus(${memberId})">완료</button>
        <button class="btn-cancel" onclick="cancelEdit()">취소</button>
    `;
}

/**
 * 상태 저장
 */
async function saveStatus(memberId) {
    const row = document.querySelector(`tr[data-id="${memberId}"]`);
    const select = document.getElementById(`status-select-${memberId}`);
    const newStatus = select.value;

    try {
        const response = await fetch('/admin/user/updateStatus', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': document.querySelector('meta[name="csrf-token"]')?.content || ''
            },
            body: JSON.stringify({
                memberId: memberId,
                status: newStatus
            })
        });

        if (response.ok) {
            const result = await response.json();
            if (result.success) {
                updateRowStatus(row, newStatus);
                showToast('상태가 변경되었습니다.', 'success');
            } else {
                showToast(result.message || '상태 변경에 실패했습니다.', 'error');
                cancelEdit();
            }
        } else {
            throw new Error('서버 오류');
        }
    } catch (error) {
        console.error('Error updating status:', error);
        showToast('상태 변경 중 오류가 발생했습니다.', 'error');
        cancelEdit();
    }
}

/**
 * 행 상태 업데이트
 */
function updateRowStatus(row, newStatus) {
    row.dataset.status = newStatus;
    row.classList.remove('editing');

    const statusCell = row.querySelector('.status-cell');
    const statusClass = newStatus === 'ACTIVE' ? 'active' : 'inactive';
    const statusText = newStatus === 'ACTIVE' ? '활성' : '비활성';

    statusCell.innerHTML = `<span class="status-badge ${statusClass}">${statusText}</span>`;

    const actionsCell = row.querySelector('.actions');
    const memberId = row.dataset.id;
    const memberName = row.cells[1].textContent;

    actionsCell.innerHTML = `
        <button class="btn-edit" onclick="editStatus(${memberId})">상태 수정</button>
        <button class="btn-delete" onclick="confirmDelete(${memberId}, '${memberName}')">영구 삭제</button>
    `;

    editingRow = null;
}

/**
 * 수정 취소
 */
function cancelEdit() {
    if (!editingRow) return;

    const memberId = editingRow.dataset.id;
    const currentStatus = editingRow.dataset.status;
    const memberName = editingRow.cells[1].textContent;

    editingRow.classList.remove('editing');

    const statusCell = editingRow.querySelector('.status-cell');
    const statusClass = currentStatus === 'ACTIVE' ? 'active' : 'inactive';
    const statusText = currentStatus === 'ACTIVE' ? '활성' : '비활성';

    statusCell.innerHTML = `<span class="status-badge ${statusClass}">${statusText}</span>`;

    const actionsCell = editingRow.querySelector('.actions');
    actionsCell.innerHTML = `
        <button class="btn-edit" onclick="editStatus(${memberId})">상태 수정</button>
        <button class="btn-delete" onclick="confirmDelete(${memberId}, '${memberName}')">영구 삭제</button>
    `;

    editingRow = null;
}

// ========================================
// 회원 삭제 관련 함수
// ========================================

/**
 * 삭제 확인 모달 열기
 */
function confirmDelete(memberId, memberName) {
    deleteTargetId = memberId;
    const nameElement = document.getElementById('deleteMemberName');
    if (nameElement) {
        nameElement.textContent = memberName;
    }

    const modal = document.getElementById('deleteModal');
    if (modal) {
        modal.classList.add('show');
    }
}

/**
 * 삭제 모달 닫기
 */
function closeDeleteModal() {
    const modal = document.getElementById('deleteModal');
    if (modal) {
        modal.classList.remove('show');
    }
    deleteTargetId = null;
}

/**
 * 실제 삭제 실행
 */
async function executeDelete() {
    if (!deleteTargetId) return;

    try {
        const response = await fetch(`/admin/user/memberDelete?id=${deleteTargetId}`, {
            method: 'DELETE',
            headers: {
                'X-CSRF-TOKEN': document.querySelector('meta[name="csrf-token"]')?.content || ''
            }
        });

        if (response.ok) {
            const result = await response.json();
            if (result.success) {
                removeRow(deleteTargetId);
                showToast('회원이 삭제되었습니다.', 'success');
            } else {
                showToast(result.message || '삭제에 실패했습니다.', 'error');
            }
        } else {
            throw new Error('서버 오류');
        }
    } catch (error) {
        console.error('Error deleting member:', error);
        showToast('삭제 중 오류가 발생했습니다.', 'error');
    }

    closeDeleteModal();
}

/**
 * 행 제거 애니메이션
 */
function removeRow(memberId) {
    const row = document.querySelector(`tr[data-id="${memberId}"]`);
    if (row) {
        row.style.transition = 'opacity 0.3s';
        row.style.opacity = '0';
        setTimeout(() => {
            row.remove();
            checkEmptyTable();
        }, 300);
    }
}

/**
 * 테이블이 비어있는지 확인
 */
function checkEmptyTable() {
    const tbody = document.querySelector('#memberTable tbody');
    if (!tbody) return;

    const rows = tbody.querySelectorAll('tr[data-id]');

    if (rows.length === 0) {
        tbody.innerHTML = '<tr><td colspan="8" class="no-data">등록된 회원이 없습니다.</td></tr>';
    }
}

// ========================================
// 필터 관련 함수
// ========================================

/**
 * 필터 초기화
 */
function resetFilters() {
    // 체크박스 초기화
    document.querySelectorAll('input[name="role"]').forEach(cb => cb.checked = true);

    // 라디오 버튼 초기화
    const allRadio = document.querySelector('input[name="status"][value="ALL"]');
    if (allRadio) {
        allRadio.checked = true;
    }

    // 정렬 초기화
    const sortSelect = document.getElementById('sortSelect');
    if (sortSelect) {
        sortSelect.value = 'LATEST';
    }

    // 검색어 초기화
    const keywordInput = document.querySelector('input[name="keyword"]');
    if (keywordInput) {
        keywordInput.value = '';
    }

    // 필터 적용 (서버로 요청)
    applyFiltersToServer();
}

/**
 * 필터 적용 (서버 사이드)
 */
function applyFiltersToServer() {
    const params = new URLSearchParams();

    // 역할 필터
    const roleFilters = Array.from(document.querySelectorAll('input[name="role"]:checked'))
        .map(cb => cb.value);
    if (roleFilters.length > 0) {
        params.set('roles', roleFilters.join(','));
    }

    // 상태 필터
    const statusFilter = document.querySelector('input[name="status"]:checked')?.value;
    if (statusFilter && statusFilter !== 'ALL') {
        params.set('status', statusFilter);
    }

    // 정렬
    const sortOrder = document.getElementById('sortSelect')?.value;
    if (sortOrder) {
        params.set('sort', sortOrder);
    }

    // 검색어
    const keyword = document.querySelector('input[name="keyword"]')?.value;
    if (keyword) {
        params.set('keyword', keyword);
    }

    // 페이지 이동
    window.location.href = `${window.location.pathname}?${params.toString()}`;
}

// ========================================
// 유틸리티 함수
// ========================================

/**
 * 토스트 메시지 표시
 */
function showToast(message, type = 'info') {
    // 기존 토스트 제거
    const existingToast = document.querySelector('.toast-message');
    if (existingToast) {
        existingToast.remove();
    }

    // 새 토스트 생성
    const toast = document.createElement('div');
    toast.className = `toast-message toast-${type}`;
    toast.textContent = message;

    // 스타일 적용
    toast.style.cssText = `
        position: fixed;
        top: 20px;
        right: 20px;
        padding: 12px 24px;
        border-radius: 8px;
        color: white;
        font-size: 14px;
        z-index: 9999;
        animation: slideIn 0.3s ease;
        box-shadow: 0 4px 6px rgba(0,0,0,0.1);
    `;

    // 타입별 배경색
    const colors = {
        success: '#10b981',
        error: '#ef4444',
        info: '#3b82f6',
        warning: '#f59e0b'
    };

    toast.style.backgroundColor = colors[type] || colors.info;

    document.body.appendChild(toast);

    // 3초 후 자동 제거
    setTimeout(() => {
        toast.style.animation = 'slideOut 0.3s ease';
        setTimeout(() => toast.remove(), 300);
    }, 3000);
}

/**
 * 디바운스 함수
 */
function debounce(func, wait) {
    let timeout;
    return function executedFunction(...args) {
        const later = () => {
            clearTimeout(timeout);
            func(...args);
        };
        clearTimeout(timeout);
        timeout = setTimeout(later, wait);
    };
}

/**
 * 쿼리 파라미터 가져오기
 */
function getQueryParam(name) {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get(name);
}

/**
 * 쿼리 파라미터 설정
 */
function setQueryParam(name, value) {
    const url = new URL(window.location);
    url.searchParams.set(name, value);
    window.history.pushState({}, '', url);
}

// ========================================
// 초기화 및 이벤트 리스너
// ========================================

/**
 * 페이지 초기화
 */
function initializePage() {
    console.log('🚀 User.js 초기화 시작');

    // 페이지네이션 데이터 확인
    if (window.paginationData) {
        console.log('✅ Pagination 데이터 확인:', window.paginationData);
    } else {
        console.warn('⚠️ Pagination 데이터 없음');
    }

    // 필터 이벤트 리스너 등록
    registerFilterListeners();

    // 키보드 이벤트 리스너
    registerKeyboardListeners();

    // 모달 이벤트 리스너
    registerModalListeners();

    // 검색 폼 이벤트
    registerSearchFormListener();

    console.log('✅ User.js 초기화 완료');
}

/**
 * 필터 이벤트 리스너 등록
 */
function registerFilterListeners() {
    // 체크박스와 라디오 버튼 변경 이벤트
    document.querySelectorAll('input[name="role"], input[name="status"]').forEach(input => {
        input.addEventListener('change', debounce(applyFiltersToServer, 300));
    });

    // 정렬 변경 이벤트
    const sortSelect = document.getElementById('sortSelect');
    if (sortSelect) {
        sortSelect.addEventListener('change', debounce(applyFiltersToServer, 300));
    }
}

/**
 * 검색 폼 이벤트 리스너
 */
function registerSearchFormListener() {
    const searchForm = document.querySelector('.search-form');
    if (searchForm) {
        searchForm.addEventListener('submit', (e) => {
            // SSR 방식으로 서버에 검색 요청
            // 기본 동작 유지 (폼 제출)
        });
    }
}

/**
 * 키보드 이벤트 리스너 등록
 */
function registerKeyboardListeners() {
    document.addEventListener('keydown', (e) => {
        // ESC 키로 모달 닫기
        if (e.key === 'Escape') {
            closeDeleteModal();
            if (editingRow) {
                cancelEdit();
            }
        }

        // Enter 키로 수정 완료
        if (e.key === 'Enter' && editingRow) {
            const memberId = editingRow.dataset.id;
            saveStatus(memberId);
        }
    });
}

/**
 * 모달 이벤트 리스너 등록
 */
function registerModalListeners() {
    // 모달 외부 클릭시 닫기
    const deleteModal = document.getElementById('deleteModal');
    if (deleteModal) {
        deleteModal.addEventListener('click', (e) => {
            if (e.target.id === 'deleteModal') {
                closeDeleteModal();
            }
        });
    }
}

// ========================================
// 외부 노출 함수 (전역)
// ========================================

// window 객체에 함수 등록 (HTML onclick에서 호출)
window.editStatus = editStatus;
window.saveStatus = saveStatus;
window.cancelEdit = cancelEdit;
window.confirmDelete = confirmDelete;
window.closeDeleteModal = closeDeleteModal;
window.executeDelete = executeDelete;
window.resetFilters = resetFilters;
window.loadPageData = loadPageData;

// ========================================
// DOM 로드 완료시 실행
// ========================================

document.addEventListener('DOMContentLoaded', initializePage);

// CSS 애니메이션 정의 (동적 추가)
const style = document.createElement('style');
style.textContent = `
    @keyframes slideIn {
        from {
            transform: translateX(100%);
            opacity: 0;
        }
        to {
            transform: translateX(0);
            opacity: 1;
        }
    }
    
    @keyframes slideOut {
        from {
            transform: translateX(0);
            opacity: 1;
        }
        to {
            transform: translateX(100%);
            opacity: 0;
        }
    }
`;
document.head.appendChild(style);