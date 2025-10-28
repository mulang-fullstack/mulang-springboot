/**
 * QnaView.js
 * Q&A 질문/답변 렌더링 및 클릭 이벤트 제어
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
                    <span class="writer">${q.writerName || "익명"}</span>
                    <span class="created">${new Date(q.createdAt).toLocaleString()}</span>
                </div>
                <div class="qna-question-content">${q.content}</div>
                <div class="qna-answer-container" style="display: none;"></div>
            `;

            container.appendChild(questionBox);

            questionBox.addEventListener("click", (e) => {
                //  [1] 답변 입력 영역 클릭 시 상위 토글 방지
                const ignoreClick = e.target.closest(".qna-answer-container") ||
                    e.target.classList.contains("qna-answer-submit");
                if (ignoreClick) return;

                const currentAnswerContainer = questionBox.querySelector(".qna-answer-container");
                const opened = container.querySelector(".qna-answer-container[style*='display: block']");

                //  [2] 동일 질문 다시 클릭 → 닫기
                if (currentAnswerContainer.style.display === "block") {
                    currentAnswerContainer.style.display = "none";
                    currentAnswerContainer.innerHTML = "";
                    return;
                }

                //  [3] 다른 질문 열려있으면 닫기
                if (opened && opened !== currentAnswerContainer) {
                    opened.style.display = "none";
                    opened.innerHTML = "";
                }

                //  [4] 현재 질문 열기
                QnaView.renderAnswerSection(q, currentAnswerContainer);
                currentAnswerContainer.style.display = "block";
            });
        });
    },

    renderAnswerSection(question, container) {
        const answersHtml = (question.answers || []).map(a => `
            <div class="qna-answer-box">
                <div class="qna-answer-header">
                    <span class="writer">${a.teacherName || "익명"}</span>
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

        //  [5] 답변 등록 버튼 동작
        const submitBtn = container.querySelector(".qna-answer-submit");
        const textarea = container.querySelector(".qna-answer-input");

        // 내부 클릭 시 이벤트 버블링 방지
        textarea.addEventListener("click", (e) => e.stopPropagation());
        submitBtn.addEventListener("click", async (e) => {
            e.stopPropagation(); // 상위 질문 클릭 이벤트 막기
            const content = textarea.value.trim();
            if (!content) return alert("답변 내용을 입력하세요.");
            await QnaController.handleAnswerSubmit(question.id, content);
        });
    },
};
