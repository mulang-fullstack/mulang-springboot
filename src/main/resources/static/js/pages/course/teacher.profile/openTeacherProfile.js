document.addEventListener("DOMContentLoaded", () => {
    const btn = document.getElementById("teacherBtn");
    const modal = document.getElementById("teacherModal");
    const modalBody = document.getElementById("modalBody");
    const closeBtn = modal.querySelector(".close");

    btn.addEventListener("click", () => {
        const courseId = btn.dataset.courseId;

        fetch(`/courseDetail/teacherProfile?id=${courseId}`)
            .then(response => response.text())
            .then(html => {
                modalBody.innerHTML = html; // 모달 내용 삽입
                modal.style.display = "block"; // 모달 열기
            })
            .catch(err => console.error(err));
    });

    closeBtn.addEventListener("click", () => {
        modal.style.display = "none";
    });

    window.addEventListener("click", (event) => {
        if(event.target === modal){
            modal.style.display = "none";
        }
    });
});