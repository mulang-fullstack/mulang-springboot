/**
 * QnaController.js
 * Q&A 게시판 전체 제어 (등록, 수정, 삭제, 페이징 포함)
 */
const QnaController = {
    courseId: null,
    listContainer: null,
    qnaList: [],
    currentPage: 0,
    totalPages: 0,

    /** 초기화 */
    init(courseId) {
        this.courseId = courseId;
        this.listContainer = document.getElementById("qna-list");
        this.loadQnaList();

        // 버튼이 DOM에 늦게 생겨도 감지하여 이벤트 연결
        const waitForButton = (id, callback) => {
            const btn = document.getElementById(id);
            if (btn) return callback(btn);

            const observer = new MutationObserver(() => {
                const el = document.getElementById(id);
                if (el) {
                    callback(el);
                    observer.disconnect();
                }
            });
            observer.observe(document.body, { childList: true, subtree: true });
        };

        // 질문 등록 버튼 이벤트 연결
        waitForButton("QnaSubmit", (btn) => {
            btn.addEventListener("click", async () => {
                const title = document.getElementById("QnaTitle").value.trim();
                const content = document.getElementById("QnaContent").value.trim();

                if (!title) return alert("질문 제목을 입력하세요.");
                if (!content) return alert("질문 내용을 입력하세요.");

                try {
                    await QnaApi.createQuestion(this.courseId, title, content);
                    document.getElementById("QnaTitle").value = "";
                    document.getElementById("QnaContent").value = "";
                    await this.loadQnaList(this.currentPage);
                } catch (err) {
                    console.error(err);
                    alert("질문 등록 중 오류가 발생했습니다.");
                }
            });
        });
    },

    /** QnA 목록 불러오기 (페이징 포함) */
    async loadQnaList(page = 0) {
        try {
            const data = await QnaApi.getQnaByCourse(this.courseId, page);
            this.qnaList = data.content || [];
            this.currentPage = data.page || 0;
            this.totalPages = data.totalPages || 1;

            QnaView.renderQuestionList(this.qnaList, this.listContainer);
            QnaView.renderPagination(this.currentPage, this.totalPages);
        } catch (err) {
            console.error(err);
            alert("Q&A 목록을 불러오지 못했습니다.");
        }
    },

    /** 답변 등록 처리 */
    async handleAnswerSubmit(questionId, content) {
        try {
            await QnaApi.createAnswer(this.courseId, questionId, content);
            const questionBox = document.querySelector(`.qna-question-box[data-id="${questionId}"]`);
            const container = questionBox?.querySelector(".qna-answer-container");
            if (container) {
                const data = await QnaApi.getQnaByCourse(this.courseId, this.currentPage);
                const updatedQ = data.content.find(q => q.id === questionId);
                if (updatedQ) {
                    QnaView.renderAnswerSection(updatedQ, container);
                    container.style.display = "block";
                }
            }
        } catch (err) {
            console.error(err);
            alert("답변 등록 중 오류가 발생했습니다.");
        }
    },
};

/* ===============================
   질문 작성 폼 토글
================================= */
document.getElementById("QnaWriteBtn").addEventListener("click", () => {
    const form = document.getElementById("qna-write-form");
    form.style.display = form.style.display === "none" ? "block" : "none";
});

document.getElementById("QnaCancel").addEventListener("click", () => {
    document.getElementById("qna-write-form").style.display = "none";
});
document.addEventListener("DOMContentLoaded", () => {
    if (window.MulangContext && window.MulangContext.courseId) {
        QnaController.init(window.MulangContext.courseId);
    }
});
