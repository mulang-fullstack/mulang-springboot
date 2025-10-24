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
document.addEventListener("DOMContentLoaded", () => {
    const tableBody = document.querySelector(".table-body");
    const pagination = document.querySelector(".pagination");

    pagination.addEventListener("click", async (e) => {
        if (!e.target.matches("a")) return;
        e.preventDefault();

        const page = e.target.getAttribute("data-page");
        const res = await fetch(`/teacher/mypage/classes/page?page=${page}`);
        const data = await res.json();

        // 테이블 내용 갱신
        tableBody.innerHTML = data.content.map(course => `
            <div class="table-row">
                <div class="thumb">
                    <img src="${course.thumbnail}" alt="썸네일" width="160" height="90">
                </div>
                <div class="title-wrap">
                    <div class="title">${course.title}</div>
                    <div class="subtitle">${course.subtitle}</div>
                </div>
                <div class="category">${course.category} / ${course.language}</div>
                <div class="menu-wrap">
                    <button class="menu-btn">⋯</button>
                    <div class="menu-dropdown">
                        <button class="menu-item edit-btn" data-id="${course.id}">수정</button>
                        <button class="menu-item delete-btn" data-id="${course.id}">삭제</button>
                    </div>
                </div>
            </div>
        `).join("");

        // 현재 페이지 표시 갱신
        const currentPage = data.number;
        const totalPages = data.totalPages;
        pagination.innerHTML = Array.from({ length: totalPages }, (_, i) => `
            <a href="#" data-page="${i}" class="${i === currentPage ? 'active' : ''}">
                ${i + 1}
            </a>
        `).join("");
    });
});

