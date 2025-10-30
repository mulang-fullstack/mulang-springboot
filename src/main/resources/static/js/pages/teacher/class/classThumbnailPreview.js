document.addEventListener("DOMContentLoaded", () => {
    const fileInput = document.querySelector("input[name='thumbnailFile']");
    if (!fileInput) return;

    // 기존 썸네일 이미지 태그
    const img = document.querySelector(".field-content img");

    // 새 파일 선택 시 미리보기
    fileInput.addEventListener("change", e => {
        const file = e.target.files[0];
        if (!file) return;

        const reader = new FileReader();
        reader.onload = ev => {
            if (img) {
                img.src = ev.target.result;
            } else {
                const preview = document.createElement("img");
                preview.src = ev.target.result;
                preview.width = 120;
                fileInput.closest(".field-content").appendChild(preview);
            }
        };
        reader.readAsDataURL(file);
    });
});
