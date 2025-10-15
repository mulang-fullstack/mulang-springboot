document.addEventListener("DOMContentLoaded", function () {
    /* -------------------- CKEditor5 초기화 -------------------- */
    if (window.ClassicEditor) {
        ClassicEditor.create(document.querySelector("#editor"), {
            language: "ko",
            toolbar: [
                "undo", "redo", "|",
                "bold", "italic", "link", "blockQuote",
                "bulletedList", "numberedList", "|",
                "insertTable", "mediaEmbed"
            ],
            mediaEmbed: { previewsInData: true },
            placeholder: "클래스 소개를 입력해주세요."
        }).catch(error => console.error(error));
    } else {
        console.error("CKEditor5가 로드되지 않았습니다. CDN 스크립트를 먼저 불러오세요.");
    }

    /* -------------------- 강의 영상 추가/삭제 기능 -------------------- */
    const videoList = document.querySelector(".video-list");
    const addBtn = document.querySelector(".add-video-btn");
    let count = document.querySelectorAll(".video-item").length;
    const maxCount = 10;

    if (!videoList || !addBtn) return;

    // 추가
    addBtn.addEventListener("click", () => {
        if (count >= maxCount) {
            alert("최대 10개의 강의만 추가할 수 있습니다.");
            return;
        }

        const newItem = document.createElement("div");
        newItem.classList.add("video-item");
        newItem.innerHTML = `
            <input type="text" name="chapter_name[]" placeholder="챕터 이름을 입력하세요" class="chapter-input" required>
            <input type="file" name="videoFile[]" accept="video/mp4,video/webm,video/mov" class="video-input" required>
            <button type="button" class="remove-video-btn">－</button>
        `;
        videoList.insertBefore(newItem, addBtn);
        count++;
    });

    // 삭제 (이벤트 위임)
    videoList.addEventListener("click", (e) => {
        if (e.target.classList.contains("remove-video-btn")) {
            const item = e.target.closest(".video-item");
            if (item && videoList.querySelectorAll(".video-item").length > 1) {
                item.remove();
                count--;
            } else {
                alert("최소 1개의 강의는 있어야 합니다.");
            }
        }
    });
});
