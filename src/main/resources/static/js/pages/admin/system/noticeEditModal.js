// ==================== 수정 모달 열기/닫기 ====================
function openEditModal() {
    document.getElementById('noticeEditModal').classList.remove('hidden');
    document.body.style.overflow = 'hidden'; // 스크롤 방지
    console.log('✅ 수정 모달 열림');
}

function closeEditModal() {
    document.getElementById('noticeEditModal').classList.add('hidden');
    document.body.style.overflow = ''; // 스크롤 복원
    document.getElementById('noticeEditForm').reset();
    document.getElementById('editCharCount').textContent = '0';
    console.log('✅ 수정 모달 닫힘');
}

// ==================== ESC 키로 수정 모달 닫기 ====================
document.addEventListener('keydown', function(e) {
    if (e.key === 'Escape') {
        const editModal = document.getElementById('noticeEditModal');
        if (editModal && !editModal.classList.contains('hidden')) {
            closeEditModal();
        }
    }
});

// ==================== 오버레이 클릭 시 수정 모달 닫기 ====================
document.addEventListener('DOMContentLoaded', function() {
    const editModal = document.getElementById('noticeEditModal');
    if (editModal) {
        editModal.addEventListener('click', function(e) {
            if (e.target === this) {
                closeEditModal();
            }
        });
    }
});

// ==================== 글자 수 카운터 (수정 모달) ====================
document.addEventListener('DOMContentLoaded', function() {
    const editContent = document.getElementById('editNoticeContent');
    if (editContent) {
        editContent.addEventListener('input', function() {
            const count = this.value.length;
            document.getElementById('editCharCount').textContent = count;

            if (count > 1000) {
                this.value = this.value.substring(0, 1000);
                document.getElementById('editCharCount').textContent = '1000';
            }
        });
    }
});

// ==================== 수정 폼 제출 ====================
document.addEventListener('DOMContentLoaded', function() {
    const editForm = document.getElementById('noticeEditForm');
    if (editForm) {
        editForm.addEventListener('submit', async function (e) {
            e.preventDefault();

            const noticeId = document.getElementById('editNoticeId').value;
            const formData = new FormData(this);
            const data = Object.fromEntries(formData.entries());

            console.log('📤 수정 요청 데이터:', data);

            try {
                const res = await fetch(`/admin/system/notice/${noticeId}`, {
                    method: 'PUT',
                    headers: { 'Content-Type': 'application/json' },
                    credentials: 'include',
                    body: JSON.stringify(data)
                });

                if (res.ok) {
                    showMessage('공지 수정 완료', 'success');
                    closeEditModal();

                    // 목록 새로고침
                    setTimeout(() => {
                        if (typeof performSearch === 'function') {
                            performSearch();
                        } else {
                            location.reload();
                        }
                    }, 500);
                } else if (res.status === 400) {
                    const text = await res.text();
                    showMessage(`입력 오류: ${text}`, 'warning');
                } else if (res.status === 401) {
                    showMessage('로그인 후 이용 가능합니다', 'error');
                } else if (res.status === 404) {
                    showMessage('해당 공지사항을 찾을 수 없습니다', 'error');
                } else {
                    showMessage('수정 실패', 'error');
                }
            } catch (err) {
                console.error('❌ 수정 오류:', err);
                showMessage('서버 오류', 'error');
            }
        });
    }
});

// ==================== 메시지 표시 함수 (기존 함수 재사용) ====================
// showMessage 함수가 없다면 여기에 추가
if (typeof showMessage !== 'function') {
    function showMessage(message, type = 'info') {
        // 간단한 alert로 대체 (실제 프로젝트에서는 toast 메시지 사용)
        alert(message);
    }
}