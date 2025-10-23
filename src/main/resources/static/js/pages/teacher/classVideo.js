document.addEventListener("DOMContentLoaded", () => {
    const videoList = document.querySelector(".video-list");
    const maxCount = 10;
    const form = document.querySelector("form");

    if (!videoList) return;

    // -------------------- 새로운 챕터 입력 행 생성 --------------------
    function createVideoItem(index) {
        const newItem = document.createElement("div");
        newItem.classList.add("video-item");
        newItem.innerHTML = `
            <input type="text" name="lectures[${index}].title" placeholder="챕터 제목" class="chapter-input" required>
            <input type="text" name="lectures[${index}].content" placeholder="챕터 소개" class="chapter-input" required>
            <input type="file" name="lectures[${index}].video" accept="video/mp4,video/webm,video/mov" class="video-input" required>
            <div class="video-btn-wrap">
                <button type="button" class="add-video-btn">＋</button>
                <button type="button" class="remove-video-btn">－</button>
            </div>
        `;
        return newItem;
    }

    // -------------------- 기본 1행 생성 --------------------
    if (videoList.children.length === 0) {
        videoList.appendChild(createVideoItem(0)); // 인덱스 명시
    }

    // -------------------- + / - 버튼 처리 --------------------
    videoList.addEventListener("click", (event) => {
        const target = event.target;

        // [+] 추가
        if (target.classList.contains("add-video-btn")) {
            const count = videoList.querySelectorAll(".video-item").length;
            if (count >= maxCount) {
                alert("최대 10개의 강의만 추가할 수 있습니다.");
                return;
            }
            videoList.appendChild(createVideoItem(count)); // 인덱스 전달
        }

        // [−] 삭제
        if (target.classList.contains("remove-video-btn")) {
            const items = videoList.querySelectorAll(".video-item");
            if (items.length <= 1) {
                alert("최소 1개의 강의는 있어야 합니다.");
                return;
            }
            target.closest(".video-item").remove();

            // 삭제 후 인덱스 재정렬
            const allItems = videoList.querySelectorAll(".video-item");
            allItems.forEach((item, idx) => {
                item.querySelector('[name$=".title"]').name = `lectures[${idx}].title`;
                item.querySelector('[name$=".content"]').name = `lectures[${idx}].content`;
                item.querySelector('[name$=".video"]').name = `lectures[${idx}].video`;
            });
        }
    });

    // -------------------- 폼 제출 시 검증 --------------------
    form.addEventListener("submit", (e) => {
        const videoItems = videoList.querySelectorAll(".video-item");

        for (let i = 0; i < videoItems.length; i++) {
            const title = videoItems[i].querySelector('input[name$=".title"]');
            const content = videoItems[i].querySelector('input[name$=".content"]');
            const video = videoItems[i].querySelector('input[type="file"]');

            if (!title.value.trim() || !content.value.trim() || !video.files.length) {
                e.preventDefault();
                alert("모든 챕터의 제목, 내용, 영상을 입력해야 합니다.");
                return;
            }
        }
    });
});
