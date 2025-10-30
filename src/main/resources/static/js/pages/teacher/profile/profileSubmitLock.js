document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("editForm");
    const submitBtn = document.querySelector(".submit-btn");

    if (!form || !submitBtn) return;

    form.addEventListener("submit", e => {
        // 이미 잠겨 있으면 중복 제출 차단
        if (submitBtn.disabled) {
            e.preventDefault();
            return;
        }

        // 버튼 잠금 + 시각적 효과
        submitBtn.disabled = true;
        submitBtn.textContent = "수정 중..."; // 표시 문구 변경
        submitBtn.style.opacity = "0.7";
        submitBtn.style.cursor = "not-allowed";

        // 일정 시간 후 자동 해제 (예: 3초)
        // 서버가 빠르게 응답하면 새 페이지가 로드되어 자동 해제됨
        setTimeout(() => {
            submitBtn.disabled = false;
            submitBtn.textContent = "수정 완료";
            submitBtn.style.opacity = "";
            submitBtn.style.cursor = "";
        }, 3000);
    });
});
