// 모달 열기/닫기
function openNoticeModal() {
    document.getElementById('noticeModal').classList.remove('hidden');
    document.body.style.overflow = 'hidden'; // 스크롤 방지
}

function closeNoticeModal() {
    document.getElementById('noticeModal').classList.add('hidden');
    document.body.style.overflow = ''; // 스크롤 복원
    document.getElementById('noticeForm').reset();
    document.getElementById('charCount').textContent = '0';
}

// ESC 키로 모달 닫기
document.addEventListener('keydown', function(e) {
    if (e.key === 'Escape') {
        const modal = document.getElementById('noticeModal');
        if (!modal.classList.contains('hidden')) {
            closeNoticeModal();
        }
    }
});

// 오버레이 클릭 시 모달 닫기
document.getElementById('noticeModal').addEventListener('click', function(e) {
    if (e.target === this) {
        closeNoticeModal();
    }
});

// 글자 수 카운터
document.getElementById('noticeContent').addEventListener('input', function() {
    const count = this.value.length;
    document.getElementById('charCount').textContent = count;

    if (count > 1000) {
        this.value = this.value.substring(0, 1000);
        document.getElementById('charCount').textContent = '1000';
    }
});

document.getElementById('noticeForm').addEventListener('submit', async function (e) {
    e.preventDefault();

    const data = Object.fromEntries(new FormData(this).entries());

    try {
        const res = await fetch('/admin/system/notice', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            credentials: 'include',
            body: JSON.stringify(data)
        });

        if (res.ok) {
            showMessage('공지 등록 완료', 'success');
            setTimeout(() => {
                location.href = '/admin/system/notice';
            }, 1000);
        } else if (res.status === 400) {
            const text = await res.text();
            showMessage(`입력 오류: ${text}`, 'warning');
        } else if (res.status === 401) {
            showMessage('로그인 후 이용 가능합니다', 'error');
        } else {
            showMessage('등록 실패', 'error');
        }
    } catch (err) {
        console.error(err);
        showMessage('서버 오류', 'error');
    }
});

