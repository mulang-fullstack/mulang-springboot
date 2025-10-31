document.addEventListener("DOMContentLoaded", () => {
    const videoList = document.querySelector(".video-list");
    if (!videoList) return;

    // ===== [1] 새 강의 추가 버튼 =====
    videoList.addEventListener("click", e => {
        if (!e.target.classList.contains("add-video-btn")) return;

        const videoItems = videoList.querySelectorAll(".video-item");

        // 추가된 부분: 최대 10개 제한
        const maxCount = 10;
        if (videoItems.length >= maxCount) return alert("최대 10개의 챕터만 추가할 수 있습니다.");

        const newIndex = videoItems.length;
        const firstItem = videoItems[0];

        // 기존 첫 강의 복제
        const newItem = firstItem.cloneNode(true);

        // 새 강의 입력 초기화
        newItem.querySelectorAll("input").forEach(input => {
            if (input.type === "file") input.value = "";
            else input.value = "";
        });

        // 파일명 리셋
        const fileNameElem = newItem.querySelector(".file-name");
        if (fileNameElem) fileNameElem.textContent = "선택된 파일 없음";

        // 데이터 인덱스 갱신
        newItem.dataset.index = newIndex;

        //  새 강의 추가 시 삭제목록 초기화 (이전 꼬임 제거)
        const deletedContainer = document.getElementById("deletedLectureContainer");
        if (deletedContainer) deletedContainer.innerHTML = "";

        // videoList에 추가
        videoList.appendChild(newItem);
        reindexVideoItems();
    });

    // ===== [2] 강의 삭제 버튼 =====
    videoList.addEventListener("click", e => {
        if (!e.target.classList.contains("remove-video-btn")) return;

        const item = e.target.closest(".video-item");
        const index = parseInt(item.dataset.index, 10);

        //  첫 번째 강의는 삭제 금지
        if (index === 0) return;

        // 삭제 hidden input 생성
        const deletedContainer = document.getElementById("deletedLectureContainer");
        const idInput = item.querySelector("input[name*='.id']");
        if (idInput && idInput.value) { //  id가 존재할 때만 삭제 목록에 추가
            const deletedInput = document.createElement("input");
            deletedInput.type = "hidden";
            deletedInput.name = "deletedLectureIds";
            deletedInput.value = idInput.value;
            deletedContainer.appendChild(deletedInput);
        }

        // 요소 제거
        item.remove();
        reindexVideoItems();
    });

    // ===== [3] 파일 선택 시 파일명 표시 =====
    videoList.addEventListener("change", e => {
        if (!e.target.classList.contains("video-input")) return;

        const fileInput = e.target;
        const fileNameSpan = fileInput.closest(".custom-file").querySelector(".file-name");

        if (fileInput.files.length > 0) {
            fileNameSpan.textContent = fileInput.files[0].name;
        } else {
            fileNameSpan.textContent = "선택된 파일 없음";
        }
    });

    // ===== [4] 인덱스 재정렬 =====
    function reindexVideoItems() {
        videoList.querySelectorAll(".video-item").forEach((item, i) => {
            item.dataset.index = i;

            item.querySelectorAll("input, label").forEach(el => {
                if (el.name && el.name.startsWith("lectures[")) {
                    const suffix = el.name.substring(el.name.indexOf("].") + 2);
                    el.name = `lectures[${i}].${suffix}`;
                }
                if (el.id && el.id.startsWith("video_")) {
                    el.id = `video_${i}`;
                }
                if (el.htmlFor && el.htmlFor.startsWith("video_")) {
                    el.htmlFor = `video_${i}`;
                }
            });

            //  첫 번째 강의는 삭제버튼 숨김
            const removeBtn = item.querySelector(".remove-video-btn");
            if (removeBtn) removeBtn.style.display = i === 0 ? "none" : "inline-block";
        });
    }

    // 초기 정렬 적용
    reindexVideoItems();
});
