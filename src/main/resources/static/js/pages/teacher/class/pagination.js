document.addEventListener("DOMContentLoaded", () => {
    const pagination = document.querySelector(".pagination");
    const tableBody = document.querySelector(".table-body");
    if (!pagination) return;

    const currentPage = parseInt(pagination.dataset.currentPage || "0", 10);
    const totalPages = parseInt(pagination.dataset.totalPages || "0", 10);

    function renderPagination(current, total) {
        pagination.innerHTML = "";

        if (total <= 1) return;

        if (current > 0)
            pagination.innerHTML += `<a href="#" data-page="${current - 1}" class="prev">«</a>`;

        for (let i = 0; i < total; i++) {
            pagination.innerHTML += `
                <a href="#" data-page="${i}" class="${i === current ? 'active' : ''}">
                    ${i + 1}
                </a>`;
        }

        if (current < total - 1)
            pagination.innerHTML += `<a href="#" data-page="${current + 1}" class="next">»</a>`;
    }

    async function loadPage(page) {
        const res = await fetch(`/teacher/mypage/classes/page?page=${page}`);
        const data = await res.json();

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

        renderPagination(data.number, data.totalPages);
    }

    pagination.addEventListener("click", e => {
        if (!e.target.matches("a")) return;
        e.preventDefault();
        const page = parseInt(e.target.dataset.page, 10);
        loadPage(page);
    });

    renderPagination(currentPage, totalPages);
});
