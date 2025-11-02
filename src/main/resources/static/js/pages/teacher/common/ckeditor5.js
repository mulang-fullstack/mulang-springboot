document.addEventListener("DOMContentLoaded", () => {
    const editorElement = document.querySelector("#editor");
    if (!editorElement) {
        console.error("CKEditor: #editor 요소를 찾을 수 없습니다.");
        return;
    }

    if (typeof ClassicEditor === "undefined") {
        console.error(" CKEditor: ClassicEditor가 로드되지 않았습니다. 스크립트 순서 확인.");
        return;
    }

    ClassicEditor.create(editorElement, {
        language: "ko",
        toolbar: [
            "heading", "|",
            "uploadImage", "|",
            "undo", "redo"
        ],
        simpleUpload: {
            uploadUrl: "/api/editor/upload",  // 이미지 업로드 엔드포인트
            headers: {
                "X-CSRF-TOKEN": document.querySelector('meta[name="_csrf"]')?.content || ""
            }
        },
        placeholder: "강의 소개를 입력하세요. (이미지 업로드 가능)"
    })
        .then(editor => {
            console.log("CKEditor 준비 완료:", editor);
        })
        .catch(error => {
            console.error("CKEditor 초기화 실패:", error);
        });
});
