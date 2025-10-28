/**
 * QnaApi.js
 * REST API 통신 담당 (컨트롤러 매핑 일치)
 */
const QnaApi = {
    /** 강좌별 Q&A 조회 */
    async getQnaByCourse(courseId, page = 0, size = 10) {
        const response = await fetch(`/api/qna/course/${courseId}?page=${page}&size=${size}`);
        if (!response.ok) throw new Error("질문 목록을 불러올 수 없습니다.");
        return response.json();
    },

    /** 질문 등록 */
    async createQuestion(courseId, title, content) {
        const response = await fetch(`/api/qna/question`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ courseId, title, content }),
        });
        if (!response.ok) throw new Error("질문 등록 실패");
    },

    /** 답변 등록 */
    async createAnswer(questionId, content) {
        const response = await fetch(`/api/qna/answer`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ questionId, content }),
        });
        if (!response.ok) throw new Error("답변 등록 실패");
    },

    /** 질문 수정 */
    async updateQuestion(questionId, title, content) {
        const response = await fetch(`/api/qna/question/${questionId}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ title, content }),
        });
        if (!response.ok) throw new Error("질문 수정 실패");
    },

    /** 질문 삭제 */
    async deleteQuestion(questionId) {
        const response = await fetch(`/api/qna/question/${questionId}`, { method: "DELETE" });
        if (!response.ok) throw new Error("질문 삭제 실패");
    },

    /** 답변 수정 */
    async updateAnswer(answerId, content) {
        const response = await fetch(`/api/qna/answer/${answerId}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ content }),
        });
        if (!response.ok) throw new Error("답변 수정 실패");
    },

    /** 답변 삭제 */
    async deleteAnswer(answerId) {
        const response = await fetch(`/api/qna/answer/${answerId}`, { method: "DELETE" });
        if (!response.ok) throw new Error("답변 삭제 실패");
    },
};
