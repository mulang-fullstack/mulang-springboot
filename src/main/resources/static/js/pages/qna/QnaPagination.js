/**
 * QnA 게시판 페이지네이션 스크립트
 * player.jsp 내 Q&A 목록 전용
 */
document.addEventListener("DOMContentLoaded", () => {
    const pagination = document.querySelector(".qna-pagination");
    if (!pagination) return;

    // 초기 상태
    let currentPage = 0;
    let totalPages = 0;

    // 페이지네이션 렌더링
    function renderPagination(current, total) {
        pagination.innerHTML = "";
        if (total <= 1) return;

        // 이전 버튼
        if (current > 0) {
            pagination.innerHTML += `
                <button class="qna-page-btn prev" data-page="${current - 1}">«</button>
            `;
        }

        // 페이지 번호 버튼
        for (let i = 0; i < total; i++) {
            pagination.innerHTML += `
                <button class="qna-page-btn ${i === current ? 'active' : ''}" data-page="${i}">
                    ${i + 1}
                </button>`;
        }

        // 다음 버튼
        if (current < total - 1) {
            pagination.innerHTML += `
                <button class="qna-page-btn next" data-page="${current + 1}">»</button>
            `;
        }
    }

    // Q&A 목록 로드
    async function loadQnaPage(page) {
        try {
            await QnaController.loadQnaList(page);
            currentPage = QnaController.currentPage;
            totalPages = QnaController.totalPages;
            renderPagination(currentPage, totalPages);
        } catch (err) {
            console.error("Q&A 페이지 로드 실패:", err);
        }
    }

    // 페이지 클릭 이벤트
    pagination.addEventListener("click", (e) => {
        if (!e.target.matches("button.qna-page-btn")) return;
        e.preventDefault();
        const page = parseInt(e.target.dataset.page, 10);
        if (!isNaN(page)) loadQnaPage(page);
    });

    // 최초 렌더링 (QnAController에서 초기 페이지 정보 세팅 후 호출)
    setTimeout(() => {
        if (typeof QnaController !== "undefined" && QnaController.totalPages) {
            currentPage = QnaController.currentPage || 0;
            totalPages = QnaController.totalPages || 0;
            renderPagination(currentPage, totalPages);
        }
    }, 500);
});
