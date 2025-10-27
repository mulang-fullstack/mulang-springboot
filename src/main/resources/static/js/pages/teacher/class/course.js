document.addEventListener("click", async e => {
    // 수정 버튼
    if (e.target.classList.contains("edit-btn")) {
        const id = e.target.dataset.id;
        if (!id) return alert("강좌 ID를 찾을 수 없습니다.");
        location.href = `/teacher/mypage/classes/update/${id}`;
    }

    // 삭제 버튼
    if (e.target.classList.contains("delete-btn")) {
        const id = e.target.dataset.id;
        if (!id) return alert("강좌 ID를 찾을 수 없습니다.");

        if (!confirm("이 클래스를 삭제 처리하시겠습니까?")) return;
        try {
            const res = await fetch(`/teacher/mypage/delete/${id}`, { method: "POST" });
            if (res.ok) {
                alert("클래스가 삭제되었습니다.");
                location.reload();
            } else {
                alert("삭제 실패");
            }
        } catch (err) {
            console.error(err);
            alert("요청 중 오류 발생");
        }
    }
});
