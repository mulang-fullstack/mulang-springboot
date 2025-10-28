/**
 * QnaController.js
 * 이벤트 흐름 관리
 */
const QnaController = {
    courseId: null,
    listContainer: null,

    init(courseId) {
        this.courseId = courseId;
        this.listContainer = document.getElementById("qna-list");
        this.loadQnaList();

        // 질문 등록 버튼 이벤트
        document.getElementById("QnaSubmit").addEventListener("click", async () => {
            const content = document.getElementById("QnaContent").value.trim();
            if (!content) return alert("질문 내용을 입력하세요.");
            await QnaApi.createQuestion(this.courseId, content);
            document.getElementById("QnaContent").value = "";
            this.loadQnaList();
        });
    },

    async loadQnaList() {
        const data = await QnaApi.getQnaByCourse(this.courseId);
        QnaView.renderQuestionList(data.content || [], this.listContainer);
    },

    async handleAnswerSubmit(questionId, content) {
        await QnaApi.createAnswer(questionId, content);
        this.loadQnaList();
    },
};
