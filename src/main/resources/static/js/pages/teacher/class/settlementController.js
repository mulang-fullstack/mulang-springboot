/**
 * 교사 판매현황 데이터 로드 컨트롤러
 * settlement.jsp 전용
 */
document.addEventListener("DOMContentLoaded", () => {
    const SalesController = {
        currentPage: 0,
        totalPages: 0,

        /**
         * 판매현황 비동기 데이터 로드
         * @param {number} page - 요청할 페이지 번호
         */
        async loadSalesList(page = 0) {
            try {
                const response = await fetch(`/teacher/mypage/settlement/page?page=${page}`, {
                    headers: { "X-Requested-With": "XMLHttpRequest" }
                });
                if (!response.ok) throw new Error("요청 실패");

                const data = await response.json();
                this.currentPage = data.number;
                this.totalPages = data.totalPages;

                const tbody = document.querySelector(".table-body");
                tbody.innerHTML = "";

                if (data.content.length === 0) {
                    tbody.innerHTML = `<div class="empty">데이터가 없습니다.</div>`;
                } else {
                    data.content.forEach(sale => {
                        tbody.innerHTML += `
                            <div class="row">
                                <span class="course-title">${sale.courseTitle}</span>
                                <span class="course-amount">${sale.totalAmount.toLocaleString()}원</span>
                            </div>`;
                    });
                }

                // 데이터 로드 완료 이벤트 발생 → 페이지네이션 렌더 트리거
                document.dispatchEvent(new Event("salesDataLoaded"));
            } catch (err) {
                console.error("판매현황 로드 오류:", err);
            }
        }
    };

    window.SalesController = SalesController;
    SalesController.loadSalesList(0);
});
