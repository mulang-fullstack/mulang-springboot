/**
 * 교사 판매현황 페이지네이션 스크립트
 * settlement.jsp 전용
 */
document.addEventListener("DOMContentLoaded", () => {
    const pagination = document.querySelector(".sales-pagination");
    if (!pagination) return;

    function renderPagination(current, total) {
        pagination.innerHTML = "";
        if (total <= 1) return;

        if (current > 0) {
            pagination.innerHTML += `
                <button class="sales-page-btn prev" data-page="${current - 1}">«</button>`;
        }

        for (let i = 0; i < total; i++) {
            pagination.innerHTML += `
                <button class="sales-page-btn ${i === current ? "active" : ""}" data-page="${i}">
                    ${i + 1}
                </button>`;
        }

        if (current < total - 1) {
            pagination.innerHTML += `
                <button class="sales-page-btn next" data-page="${current + 1}">»</button>`;
        }
    }

    async function loadSalesPage(page) {
        try {
            await SalesController.loadSalesList(page);
            renderPagination(SalesController.currentPage, SalesController.totalPages);
        } catch (err) {
            console.error("판매현황 페이지 로드 실패:", err);
        }
    }

    pagination.addEventListener("click", (e) => {
        if (!e.target.matches("button.sales-page-btn")) return;
        e.preventDefault();
        const page = parseInt(e.target.dataset.page, 10);
        if (!isNaN(page)) loadSalesPage(page);
    });

    // 데이터 로드 완료 시 페이지네이션 렌더링
    document.addEventListener("salesDataLoaded", () => {
        renderPagination(SalesController.currentPage, SalesController.totalPages);
    });
});
