document.addEventListener("DOMContentLoaded", () => {
    const form =
        document.querySelector("form[action='/teacher/mypage/classes']") ||
        document.querySelector("form[action='/teacher/mypage/classes/update']");
    if (!form) return;

    form.addEventListener("submit", e => {
        form.dataset.validationFailed = "false";

        const title = form.querySelector("input[name='title']");
        const subtitle = form.querySelector("input[name='subtitle']");
        const content = document.querySelector("#summernote")?.value || "";

        if (!title.value.trim()) {
            alert("클래스 제목을 입력하세요.");
            e.preventDefault();
            form.dataset.validationFailed = "true";
            return;
        }
        if (title.value.trim().length > 12) {
            alert("클래스 제목은 최대 12자까지 입력 가능합니다.");
            e.preventDefault();
            form.dataset.validationFailed = "true";
            return;
        }
        if (!subtitle.value.trim()) {
            alert("부제목을 입력하세요.");
            e.preventDefault();
            form.dataset.validationFailed = "true";
            return;
        }
        if (subtitle.value.trim().length > 12) {
            alert("부제목은 최대 12자까지 입력 가능합니다.");
            e.preventDefault();
            form.dataset.validationFailed = "true";
            return;
        }
        if (!content.trim()) {
            alert("강의 소개 내용을 입력하세요.");
            e.preventDefault();
            form.dataset.validationFailed = "true";
            return;
        }

        const lectures = form.querySelectorAll(".video-item");
        for (let i = 0; i < lectures.length; i++) {
            const lecTitle = lectures[i].querySelector("input[name*='.title']");
            const lecContent = lectures[i].querySelector("input[name*='.content']");
            if (!lecTitle || !lecContent) continue;

            const lecTitleVal = lecTitle.value.trim();
            const lecContentVal = lecContent.value.trim();

            if (!lecTitleVal) {
                alert(`강의 ${i + 1}의 제목을 입력하세요.`);
                e.preventDefault();
                form.dataset.validationFailed = "true";
                return;
            }
            if (lecTitleVal.length > 20) {
                alert(`강의 ${i + 1}의 제목은 최대 20자까지 입력 가능합니다.`);
                e.preventDefault();
                form.dataset.validationFailed = "true";
                return;
            }
            if (!lecContentVal) {
                alert(`강의 ${i + 1}의 내용을 입력하세요.`);
                e.preventDefault();
                form.dataset.validationFailed = "true";
                return;
            }
            if (lecContentVal.length > 20) {
                alert(`강의 ${i + 1}의 내용은 최대 20자까지 입력 가능합니다.`);
                e.preventDefault();
                form.dataset.validationFailed = "true";
                return;
            }
        }
    });
});
