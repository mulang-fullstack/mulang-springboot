document.addEventListener("DOMContentLoaded", () => {
    const editorElement = document.querySelector("#editor");
    if (!editorElement) {
        console.error("❌ CKEditor: #editor 요소를 찾을 수 없습니다.");
        return;
    }

    if (typeof ClassicEditor === "undefined") {
        console.error("❌ CKEditor: ClassicEditor가 로드되지 않았습니다. 스크립트 순서 확인.");
        return;
    }

    ClassicEditor.create(editorElement, {
        language: "ko",
        toolbar: [
            "heading", "|",
            "bold", "italic", "underline", "link", "|",
            "bulletedList", "numberedList", "|",
            "insertTable", "mediaEmbed", "undo", "redo"
        ],
        placeholder: "클래스 소개를 입력해주세요."
    })
        .then(editor => {
            console.log("✅ CKEditor 초기화 성공:", editor);
        })
        .catch(error => {
            console.error("❌ CKEditor 초기화 실패:", error);
        });
});
