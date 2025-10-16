document.addEventListener("DOMContentLoaded", () => {
    const videoList = document.querySelector(".video-list");
    const maxCount = 10;
    if (!videoList) return;

    // -------------------- 챕터 행 생성 함수 --------------------
    function createVideoItem() {
        const item = document.createElement("div");
        item.classList.add("video-item");
        item.innerHTML = `
            <input type="text" name="chapter_name[]" placeholder="챕터 이름을 입력하세요" class="chapter-input" required>
            <input type="file" name="videoFile[]" accept="video/mp4,video/webm,video/mov" class="video-input" required>
            <div class="video-btn-wrap">
                <button type="button" class="add-video-btn">＋</button>
                <button type="button" class="remove-video-btn">－</button>
            </div>
        `;
        return item;
    }

    // -------------------- 기본 1행 생성 --------------------
    if (videoList.children.length === 0) {
        videoList.appendChild(createVideoItem());
    }

    // -------------------- 이벤트 위임 --------------------
    videoList.addEventListener("click", (e) => {
        const target = e.target;

        // 챕터 추가
        if (target.classList.contains("add-video-btn")) {
            const currentItem = target.closest(".video-item");
            const hint = videoList.querySelector(".hint");
            const count = videoList.querySelectorAll(".video-item").length;
            if (count >= maxCount) {
                alert("최대 10개의 강의만 추가할 수 있습니다.");
                return;
            }

            const newItem = createVideoItem();
            newItem.style.opacity = "0";
            videoList.insertBefore(newItem, hint || currentItem.nextSibling);

            requestAnimationFrame(() => {
                newItem.style.transition = "opacity 0.25s ease";
                newItem.style.opacity = "1";
            });
        }

        // 챕터 삭제
        if (target.classList.contains("remove-video-btn")) {
            const items = videoList.querySelectorAll(".video-item");
            if (items.length <= 1) {
                alert("최소 1개의 강의는 있어야 합니다.");
                return;
            }

            const item = target.closest(".video-item");
            item.style.transition = "opacity 0.2s ease";
            item.style.opacity = "0";
            setTimeout(() => item.remove(), 200);
        }
    });
});
