document.querySelectorAll(".heart-icon").forEach(icon => {
    icon.addEventListener("click", function() {
        const courseId = this.dataset.courseId;
        const img = this.querySelector("img");

        fetch("/courseFavorite", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded",
                "X-CSRF-TOKEN": document.querySelector('meta[name="_csrf"]').content
            },
            body: new URLSearchParams({ courseId: courseId })
        })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    // 찜 상태 토글
                    img.src = img.src.includes("heart-empty")
                        ? "/img/icon/heart-full.svg"
                        : "/img/icon/heart-empty.svg";
                } else if (data.loginUrl) {
                    // 로그인 필요 시 리다이렉트
                    window.location.href = data.loginUrl;
                } else {
                    alert(data.message);
                }
            })
            .catch(err => console.error("찜 요청 실패:", err));
    });
});