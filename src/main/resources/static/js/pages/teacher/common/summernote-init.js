$(document).ready(function () {
    const note = $("#summernote");

    if (!note.length) return;

    note.summernote({
        height: 300,
        lang: "ko-KR",
        placeholder: "강의 소개를 입력하세요. (이미지 업로드 가능)",
        toolbar: [
            ["style", ["bold", "italic", "underline"]],
            ["para", ["ul", "ol"]],
            ["insert", ["picture"]],
        ],
        callbacks: {
            onImageUpload: async function (files) {
                const file = files[0];
                if (!file) return;

                const formData = new FormData();
                formData.append("upload", file);

                try {
                    const token = document.querySelector('meta[name="_csrf"]')?.content || "";
                    const header = document.querySelector('meta[name="_csrf_header"]')?.content || "";

                    const res = await fetch("/api/editor/upload", {
                        method: "POST",
                        headers: header ? { [header]: token } : {},
                        body: formData
                    });

                    const result = await res.json();
                    note.summernote("insertImage", result.url);
                } catch (err) {
                    console.error("이미지 업로드 오류:", err);
                    alert("이미지 업로드 실패");
                }
            }
        }
    });
});
