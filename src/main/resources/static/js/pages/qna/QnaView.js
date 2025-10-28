/**
 * QnaView.js
 * DOM 렌더링 및 이벤트 제어
 */
const QnaView = {
    renderQuestionList(qnaList, container) {
        container.innerHTML = "";

        qnaList.forEach(q => {
            const questionBox = document.createElement("div");
            questionBox.className = "qna-question-box";
            questionBox.dataset.id = q.id;

            questionBox.innerHTML = `
                <div class="qna-question-header">
                    <span class="writer">${q.writerNickname || "익명"}</span>
                    <span class="created">${new Date(q.createdAt).toLocaleString()}</span>
                </div>
                <div class="qna-question-content">${q.content}</div>
                <div class="qna-answer-container" style="display: none;"></div>
            `;

            container.appendChild(questionBox);

            // 질문 클릭 시 답변창 토글
            questionBox.addEventListener("click", () => {
                const answerContainer = questionBox.querySelector(".qna-answer-container");
                if (answerContainer.style.display === "none") {
                    QnaView.renderAnswerSection(q, answerContainer);
                    answerContainer.style.display = "block";
                } else {
                    answerContainer.style.display = "none";
                }
            });
        });
    },

    renderAnswerSection(question, container) {
        const answersHtml = question.answers.map(a => `
            <div class="qna-answer-box">
                <div class="qna-answer-header">
                    <span class="writer">${a.writerNickname || "익명"}</span>
                    <span class="created">${new Date(a.createdAt).toLocaleString()}</span>
                </div>
                <div class="qna-answer-content">${a.content}</div>
            </div>
        `).join("");

        container.innerHTML = `
            <div class="qna-answer-list">
                ${answersHtml || "<p class='no-answer'>아직 답변이 없습니다.</p>"}
            </div>
            <div class="qna-answer-form">
                <textarea class="qna-answer-input" placeholder="답변을 입력하세요."></textarea>
                <button class="qna-answer-submit">등록</button>
            </div>
        `;

        const submitBtn = container.querySelector(".qna-answer-submit");
        const textarea = container.querySelector(".qna-answer-input");

        submitBtn.addEventListener("click", async (e) => {
            e.stopPropagation();
            const content = textarea.value.trim();
            if (!content) return alert("답변 내용을 입력하세요.");
            await QnaController.handleAnswerSubmit(question.id, content);
        });
    },
};
