document.addEventListener("DOMContentLoaded", () => {
    const btn = document.querySelector(".submit-btn");
    if (!btn) return;
    btn.addEventListener("click", () => location.href = "/teacher/mypage/profile/edit");
});
