document.addEventListener("DOMContentLoaded", () => {
    const menuLinks = document.querySelectorAll(".menu a");
    let isLocked = false;

    menuLinks.forEach(link => {
        link.addEventListener("click", e => {
            if (isLocked) {
                e.preventDefault(); // 중복 클릭 차단
                return;
            }

            // 첫 클릭만 통과
            isLocked = true;

            // UX 피드백 (잠시 비활성화)
            link.style.pointerEvents = "none";
            link.style.opacity = "0.6";

            // 일정 시간 후 잠금 해제
            setTimeout(() => {
                isLocked = false;
                link.style.pointerEvents = "";
                link.style.opacity = "";
            }, 1000);
        });
    });
});
