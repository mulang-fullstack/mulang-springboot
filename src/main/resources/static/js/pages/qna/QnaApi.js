// =============================
// QnaApi : REST API 통신 모듈
// =============================
const QnaApi = (() => {
    const BASE = "/api/qna";

    async function request(url, options = {}) {
        const res = await fetch(url, {
            headers: { "Content-Type": "application/json", ...(options.headers || {}) },
            ...options
        });
        if (!res.ok) throw new Error(await res.text());
        return res.status === 204 ? null : res.json();
    }

    return {
        getQnaByCourse: (courseId, page = 0, size = 10) =>
            request(`${BASE}/course/${courseId}?page=${page}&size=${size}`),

        createQuestion: body =>
            request(`${BASE}/question`, { method: "POST", body: JSON.stringify(body) }),

        createAnswer: body =>
            request(`${BASE}/answer`, { method: "POST", body: JSON.stringify(body) }),

        updateQuestion: (questionId, body) =>
            request(`${BASE}/question/${questionId}`, { method: "PUT", body: JSON.stringify(body) }),

        updateAnswer: (answerId, body) =>
            request(`${BASE}/answer/${answerId}`, { method: "PUT", body: JSON.stringify(body) }),

        deleteQuestion: questionId =>
            request(`${BASE}/question/${questionId}`, { method: "DELETE" }),

        deleteAnswer: answerId =>
            request(`${BASE}/answer/${answerId}`, { method: "DELETE" })
    };
})();
