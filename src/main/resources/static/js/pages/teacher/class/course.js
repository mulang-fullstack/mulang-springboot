document.addEventListener("click", e => {
    // 수정 버튼
    if (e.target.classList.contains("edit-btn")) {
        const id = e.target.dataset.id;
        if (!id) return alert("강좌 ID를 찾을 수 없습니다.");
        location.href = `/teacher/mypage/classes/update/${id}`;
        return;
    }

    //  반려 사유 보기 버튼
    if (e.target.classList.contains("reason-btn")) {
        e.preventDefault();
        const reason = e.target.dataset.reason || "";
        showRejectionReasonPopup(reason);
    }
});

function showRejectionReasonPopup(reason) {
    // 혹시 기존 팝업이 있으면 제거
    const existing = document.querySelector(".rejection-popup");
    if (existing) existing.remove();

    // 팝업 DOM 구성
    const popup = document.createElement("div");
    popup.className = "rejection-popup";
    popup.innerHTML = `
        <div class="popup-content">
            <h3>심사 반려 사유</h3>
            <p>${reason.trim() !== "" ? reason : "관리자 반려 사유가 없습니다."}</p>
            <button class="close-popup-btn">닫기</button>
        </div>
    `;

    document.body.appendChild(popup);

    // 닫기
    popup.querySelector(".close-popup-btn").addEventListener("click", () => {
        popup.remove();
    });
}
