// ==================== 강좌 상태 인라인 수정 ====================

/**
 * 수정 모드 활성화
 */
function enableCourseEditMode(courseId) {
    console.log('✏️ 수정 모드 활성화:', courseId);

    const row = document.querySelector(`tr[data-id="${courseId}"]`);
    if (!row) return;

    // 이미 수정 모드인지 확인
    if (row.classList.contains('editing')) {
        return;
    }

    // 다른 수정 중인 행 취소
    cancelAllCourseEditing();

    // 현재 상태 가져오기
    const statusBadge = row.querySelector('.status-badge');
    const currentStatus = statusBadge.classList.contains('public') ? 'PUBLIC' : 'PRIVATE';

    // 수정 모드 표시
    row.classList.add('editing');

    // 상태 셀을 select로 변경
    const statusCell = row.querySelector('td:nth-child(7)'); // 상태 컬럼
    statusCell.innerHTML = `
        <select class="status-select" data-original="${currentStatus}">
            <option value="PUBLIC" ${currentStatus === 'PUBLIC' ? 'selected' : ''}>공개</option>
            <option value="PRIVATE" ${currentStatus === 'PRIVATE' ? 'selected' : ''}>비공개</option>
        </select>
    `;

    // 액션 버튼을 저장/취소로 변경
    const actionsCell = row.querySelector('.actions');
    actionsCell.innerHTML = `
        <button class="btn-save" onclick="saveCourseStatus(${courseId})">저장</button>
        <button class="btn-cancel" onclick="cancelCourseEdit(${courseId})">취소</button>
    `;
}

/**
 * 수정 취소
 */
function cancelCourseEdit(courseId) {
    const row = document.querySelector(`tr[data-id="${courseId}"]`);
    if (!row) return;

    // 원래 상태로 복원
    const select = row.querySelector('.status-select');
    const originalStatus = select?.dataset.original || 'PUBLIC';

    const statusCell = row.querySelector('td:nth-child(7)');
    const statusText = originalStatus === 'PUBLIC' ? '공개' : '비공개';
    statusCell.innerHTML = `
        <span class="status-badge ${originalStatus === 'PUBLIC' ? 'public' : 'private'}">
            ${statusText}
        </span>
    `;

    // 액션 버튼 복원
    const actionsCell = row.querySelector('.actions');
    actionsCell.innerHTML = `
        <button class="btn-edit" onclick="enableCourseEditMode(${courseId})">정보 수정</button>
    `;

    // 수정 모드 해제
    row.classList.remove('editing');
}

/**
 * 모든 수정 취소
 */
function cancelAllCourseEditing() {
    const editingRows = document.querySelectorAll('tr.editing');
    editingRows.forEach(row => {
        const courseId = row.dataset.id;
        if (courseId) {
            cancelCourseEdit(courseId);
        }
    });
}

/**
 * 강좌 상태 저장
 */
async function saveCourseStatus(courseId) {
    const row = document.querySelector(`tr[data-id="${courseId}"]`);
    if (!row) return;

    const select = row.querySelector('.status-select');
    const newStatus = select?.value;
    const originalStatus = select?.dataset.original;

    // 변경사항 없으면 그냥 취소
    if (newStatus === originalStatus) {
        cancelCourseEdit(courseId);
        return;
    }

    console.log('💾 상태 저장:', courseId, newStatus);

    try {
        const response = await fetch(`/admin/content/course/api/${courseId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': document.querySelector('meta[name="csrf-token"]')?.content
            },
            body: JSON.stringify({
                status: newStatus
            })
        });

        if (!response.ok) {
            const errorData = await response.json().catch(() => ({}));
            throw new Error(errorData.message || '상태 변경에 실패했습니다.');
        }

        const result = await response.json();

        // 성공 메시지
        showMessage(result.message || '성공적으로 수정되었습니다.', 'success');

        // 페이지 새로고침
        setTimeout(() => {
            window.location.reload();
        }, 500); // 메시지를 잠깐 보여준 후 새로고침

    } catch (error) {
        console.error('Error updating course status:', error);
        showMessage(error.message || '상태 변경에 실패했습니다.', 'error');

        // 실패 시 원래대로 복원
        cancelCourseEdit(courseId);
    }
}
/**
 * ESC 키로 수정 취소
 */
document.addEventListener('keydown', function(e) {
    if (e.key === 'Escape') {
        cancelAllCourseEditing();
    }
});