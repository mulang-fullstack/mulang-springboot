/**
 * QnA 전용 AJAX 페이징 스크립트
 * - 질문 목록 불러오기
 * - 페이징 버튼 렌더링
 * - 페이지 이동 이벤트 처리
 */
document.addEventListener("DOMContentLoaded", () => {
    const qnaSection = document.querySelector(".qna-section");
    if (!qnaSection) return;

    const courseId = qnaSection.dataset.courseId;
    const qnaList = document.getElementById("qna-list");
    const pagination = document.querySelector(".pagination");
    const writeBox = document.querySelector("#qna-content");
    const submitBtn = document.querySelector("#qna-submit");

    let currentPage = 0;
    const pageSize = 5;

    /**  페이징 버튼 렌더링 */
    function renderPagination(current, total) {
        pagination.innerHTML = "";

        if (total <= 1) return;

        // 이전
        if (current > 0)
            pagination.innerHTML += `<a href="#" data-page="${current - 1}" class="prev">«</a>`;

        // 페이지 번호
        for (let i = 0; i < total; i++) {
            pagination.innerHTML += `
                <a href="#" data-page="${i}" class="${i === current ? 'active' : ''}">
                    ${i + 1}
                </a>`;
        }

        // 다음
        if (current < total - 1)
            pagination.innerHTML += `<a href="#" data-page="${current + 1}" class="next">»</a>`;
    }

    /** 질문 리스트 로드 */
    async function loadPage(page = 0) {
        try {
            const res = await fetch(`/api/qna/questions?courseId=${courseId}&page=${page}&size=${pageSize}`);
            if (!res.ok) throw new Error("질문 목록을 불러올 수 없습니다.");
            const data = await res.json();

            // 리스트 렌더링
            qnaList.innerHTML = data.content.map(q =>
                `<div class="qna-item">
                    <div class="question">${q.title}</div>
                    <div class="content">${q.content}</div>
                    ${q.answers && q.answers.length > 0
                    ? q.answers.map(a => `<div class="answer"> ${a.content}</div>`).join('')
                    : ''}
                </div>`
            ).join("");

            renderPagination(data.number, data.totalPages);
            currentPage = data.number;
        } catch (e) {
            console.error(e);
        }
    }

    /**  페이지 클릭 이벤트 */
    pagination.addEventListener("click", e => {
        if (!e.target.matches("a")) return;
        e.preventDefault();
        const page = parseInt(e.target.dataset.page, 10);
        loadPage(page);
    });

    /** 질문 등록 */
    submitBtn?.addEventListener("click", async () => {
        const content = writeBox.value.trim();
        if (!content) return alert("내용을 입력하세요.");

        try {
            await fetch("/api/qna/questions", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ courseId, content })
            });
            writeBox.value = "";
            loadPage(0); // 새로고침
        } catch (e) {
            console.error(e);
        }
    });

    /**  초기 로드 */
    loadPage(0);
});
