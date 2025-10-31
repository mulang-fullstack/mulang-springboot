document.addEventListener("DOMContentLoaded", () => {
    const form =
        document.querySelector("form[action='/teacher/mypage/classes']") ||
        document.querySelector("form[action='/teacher/mypage/classes/update']");
    const submitBtn = document.querySelector(".submit-btn");
    if (!form || !submitBtn) return;

    const overlay = document.createElement("div");
    overlay.className = "loading-overlay";
    overlay.innerHTML = `
        <div class="loading-spinner"></div>
        <p>클래스를 저장 중입니다. 잠시만 기다려주세요...</p>
    `;
    overlay.style.display = "none";
    document.body.appendChild(overlay);

    form.addEventListener("submit", e => {
        // 검증 실패 시 종료
        if (form.dataset.validationFailed === "true") return;

        if (submitBtn.disabled) {
            e.preventDefault();
            return;
        }

        submitBtn.disabled = true;
        submitBtn.textContent = "저장 중...";
        submitBtn.style.opacity = "0.7";
        submitBtn.style.cursor = "not-allowed";

        overlay.style.display = "flex";
        document.body.style.pointerEvents = "none";
        overlay.style.pointerEvents = "auto";
    });
});
