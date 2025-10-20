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
            const lectureId = btn.dataset.id;
            if (!lectureId) return alert('강좌 ID를 찾을 수 없습니다.');
            // ✅ RESTful 경로에 맞춰 수정
            location.href = `/teacher/mypage/classes/update/${lectureId}`;
        });
    });

    // 삭제 버튼
    document.querySelectorAll('.delete-btn').forEach(btn => {
        btn.addEventListener('click', async () => {
            const lectureId = btn.dataset.id;
            if (!lectureId) return alert('강좌 ID를 찾을 수 없습니다.');

            if (confirm('정말 이 클래스를 삭제하시겠습니까?')) {
                try {
                    const res = await fetch(`/teacher/mypage/classes/${lectureId}`, {
                        method: 'DELETE',
                        headers: { 'Content-Type': 'application/json' }
                    });

                    if (res.ok) {
                        alert('삭제되었습니다.');
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
