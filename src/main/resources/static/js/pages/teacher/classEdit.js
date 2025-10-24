document.addEventListener('DOMContentLoaded', () => {
    // 메뉴 열기 / 닫기
    document.querySelectorAll('.menu-btn').forEach(btn => {
        btn.addEventListener('click', e => {
            const dropdown = btn.nextElementSibling;
            dropdown.classList.toggle('show');
            e.stopPropagation();
        });
    });

    // 바깥 클릭 시 메뉴 닫기
    document.addEventListener('click', () => {
        document.querySelectorAll('.menu-dropdown').forEach(d => d.classList.remove('show'));
    });

    // 수정 버튼 → classUpdate.jsp 이동
    document.querySelectorAll('.edit-btn').forEach(btn => {
        btn.addEventListener('click', () => {
            const courseId = btn.dataset.id;
            if (!courseId) return alert('강좌 ID를 찾을 수 없습니다.');
            location.href = `/teacher/mypage/classes/update/${courseId}`;
        });
    });

    // 삭제 버튼 → 상태를 PRIVATE 으로 변경 (POST 방식)
    document.querySelectorAll('.delete-btn').forEach(btn => {
        btn.addEventListener('click', async () => {
            const courseId = btn.dataset.id;
            if (!courseId) return alert('강좌 ID를 찾을 수 없습니다.');

            if (confirm('이 클래스를 삭제 처리하시겠습니까?')) {
                try {
                    const res = await fetch(`/teacher/mypage/delete/${courseId}`, {
                        method: 'POST',
                        headers: { 'Content-Type': 'application/json' }
                    });

                    if (res.ok) {
                        alert('클래스가 삭제되었습니다.');
                        location.reload();
                    } else {
                        alert('삭제 실패');
                    }
                } catch (err) {
                    console.error(err);
                    alert('요청 중 오류 발생');
                }
            }
        });
    });
});
document.addEventListener('DOMContentLoaded', () => {
    document.querySelectorAll('.status[data-status]').forEach(el => {
        const status = el.dataset.status;
        el.classList.add(status.toLowerCase()); // public / private / pending
        el.textContent =
            status === 'PUBLIC' ? '공개'
                : status === 'PRIVATE' ? '비공개'
                    : status === 'PENDING' ? '승인대기'
                        : '-';
    });
});

