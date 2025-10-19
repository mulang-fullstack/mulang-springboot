document.addEventListener("DOMContentLoaded", () => {
    initPagination();
    bindDateShortcuts();
    bindFilterEvents();
});

/* ==========================================================
   [1] 빠른 날짜 선택
   ========================================================== */
function setDateRange(type) {
    const today = new Date();
    let startDate = new Date();

    switch (type) {
        case "today":
            break;
        case "week":
            startDate.setDate(today.getDate() - 7);
            break;
        case "month":
            startDate.setMonth(today.getMonth() - 1);
            break;
        case "3months":
            startDate.setMonth(today.getMonth() - 3);
            break;
        default:
            return;
    }

    document.getElementById("startDate").value = formatDate(startDate);
    document.getElementById("endDate").value = formatDate(today);
}

/* ==========================================================
   [2] 날짜 포맷 (yyyy-MM-dd)
   ========================================================== */
function formatDate(date) {
    const y = date.getFullYear();
    const m = String(date.getMonth() + 1).padStart(2, "0");
    const d = String(date.getDate()).padStart(2, "0");
    return `${y}-${m}-${d}`;
}

/* ==========================================================
   [3] 필터 변경 이벤트 (라디오/체크박스/셀렉트)
   ========================================================== */
function bindFilterEvents() {
    const filters = document.querySelectorAll(
        "input[name='logType'], select[name='sort'], input[type='date']"
    );
    filters.forEach(el => el.addEventListener("change", submitFilterForm));
}

/* 필터 변경 시 폼 재전송 */
function submitFilterForm() {
    const form = document.querySelector(".search-form");
    if (form) form.submit();
}

/* ==========================================================
   [4] 빠른 날짜 선택 버튼 활성화
   ========================================================== */
function bindDateShortcuts() {
    const buttons = document.querySelectorAll(".quick-select button");
    buttons.forEach(btn => {
        btn.addEventListener("click", () => {
            buttons.forEach(b => b.classList.remove("active"));
            btn.classList.add("active");
        });
    });
}

/* ==========================================================
   [5] 엑셀(CSV) 다운로드
   ========================================================== */
function exportLogs() {
    const table = document.getElementById("logTable");
    if (!table) return;

    const rows = [...table.querySelectorAll("tr")];
    const csv = rows
        .map(row =>
            [...row.querySelectorAll("th,td")]
                .map(cell => `"${cell.innerText.replace(/"/g, '""')}"`)
                .join(",")
        )
        .join("\n");

    const blob = new Blob([csv], { type: "text/csv;charset=utf-8;" });
    const url = URL.createObjectURL(blob);
    const link = document.createElement("a");
    link.href = url;
    link.download = `user_log_${formatDate(new Date())}.csv`;
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
    URL.revokeObjectURL(url);
}

/* ==========================================================
   [6] 페이징 렌더링
   ========================================================== */
function initPagination() {
    const { currentPage, totalPages } = window.serverData || {};
    const container = document.getElementById("pagination");
    if (!container || totalPages <= 1) return;

    container.innerHTML = "";

    const createBtn = (label, page, disabled = false, active = false) => {
        const btn = document.createElement("button");
        btn.textContent = label;
        if (disabled) btn.disabled = true;
        if (active) btn.classList.add("active");
        btn.addEventListener("click", () => goToPage(page));
        return btn;
    };

    container.appendChild(createBtn("«", currentPage - 1, currentPage === 1));

    const maxVisible = 5;
    let start = Math.max(1, currentPage - Math.floor(maxVisible / 2));
    let end = Math.min(totalPages, start + maxVisible - 1);

    if (end - start < maxVisible - 1)
        start = Math.max(1, end - maxVisible + 1);

    for (let i = start; i <= end; i++) {
        container.appendChild(createBtn(i, i, false, i === currentPage));
    }

    container.appendChild(
        createBtn("»", currentPage + 1, currentPage === totalPages)
    );
}

/* ==========================================================
   [7] 페이지 이동
   ========================================================== */
function goToPage(page) {
    const params = new URLSearchParams(window.location.search);
    params.set("page", page);
    window.location.search = params.toString();
}

/* ==========================================================
   [8] 필터 초기화
   ========================================================== */
function resetFilters() {
    const radios = document.querySelectorAll("input[type='radio']");
    const checkboxes = document.querySelectorAll("input[type='checkbox']");
    const dates = document.querySelectorAll("input[type='date']");
    const keyword = document.querySelector("input[name='keyword']");

    radios.forEach(r => (r.checked = r.value === "ALL"));
    checkboxes.forEach(c => (c.checked = true));
    dates.forEach(d => (d.value = ""));
    if (keyword) keyword.value = "";

    submitFilterForm();
}
