// ==================== 사용자 상태 인라인 수정 ====================

/**
 * 수정 모드 활성화
 */
function enableEditMode(userId) {
    console.log('✏️ 수정 모드 활성화:', userId);

    const row = document.querySelector(`tr[data-id="${userId}"]`);
    if (!row) return;

    // 이미 수정 모드인지 확인
    if (row.classList.contains('editing')) {
        return;
    }

    // 다른 수정 중인 행 취소
    cancelAllEditing();

    // 현재 상태 가져오기
    const statusBadge = row.querySelector('.status-badge');
    const currentStatus = statusBadge.classList.contains('active') ? 'ACTIVE' : 'INACTIVE';

    // 수정 모드 표시
    row.classList.add('editing');

    // 상태 셀을 select로 변경
    const statusCell = row.querySelector('.status-cell');
    statusCell.innerHTML = `
        <select class="status-select" data-original="${currentStatus}">
            <option value="ACTIVE" ${currentStatus === 'ACTIVE' ? 'selected' : ''}>활성</option>
            <option value="INACTIVE" ${currentStatus === 'INACTIVE' ? 'selected' : ''}>비활성</option>
        </select>
    `;

    // 액션 버튼을 저장/취소로 변경
    const actionsCell = row.querySelector('.actions');
    actionsCell.innerHTML = `
        <button class="btn-save" onclick="saveUserStatus(${userId})">저장</button>
        <button class="btn-cancel" onclick="cancelEdit(${userId})">취소</button>
    `;
}

/**
 * 수정 취소
 */
function cancelEdit(userId) {
    const row = document.querySelector(`tr[data-id="${userId}"]`);
    if (!row) return;

    // 원래 상태로 복원
    const select = row.querySelector('.status-select');
    const originalStatus = select?.dataset.original || 'ACTIVE';

    const statusCell = row.querySelector('.status-cell');
    const statusText = originalStatus === 'ACTIVE' ? '활성' : '비활성';
    statusCell.innerHTML = `
        <span class="status-badge ${originalStatus === 'ACTIVE' ? 'active' : 'inactive'}">
            ${statusText}
        </span>
    `;

    // 액션 버튼 복원
    const actionsCell = row.querySelector('.actions');
    actionsCell.innerHTML = `
        <button class="btn-edit" onclick="enableEditMode(${userId})">정보 수정</button>
    `;

    // 수정 모드 해제
    row.classList.remove('editing');
}

/**
 * 모든 수정 취소
 */
function cancelAllEditing() {
    const editingRows = document.querySelectorAll('tr.editing');
    editingRows.forEach(row => {
        const userId = row.dataset.id;
        if (userId) {
            cancelEdit(userId);
        }
    });
}

/**
 * 사용자 상태 저장
 */
async function saveUserStatus(userId) {
    const row = document.querySelector(`tr[data-id="${userId}"]`);
    if (!row) return;

    const select = row.querySelector('.status-select');
    const newStatus = select?.value;
    const originalStatus = select?.dataset.original;

    // 변경사항 없으면 그냥 취소
    if (newStatus === originalStatus) {
        cancelEdit(userId);
        return;
    }

    console.log('💾 상태 저장:', userId, newStatus);

    try {
        const response = await fetch(`/admin/user/api/${userId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': document.querySelector('meta[name="csrf-token"]')?.content
            },
            body: JSON.stringify(newStatus)
        });

        if (!response.ok) {
            const errorData = await response.json().catch(() => ({}));
            throw new Error(errorData.message || '상태 변경에 실패했습니다.');
        }

        const result = await response.json();

        // 성공 메시지
        showMessage(result.message || '성공적으로 수정되었습니다.', 'success');

        // UI 업데이트
        const statusText = newStatus === 'ACTIVE' ? '활성' : '비활성';
        const statusCell = row.querySelector('.status-cell');
        statusCell.innerHTML = `
            <span class="status-badge ${newStatus === 'ACTIVE' ? 'active' : 'inactive'}">
                ${statusText}
            </span>
        `;

        // 액션 버튼 복원
        const actionsCell = row.querySelector('.actions');
        actionsCell.innerHTML = `
            <button class="btn-edit" onclick="enableEditMode(${userId})">정보 수정</button>
        `;

        // 수정 모드 해제
        row.classList.remove('editing');

    } catch (error) {
        console.error('Error updating user status:', error);
        showMessage(error.message || '상태 변경에 실패했습니다.', 'error');

        // 실패 시 원래대로 복원
        cancelEdit(userId);
    }
}

/**
 * ESC 키로 수정 취소
 */
document.addEventListener('keydown', function(e) {
    if (e.key === 'Escape') {
        cancelAllEditing();
    }
});