/**
 * QnaView.js
 * Q&A 질문/답변 렌더링 및 클릭 이벤트 제어 + 페이지네이션
 */
const QnaView = {
    /** 시간 간소화 표시 */
    formatDateTime(dateStr) {
        const date = new Date(dateStr);
        return date.toLocaleString("ko-KR", {
            year: "2-digit",
            month: "2-digit",
            day: "2-digit"
        });
    },

    /** 질문 리스트 렌더링 */
    renderQuestionList(qnaList, container) {
        container.innerHTML = "";
        qnaList.forEach(q => {
            const questionBox = document.createElement("div");
            questionBox.className = "qna-question-box";
            questionBox.dataset.id = q.id;

            const actionMenu = `
                <div class="qna-actions">
                    <button class="qna-more-btn">⋯</button>
                    <div class="qna-action-menu" style="display:none;">
                        ${q.editable ? `<button class="qna-question-edit" data-id="${q.id}">수정</button>` : ""}
                        ${q.deletable ? `<button class="qna-question-delete" data-id="${q.id}">삭제</button>` : ""}
                    </div>
                </div>
            `;

            questionBox.innerHTML = `
                <div class="qna-question-header">
                    <span class="writer">${q.writerName || "익명"}</span>
                    <span class="created">${this.formatDateTime(q.createdAt)}</span>
                    ${actionMenu}
                </div>
                <div class="qna-question-content">${q.content}</div>
                <div class="qna-answer-container" style="display:none;"></div>
            `;
            container.appendChild(questionBox);

            /* ⋯ 버튼 토글 */
            const moreBtn = questionBox.querySelector(".qna-more-btn");
            const menu = questionBox.querySelector(".qna-action-menu");
            if (moreBtn) {
                moreBtn.addEventListener("click", e => {
                    e.stopPropagation();
                    const isOpen = menu.style.display === "block";
                    document.querySelectorAll(".qna-action-menu").forEach(m => (m.style.display = "none"));
                    menu.style.display = isOpen ? "none" : "block";
                });
            }

            /* 외부 클릭 시 메뉴 닫기 */
            document.addEventListener("click", () => {
                document.querySelectorAll(".qna-action-menu").forEach(m => (m.style.display = "none"));
            });

            /* 수정 */
            const editBtn = questionBox.querySelector(".qna-question-edit");
            if (editBtn) {
                editBtn.addEventListener("click", async e => {
                    e.stopPropagation();
                    const contentElem = questionBox.querySelector(".qna-question-content");
                    if (!editBtn.classList.contains("editing")) {
                        const originalText = contentElem.textContent.trim();
                        contentElem.innerHTML = `<textarea class="qna-edit-textarea">${originalText}</textarea>`;
                        editBtn.classList.add("editing");
                        editBtn.textContent = "저장";
                    } else {
                        const newContent = contentElem.querySelector(".qna-edit-textarea").value.trim();
                        if (!newContent) return alert("내용을 입력하세요.");
                        await QnaApi.updateQuestion(QnaController.courseId, q.id, q.title, newContent);
                        contentElem.textContent = newContent;
                        editBtn.classList.remove("editing");
                        editBtn.textContent = "수정";
                    }
                });
            }

            /* 삭제 */
            const delBtn = questionBox.querySelector(".qna-question-delete");
            if (delBtn) {
                delBtn.addEventListener("click", async e => {
                    e.stopPropagation();
                    if (!confirm("이 질문을 삭제하시겠습니까?")) return;
                    await QnaApi.deleteQuestion(QnaController.courseId, q.id);
                    QnaController.loadQnaList(QnaController.currentPage);
                });
            }

            /* 질문 클릭 → 답변 로드 */
            questionBox.addEventListener("click", async e => {
                const ignoreClick =
                    e.target.closest(".qna-answer-container") ||
                    e.target.closest(".qna-action-menu") ||
                    e.target.classList.contains("qna-more-btn");
                if (ignoreClick) return;

                const currentContainer = questionBox.querySelector(".qna-answer-container");
                const opened = container.querySelector(".qna-answer-container[style*='display: block']");
                if (currentContainer.style.display === "block") {
                    currentContainer.style.display = "none";
                    currentContainer.innerHTML = "";
                    return;
                }
                if (opened && opened !== currentContainer) {
                    opened.style.display = "none";
                    opened.innerHTML = "";
                }

                // 서버에서 최신 데이터 다시 조회
                const data = await QnaApi.getQnaByCourse(QnaController.courseId, QnaController.currentPage);
                const updatedQ = data.content.find(item => item.id === q.id);
                QnaView.renderAnswerSection(updatedQ, currentContainer);
                currentContainer.style.display = "block";
            });
        });
    },

    /** 답변 렌더링 */
    renderAnswerSection(question, container) {
        const answersHtml = (question.answers || []).map(a => {
            return `
                <div class="qna-answer-box">
                    <div class="qna-answer-header">
                        <span class="writer">${a.teacherName || "익명"}</span>
                        <span class="created">${QnaView.formatDateTime(a.createdAt)}</span>
                        ${(a.editable || a.deletable) ? `
                          <div class="qna-actions">
                              <button class="qna-more-btn">⋯</button>
                              <div class="qna-action-menu" style="display:none;">
                                  ${a.editable ? `<button class="qna-answer-edit" data-id="${a.id}">수정</button>` : ""}
                                  ${a.deletable ? `<button class="qna-answer-delete" data-id="${a.id}">삭제</button>` : ""}
                              </div>
                          </div>` : ""}
                    </div>
                    <div class="qna-answer-content">${a.content}</div>
                </div>
            `;
        }).join("");

        container.innerHTML = `
            <div class="qna-answer-list">
                ${answersHtml || "<p class='no-answer'>아직 답변이 없습니다.</p>"}
            </div>
            <div class="qna-answer-form">
                <textarea class="qna-answer-input" placeholder="답변을 입력하세요."></textarea>
                <button class="qna-answer-submit">등록</button>
            </div>
        `;

        /* ⋯ 메뉴 */
        container.querySelectorAll(".qna-more-btn").forEach(btn => {
            btn.addEventListener("click", e => {
                e.stopPropagation();
                const menu = btn.nextElementSibling;
                const isOpen = menu.style.display === "block";
                document.querySelectorAll(".qna-action-menu").forEach(m => (m.style.display = "none"));
                menu.style.display = isOpen ? "none" : "block";
            });
        });

        /* 수정 */
        container.querySelectorAll(".qna-answer-edit").forEach(btn => {
            btn.addEventListener("click", async e => {
                e.stopPropagation();
                const answerBox = btn.closest(".qna-answer-box");
                const contentElem = answerBox.querySelector(".qna-answer-content");
                if (!btn.classList.contains("editing")) {
                    const original = contentElem.textContent.trim();
                    contentElem.innerHTML = `<textarea class="qna-edit-textarea">${original}</textarea>`;
                    btn.classList.add("editing");
                    btn.textContent = "저장";
                } else {
                    const newContent = contentElem.querySelector(".qna-edit-textarea").value.trim();
                    if (!newContent) return alert("내용을 입력하세요.");
                    await QnaApi.updateAnswer(QnaController.courseId, btn.dataset.id, newContent);
                    contentElem.textContent = newContent;
                    btn.classList.remove("editing");
                    btn.textContent = "수정";
                }
            });
        });

        /* 삭제 */
        container.querySelectorAll(".qna-answer-delete").forEach(btn => {
            btn.addEventListener("click", async e => {
                e.stopPropagation();
                if (!confirm("이 답변을 삭제하시겠습니까?")) return;
                await QnaApi.deleteAnswer(QnaController.courseId, btn.dataset.id);
                QnaController.loadQnaList(QnaController.currentPage);
            });
        });

        /* 답변 등록 */
        const submitBtn = container.querySelector(".qna-answer-submit");
        const textarea = container.querySelector(".qna-answer-input");
        textarea.addEventListener("click", e => e.stopPropagation());
        submitBtn.addEventListener("click", async e => {
            e.stopPropagation();
            const content = textarea.value.trim();
            if (!content) return alert("답변 내용을 입력하세요.");
            await QnaController.handleAnswerSubmit(question.id, content);
        });
    },

    /** 페이지네이션 */
    renderPagination(currentPage, totalPages) {
        const container = document.querySelector(".qna-pagination");
        if (!container) return;
        container.innerHTML = "";

        if (totalPages <= 1) return;

        const prevDisabled = currentPage === 0 ? "disabled" : "";
        const nextDisabled = currentPage >= totalPages - 1 ? "disabled" : "";

        const prevBtn = `<button class="qna-page-btn" data-page="${currentPage - 1}" ${prevDisabled}>이전</button>`;
        const nextBtn = `<button class="qna-page-btn" data-page="${currentPage + 1}" ${nextDisabled}>다음</button>`;

        let numberBtns = "";
        for (let i = 0; i < totalPages; i++) {
            const active = i === currentPage ? "active" : "";
            numberBtns += `<button class="qna-page-btn ${active}" data-page="${i}">${i + 1}</button>`;
        }

        container.innerHTML = `${prevBtn}${numberBtns}${nextBtn}`;
        container.querySelectorAll(".qna-page-btn").forEach(btn => {
            btn.addEventListener("click", e => {
                const page = parseInt(e.target.dataset.page);
                if (!isNaN(page)) QnaController.loadQnaList(page);
            });
        });
    },
};
