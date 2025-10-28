// =============================
// QnaController : 초기화 및 제어 모듈
// =============================
const QnaController = (() => {
    async function load(courseId) {
        try {
            const data = await QnaApi.getQnaByCourse(courseId);
            QnaView.render(data.content, courseId);
        } catch (err) {
            console.error(err);
            alert("Q&A 데이터를 불러오지 못했습니다.");
        }
    }

    function init(courseId) {
        QnaView.bindEvents(courseId);
        load(courseId);

        const submitBtn = document.querySelector("#QnaSubmit");
        if (submitBtn) {
            submitBtn.addEventListener("click", async () => {
                const title = document.querySelector("#QnaTitle").value.trim();
                const content = document.querySelector("#QnaContent").value.trim();
                if (!title || !content) return alert("모든 필드를 입력하세요.");

                await QnaApi.createQuestion({ courseId, title, content });
                document.querySelector("#QnaTitle").value = "";
                document.querySelector("#QnaContent").value = "";
                load(courseId);
            });
        }
    }

    return { init, load };
})();
