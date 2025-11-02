// ==================== 강좌 심사 상태 변경 ====================

/**
 * 승인 모달 열기
 */
function openApproveModal(courseId, courseTitle) {
    const modal = document.getElementById('approveModal');
    if (!modal) {
        console.error('승인 모달을 찾을 수 없습니다.');
        return;
    }

    // 모달 데이터 설정
    document.getElementById('approveCourseId').value = courseId;
    document.getElementById('approveCourseTitle').textContent = courseTitle;

    // 모달 표시
    modal.style.display = 'flex';
    document.body.style.overflow = 'hidden';
}

/**
 * 승인 모달 닫기
 */
function closeApproveModal() {
    const modal = document.getElementById('approveModal');
    if (modal) {
        modal.style.display = 'none';
        document.body.style.overflow = '';
    }

    // 폼 초기화
    document.getElementById('approveCourseId').value = '';
}

/**
 * 강좌 승인 실행
 */
async function executeApprove() {
    const courseId = document.getElementById('approveCourseId').value;

    try {
        console.log('📡 강좌 승인 요청:', courseId);

        const response = await fetch(`/admin/content/course/api/${courseId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': document.querySelector('meta[name="csrf-token"]')?.content
            },
            body: JSON.stringify({
                status: 'PUBLIC'
            })
        });

        if (!response.ok) {
            const err = await response.json().catch(() => ({}));
            throw new Error(err.message || '승인 요청에 실패했습니다.');
        }

        const result = await response.json();
        showMessage(result.message || '강좌가 성공적으로 승인되었습니다.', 'success');

        // 모달 닫기
        closeApproveModal();

        // ✅ 테이블 즉시 갱신
        performSearch();

    } catch (error) {
        console.error('❌ 강좌 승인 실패:', error);
        showMessage(error.message || '승인에 실패했습니다.', 'error');
    }
}

/**
 * 거절 모달 열기
 */
function openRejectModal(courseId, courseTitle) {
    const modal = document.getElementById('rejectModal');
    if (!modal) {
        console.error('거절 모달을 찾을 수 없습니다.');
        return;
    }

    // 모달 데이터 설정
    document.getElementById('rejectCourseId').value = courseId;
    document.getElementById('rejectCourseTitle').textContent = courseTitle;
    document.getElementById('rejectionReason').value = '';

    // 모달 표시
    modal.style.display = 'flex';
    document.body.style.overflow = 'hidden';
}

/**
 * 거절 모달 닫기
 */
function closeRejectModal() {
    const modal = document.getElementById('rejectModal');
    if (modal) {
        modal.style.display = 'none';
        document.body.style.overflow = '';
    }

    // 폼 초기화
    document.getElementById('rejectCourseId').value = '';
    document.getElementById('rejectionReason').value = '';
}

/**
 * 강좌 거절 실행
 */
async function executeReject() {
    const courseId = document.getElementById('rejectCourseId').value;
    const rejectionReason = document.getElementById('rejectionReason').value.trim();

    // 유효성 검사
    if (!rejectionReason) {
        showMessage('거절 사유를 입력해주세요.', 'warning');
        return;
    }

    if (rejectionReason.length < 10) {
        showMessage('거절 사유는 최소 10자 이상 입력해주세요.', 'warning');
        return;
    }

    try {
        console.log('📡 강좌 거절 요청:', courseId, rejectionReason);

        const response = await fetch(`/admin/content/course/api/${courseId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': document.querySelector('meta[name="csrf-token"]')?.content
            },
            body: JSON.stringify({
                status: 'REJECTED',
                rejectionReason: rejectionReason
            })
        });

        if (!response.ok) {
            const err = await response.json().catch(() => ({}));
            throw new Error(err.message || '거절 요청에 실패했습니다.');
        }

        const result = await response.json();
        showMessage(result.message || '강좌가 거절되었습니다.', 'success');

        // 모달 닫기
        closeRejectModal();

        // ✅ 테이블 즉시 갱신
        performSearch();

    } catch (error) {
        console.error('❌ 강좌 거절 실패:', error);
        showMessage(error.message || '거절에 실패했습니다.', 'error');
    }
}

/**
 * ESC 키로 모달 닫기
 */
document.addEventListener('keydown', function(e) {
    if (e.key === 'Escape') {
        const approveModal = document.getElementById('approveModal');
        const rejectModal = document.getElementById('rejectModal');

        if (approveModal && approveModal.style.display === 'flex') {
            closeApproveModal();
        }
        if (rejectModal && rejectModal.style.display === 'flex') {
            closeRejectModal();
        }
    }
});

/**
 * 모달 외부 클릭으로 닫기
 */
window.addEventListener('click', function(e) {
    const approveModal = document.getElementById('approveModal');
    const rejectModal = document.getElementById('rejectModal');

    if (e.target === approveModal) {
        closeApproveModal();
    }
    if (e.target === rejectModal) {
        closeRejectModal();
    }
});