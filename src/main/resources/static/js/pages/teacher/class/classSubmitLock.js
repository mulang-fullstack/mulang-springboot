document.addEventListener("DOMContentLoaded", () => {
    const form = document.querySelector("form[action='/teacher/mypage/classes']");
    const submitBtn = document.querySelector(".submit-btn");

    if (!form || !submitBtn) return;

    form.addEventListener("submit", e => {
        // 중복 클릭 차단
        if (submitBtn.disabled) {
            e.preventDefault();
            return;
        }

        // 버튼 비활성화 + 문구 변경
        submitBtn.disabled = true;
        submitBtn.textContent = "저장 중...";
        submitBtn.style.opacity = "0.7";
        submitBtn.style.cursor = "not-allowed";

        // 시각적 로딩 표시 추가 (선택사항)
        const spinner = document.createElement("span");
        spinner.className = "spinner";
        spinner.style.marginLeft = "8px";
        spinner.innerHTML = "⏳";
        submitBtn.appendChild(spinner);

        // 5초 후 자동 해제 (혹시 서버 응답이 느릴 때 대비)
        setTimeout(() => {
            submitBtn.disabled = false;
            submitBtn.textContent = "저장하기";
            submitBtn.style.opacity = "";
            submitBtn.style.cursor = "";
            if (spinner && spinner.parentNode) spinner.parentNode.removeChild(spinner);
        }, 5000);
    });
});
