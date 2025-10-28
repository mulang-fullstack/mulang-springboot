/**
 * QnaController.js
 * 이벤트 흐름 관리
 */
const QnaController = {
    courseId: null,
    listContainer: null,
    qnaList: [],

    init(courseId) {
        this.courseId = courseId;
        this.listContainer = document.getElementById("qna-list");
        this.loadQnaList();

        // 질문 등록 버튼 이벤트
        document.getElementById("QnaSubmit").addEventListener("click", async () => {
            const title = document.getElementById("QnaTitle").value.trim();
            const content = document.getElementById("QnaContent").value.trim();

            if (!title) return alert("질문 제목을 입력하세요.");
            if (!content) return alert("질문 내용을 입력하세요.");

            await QnaApi.createQuestion(this.courseId, title, content);

            // 입력창 초기화
            document.getElementById("QnaTitle").value = "";
            document.getElementById("QnaContent").value = "";

            // 목록 새로고침
            this.loadQnaList();
        });
    },

    /** QnA 목록 불러오기 */
    async loadQnaList() {
        const data = await QnaApi.getQnaByCourse(this.courseId);
        this.qnaList = data.content || data; // PageImpl 대응
        QnaView.renderQuestionList(this.qnaList, this.listContainer);
    },

    /** 답변 등록 처리 */
    async handleAnswerSubmit(questionId, content) {
        try {
            // 새 답변 등록 요청
            const response = await QnaApi.createAnswer(questionId, content);

            // 기존 질문 데이터 찾기
            const question = this.qnaList.find(q => q.id === questionId);
            if (question) {
                question.answers = question.answers || [];
                question.answers.push(response);
            }

            // 열린 질문 박스만 갱신 (닫히지 않음)
            const questionBox = document.querySelector(`.qna-question-box[data-id="${questionId}"]`);
            if (questionBox) {
                const container = questionBox.querySelector(".qna-answer-container");
                QnaView.renderAnswerSection(question, container);
            }

        } catch (err) {
            console.error(err);
            alert("답변 등록 중 오류가 발생했습니다.");
        }
    },
};

/* ===============================
   질문 작성 폼 토글 버튼 제어
================================= */
document.getElementById("QnaWriteBtn").addEventListener("click", () => {
    const form = document.getElementById("qna-write-form");
    form.style.display = form.style.display === "none" ? "block" : "none";
});

document.getElementById("QnaCancel").addEventListener("click", () => {
    document.getElementById("qna-write-form").style.display = "none";
});
