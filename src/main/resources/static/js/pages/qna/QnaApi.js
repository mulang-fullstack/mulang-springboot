/**
 * QnaApi.js
 * player/{courseId}/api/qna 기반 통신
 */
const QnaApi = {
    /** 강좌별 Q&A 조회 */
    async getQnaByCourse(courseId, page = 0, size = 5) {
        const response = await fetch(`/player/${courseId}/api/qna?page=${page}&size=${size}`);
        if (!response.ok) throw new Error("질문 목록을 불러올 수 없습니다.");
        return response.json();
    },

    /** 질문 등록 */
    async createQuestion(courseId, title, content) {
        const response = await fetch(`/player/${courseId}/api/qna/question`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ courseId, title, content }),
        });
        if (!response.ok) throw new Error("질문 등록 실패");
        return response;
    },

    /** 답변 등록 */
    async createAnswer(courseId, questionId, content) {
        const response = await fetch(`/player/${courseId}/api/qna/answer`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ questionId, content }),
        });
        if (!response.ok) throw new Error("답변 등록 실패");
        return response;
    },

    /** 질문 수정 */
    async updateQuestion(courseId, questionId, title, content) {
        const response = await fetch(`/player/${courseId}/api/qna/question/${questionId}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ title, content }),
        });
        if (!response.ok) throw new Error("질문 수정 실패");
        return response;
    },

    /** 질문 삭제 */
    async deleteQuestion(courseId, questionId) {
        const response = await fetch(`/player/${courseId}/api/qna/question/${questionId}`, {
            method: "DELETE",
        });
        if (!response.ok) throw new Error("질문 삭제 실패");
        return response;
    },

    /** 답변 수정 */
    async updateAnswer(courseId, answerId, content) {
        const response = await fetch(`/player/${courseId}/api/qna/answer/${answerId}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ content }),
        });
        if (!response.ok) throw new Error("답변 수정 실패");
        return response;
    },

    /** 답변 삭제 */
    async deleteAnswer(courseId, answerId) {
        const response = await fetch(`/player/${courseId}/api/qna/answer/${answerId}`, {
            method: "DELETE",
        });
        if (!response.ok) throw new Error("답변 삭제 실패");
        return response;
    },
};
