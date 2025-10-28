// =============================
// QnaView : 화면 렌더링 모듈
// =============================
const QnaView = (() => {
    const container = document.querySelector("#qna-list");

    function render(questions, courseId) {
        container.innerHTML = "";
        if (!questions.length) {
            container.innerHTML = "<p class='empty'>등록된 질문이 없습니다.</p>";
            return;
        }

        questions.forEach(q => {
            const qElem = document.createElement("div");
            qElem.className = "qna-item";
            qElem.innerHTML = `
        <div class="qna-question">
          <h4>${q.title}</h4>
          <p>${q.content}</p>
          <small>${q.writerName} · ${new Date(q.createdAt).toLocaleString()}</small>
          <div class="qna-actions">
            <button class="QnaEditBtn" data-id="${q.id}">수정</button>
            <button class="QnaDeleteBtn" data-id="${q.id}">삭제</button>
          </div>
        </div>
        <div class="qna-answers">
          ${q.answers.map(a => `
            <div class="qna-answer">
              <p>${a.content}</p>
              <small>${a.teacherName} · ${new Date(a.createdAt).toLocaleString()}</small>
              <div class="qna-actions">
                <button class="AnswerEditBtn" data-id="${a.id}">수정</button>
                <button class="AnswerDeleteBtn" data-id="${a.id}">삭제</button>
              </div>
            </div>
          `).join("")}
        </div>
        <div class="qna-answer-form">
          <textarea id="AnswerContent${q.id}" placeholder="답변 입력..."></textarea>
          <button class="AnswerSubmitBtn" data-qid="${q.id}">등록</button>
        </div>
      `;
            container.appendChild(qElem);
        });
    }

    function bindEvents(courseId) {
        container.addEventListener("click", async e => {
            const t = e.target;

            // 질문 삭제
            if (t.classList.contains("QnaDeleteBtn")) {
                await QnaApi.deleteQuestion(t.dataset.id);
                QnaController.load(courseId);
            }

            // 질문 수정
            if (t.classList.contains("QnaEditBtn")) {
                const id = t.dataset.id;
                const title = prompt("제목 수정:");
                const content = prompt("내용 수정:");
                await QnaApi.updateQuestion(id, { title, content });
                QnaController.load(courseId);
            }

            // 답변 삭제
            if (t.classList.contains("AnswerDeleteBtn")) {
                await QnaApi.deleteAnswer(t.dataset.id);
                QnaController.load(courseId);
            }

            // 답변 수정
            if (t.classList.contains("AnswerEditBtn")) {
                const id = t.dataset.id;
                const content = prompt("새 답변 내용:");
                await QnaApi.updateAnswer(id, { content });
                QnaController.load(courseId);
            }

            // 답변 등록
            if (t.classList.contains("AnswerSubmitBtn")) {
                const qid = t.dataset.qid;
                const content = document.querySelector(`#AnswerContent${qid}`).value.trim();
                if (!content) return alert("내용을 입력하세요.");
                await QnaApi.createAnswer({ questionId: qid, content });
                QnaController.load(courseId);
            }
        });
    }

    return { render, bindEvents };
})();
