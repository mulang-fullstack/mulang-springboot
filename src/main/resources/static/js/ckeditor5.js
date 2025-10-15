
document.addEventListener("DOMContentLoaded", function () {
    if (!window.ClassicEditor) {
        console.error("CKEditor5가 로드되지 않았습니다. CDN 스크립트를 먼저 불러오세요.");
        return;
    }

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
});
