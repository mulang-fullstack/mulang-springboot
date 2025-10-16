document.addEventListener("DOMContentLoaded", () => {
    // 에디터 DOM 존재 확인
    const editorElement = document.querySelector("#editor");
    if (!editorElement || !window.ClassicEditor) return;

    ClassicEditor.create(editorElement, {
        language: "ko",
        toolbar: [
            "undo", "redo", "|",
            "bold", "italic", "link", "blockQuote",
            "bulletedList", "numberedList", "|",
            "insertTable", "mediaEmbed"
        ],
        mediaEmbed: { previewsInData: true },
        placeholder: "클래스 소개를 입력해주세요."
    }).catch(error => console.error("CKEditor5 초기화 실패:", error));
});
