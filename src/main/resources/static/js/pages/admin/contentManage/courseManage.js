document.addEventListener("DOMContentLoaded", () => {
    initPagination();
    console.log("CourseManage.js loaded");
});

/* -------------------- 페이징 -------------------- */
function initPagination() {
    const pagination = document.querySelector(".pagination");
    if (!pagination) return;
    const buttons = pagination.querySelectorAll("button:not(:disabled)");
    buttons.forEach(btn => {
        btn.addEventListener("click", () => {
            buttons.forEach(b => b.classList.remove("active"));
            btn.classList.add("active");
        });
    });
}

/* -------------------- 새 강좌 등록 (테스트용) -------------------- */
function openAddModal() {
    alert("새 강좌 등록 기능은 추후 구현 예정입니다.");
}
