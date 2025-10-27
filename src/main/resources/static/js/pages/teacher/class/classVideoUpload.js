document.addEventListener("DOMContentLoaded", () => {
    const videoList = document.querySelector(".video-list");
    const maxCount = 10;
    if (!videoList) return;

    function createVideoItem(index) {
        const div = document.createElement("div");
        div.classList.add("video-item");
        div.dataset.index = index;
        div.innerHTML = `
            <input type="text" name="lectures[${index}].title" placeholder="챕터 제목" required class="chapter-input">
            <input type="text" name="lectures[${index}].content" placeholder="챕터 소개" required class="chapter-input">
            <div class="custom-file">
                <label for="video_${index}" class="file-label">
                    <span class="file-button">파일 선택</span>
                    <span class="file-name">선택된 파일 없음</span>
                </label>
                <input type="file"
                       id="video_${index}"
                       name="lectures[${index}].video"
                       accept="video/mp4,video/webm,video/mov"
                       class="video-input"
                       required>
            </div>
            <div class="video-btn-wrap">
                <button type="button" class="add-video-btn">＋</button>
                <button type="button" class="remove-video-btn">－</button>
            </div>`;
        return div;
    }

    videoList.addEventListener("click", e => {
        const target = e.target;

        if (target.classList.contains("add-video-btn")) {
            const count = videoList.querySelectorAll(".video-item").length;
            if (count >= maxCount) return alert("최대 10개의 챕터만 추가할 수 있습니다.");
            const newItem = createVideoItem(count);
            target.closest(".video-item").after(newItem);
            reindex();
        }

        if (target.classList.contains("remove-video-btn")) {
            const items = videoList.querySelectorAll(".video-item");
            if (items.length <= 1) return alert("최소 1개의 챕터는 필요합니다.");
            target.closest(".video-item").remove();
            reindex();
        }
    });

    videoList.addEventListener("change", e => {
        if (e.target.matches(".video-input")) {
            const name = e.target.files[0]?.name || "선택된 파일 없음";
            e.target.closest(".custom-file").querySelector(".file-name").textContent = name;
        }
    });

    function reindex() {
        videoList.querySelectorAll(".video-item").forEach((item, i) => {
            item.dataset.index = i;
            item.querySelectorAll("input, label").forEach(el => {
                if (el.name?.startsWith("lectures[")) {
                    const suffix = el.name.substring(el.name.indexOf("].") + 2);
                    el.name = `lectures[${i}].${suffix}`;
                }
                if (el.id?.startsWith("video_")) el.id = `video_${i}`;
                if (el.htmlFor?.startsWith("video_")) el.htmlFor = `video_${i}`;
            });
        });
    }
});

