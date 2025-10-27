document.addEventListener("DOMContentLoaded", () => {
    const videoList = document.querySelector(".video-list");
    const maxCount = 10;
    const form = document.querySelector("form");
    if (!videoList) return;

    // -------------------- 삭제 ID 기록용 --------------------
    function markLectureDeleted(lectureId) {
        if (!lectureId) return;
        const hidden = document.createElement("input");
        hidden.type = "hidden";
        hidden.name = "deletedLectureIds";
        hidden.value = lectureId;
        form.appendChild(hidden);
    }

    // -------------------- 새로운 비디오 항목 생성 --------------------
    function createVideoItem(index) {
        const newItem = document.createElement("div");
        newItem.classList.add("video-item");
        newItem.dataset.index = index;
        newItem.innerHTML = `
            <input type="hidden" name="lectures[${index}].id" value="">
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
            </div>
        `;
        return newItem;
    }

    // -------------------- 기본 1개 챕터 보장 --------------------
    if (videoList.children.length === 0) {
        videoList.appendChild(createVideoItem(0));
    }

    // -------------------- 이벤트 위임 --------------------
    videoList.addEventListener("click", (e) => {
        const target = e.target;

        // 추가
        if (target.classList.contains("add-video-btn")) {
            const items = videoList.querySelectorAll(".video-item");
            if (items.length >= maxCount) return alert("최대 10개의 강의만 추가 가능합니다.");

            const currentItem = target.closest(".video-item");
            const newItem = createVideoItem(items.length);
            currentItem.after(newItem);
            reindexVideoItems();
        }

        // 삭제
        if (target.classList.contains("remove-video-btn")) {
            const items = videoList.querySelectorAll(".video-item");
            if (items.length <= 1) return alert("최소 1개의 강의는 필요합니다.");

            const item = target.closest(".video-item");
            const idInput = item.querySelector('input[name$=".id"]');
            const lectureId = idInput ? idInput.value : null;

            // 삭제 ID 기록
            markLectureDeleted(lectureId);

            item.remove();
            reindexVideoItems();
        }
    });

    // -------------------- 파일 선택 시 파일명 표시 --------------------
    videoList.addEventListener("change", (e) => {
        if (e.target.matches(".video-input")) {
            const fileNameSpan = e.target.closest(".custom-file").querySelector(".file-name");
            fileNameSpan.textContent = e.target.files[0]?.name || "선택된 파일 없음";
        }
    });

    // -------------------- 인덱스 재정렬 --------------------
    function reindexVideoItems() {
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

    // -------------------- 제출 검증 --------------------
    form.addEventListener("submit", (e) => {
        const items = videoList.querySelectorAll(".video-item");
        for (let i = 0; i < items.length; i++) {
            const t = items[i].querySelector('input[name$=".title"]');
            const c = items[i].querySelector('input[name$=".content"]');
            const v = items[i].querySelector('input[type="file"]');
            const existingFileName = items[i].querySelector(".file-name")?.textContent.trim();

            if (!t.value.trim() || !c.value.trim() ||
                (!v.files.length && (!existingFileName || existingFileName === "선택된 파일 없음"))) {
                e.preventDefault();
                alert("모든 챕터의 제목, 소개, 영상을 입력해야 합니다.");
                return;
            }
        }
    });
});

// -------------------- 이미지 교체 --------------------
document.addEventListener("DOMContentLoaded", () => {
    const replaceBtn = document.querySelector("#replaceImageBtn");
    const replaceInput = document.querySelector("#replaceImageInput");

    replaceBtn?.addEventListener("click", () => replaceInput.click());

    replaceInput?.addEventListener("change", async (e) => {
        const file = e.target.files[0];
        if (!file) return;

        const oldUrl = prompt("교체할 기존 이미지의 URL을 입력하세요:");
        if (!oldUrl) return;

        const courseId = document.querySelector("#courseId")?.value;
        if (!courseId) {
            alert("courseId를 찾을 수 없습니다.");
            return;
        }

        const formData = new FormData();
        formData.append("courseId", courseId);
        formData.append("oldUrl", oldUrl);
        formData.append("file", file);

        try {
            const res = await fetch("/teacher/mypage/classes/image/update", {
                method: "POST",
                body: formData,
            });

            const msg = await res.text();
            if (res.ok) {
                alert("이미지 교체 완료");
                location.reload();
            } else {
                alert("실패: " + msg);
            }
        } catch (err) {
            console.error("업로드 오류:", err);
            alert("업로드 중 오류가 발생했습니다.");
        }
    });
});
