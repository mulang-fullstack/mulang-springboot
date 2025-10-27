document.addEventListener("DOMContentLoaded", () => {
    document.querySelectorAll(".status[data-status]").forEach(el => {
        const status = el.dataset.status;
        el.classList.add(status.toLowerCase());
        el.textContent =
            status === "PUBLIC" ? "공개"
                : status === "PRIVATE" ? "비공개"
                    : status === "PENDING" ? "승인대기"
                        : "-";
    });
});
