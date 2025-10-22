// 페이지가 완전히 로드되면 실행
document.addEventListener("DOMContentLoaded", () => {
    const videoList = document.querySelector(".video-list"); // 영상 입력 목록 영역
    const maxCount = 10; // 최대 추가 가능 개수
    const form = document.querySelector("form"); // 업로드 폼

    if (!videoList) return;

    /** -------------------- 새로운 챕터 입력 행 생성 -------------------- */
    function createVideoItem() {
        const newItem = document.createElement("div");
        newItem.classList.add("video-item");
        newItem.innerHTML = `
            <input type="text" name="lectureTitle[]" 
                   placeholder="챕터 제목을 입력하세요" class="chapter-input" required>

            <input type="file" name="lectureVideo[]" 
                   accept="video/mp4,video/webm,video/mov" class="video-input" required>

            <div class="video-btn-wrap">
                <button type="button" class="add-video-btn">＋</button>
                <button type="button" class="remove-video-btn">－</button>
            </div>
        `;
        return newItem;
    }

    /** -------------------- 기본 1행 생성 -------------------- */
    if (videoList.children.length === 0) {
        videoList.appendChild(createVideoItem());
    }

    /** -------------------- + / - 버튼 처리 -------------------- */
    videoList.addEventListener("click", (event) => {
        const target = event.target;

        // [+] 추가
        if (target.classList.contains("add-video-btn")) {
            const count = videoList.querySelectorAll(".video-item").length;
            if (count >= maxCount) {
                alert("최대 10개의 강의만 추가할 수 있습니다.");
                return;
            }
            videoList.appendChild(createVideoItem());
        }

        // [−] 삭제
        if (target.classList.contains("remove-video-btn")) {
            const items = videoList.querySelectorAll(".video-item");
            if (items.length <= 1) {
                alert("최소 1개의 강의는 있어야 합니다.");
                return;
            }
            target.closest(".video-item").remove();
        }
    });

    /** -------------------- 폼 제출 시 파일 검증 -------------------- */
    form.addEventListener("submit", (e) => {
        const fileInputs = videoList.querySelectorAll('input[type="file"][name="lectureVideo[]"]');

        // ① 비어 있는 파일 input 제거
        fileInputs.forEach(input => {
            if (!input.files || input.files.length === 0) {
                input.remove();
            }
        });

        // ② 실제 파일이 하나라도 있는지 검사
        const validFiles = Array.from(videoList.querySelectorAll('input[type="file"][name="lectureVideo[]"]'))
            .filter(input => input.files && input.files.length > 0);

        if (validFiles.length === 0) {
            e.preventDefault();
            alert("최소 1개의 강의 영상을 업로드해야 합니다.");
            return;
        }
    });
});
