/**
 * QnaView.js
 * Q&A 질문/답변 렌더링 및 클릭 이벤트 제어 + 페이지네이션
 */
const QnaView = {
    renderQuestionList(qnaList, container) {
        container.innerHTML = "";

        qnaList.forEach(q => {
            const questionBox = document.createElement("div");
            questionBox.className = "qna-question-box";
            questionBox.dataset.id = q.id;

            const editBtnHtml = q.editable ? `<button class="qna-question-edit" data-id="${q.id}">수정</button>` : "";
            const deleteBtnHtml = q.deletable ? `<button class="qna-question-delete" data-id="${q.id}">삭제</button>` : "";

            questionBox.innerHTML = `
                <div class="qna-question-header">
                    <span class="writer">${q.writerName || "익명"}</span>
                    <span class="created">${new Date(q.createdAt).toLocaleString()}</span>
                    <span class="qna-actions">${editBtnHtml}${deleteBtnHtml}</span>
                </div>
                <div class="qna-question-content">${q.content}</div>
                <div class="qna-answer-container" style="display:none;"></div>
            `;

            container.appendChild(questionBox);

            /** 질문 수정 버튼 (토글형) */
            const editBtn = questionBox.querySelector(".qna-question-edit");
            if (editBtn) {
                editBtn.addEventListener("click", async (e) => {
                    e.stopPropagation();
                    const contentElem = questionBox.querySelector(".qna-question-content");

                    if (!editBtn.classList.contains("editing")) {
                        // 편집 시작
                        const originalText = contentElem.textContent.trim();
                        contentElem.innerHTML = `<textarea class="qna-edit-textarea">${originalText}</textarea>`;
                        editBtn.classList.add("editing");
                        editBtn.textContent = "저장";
                    } else {
                        // 저장
                        const textarea = contentElem.querySelector(".qna-edit-textarea");
                        const newContent = textarea.value.trim();
                        if (!newContent) return alert("내용을 입력하세요.");
                        try {
                            await QnaApi.updateQuestion(q.id, q.title, newContent);
                            contentElem.textContent = newContent;
                            editBtn.classList.remove("editing");
                            editBtn.textContent = "수정";
                        } catch (err) {
                            console.error(err);
                            alert("질문 수정 중 오류가 발생했습니다.");
                        }
                    }
                });
            }

            /** 질문 삭제 버튼 */
            const delBtn = questionBox.querySelector(".qna-question-delete");
            if (delBtn) {
                delBtn.addEventListener("click", async (e) => {
                    e.stopPropagation();
                    if (!confirm("이 질문을 삭제하시겠습니까?")) return;
                    try {
                        await QnaApi.deleteQuestion(q.id);
                        QnaController.loadQnaList(QnaController.currentPage);
                    } catch (err) {
                        console.error(err);
                        alert("질문 삭제 중 오류가 발생했습니다.");
                    }
                });
            }

            /** 질문 클릭 시 답변 토글 */
            questionBox.addEventListener("click", (e) => {
                const ignoreClick = e.target.closest(".qna-answer-container")
                    || e.target.classList.contains("qna-answer-submit")
                    || e.target.classList.contains("qna-question-delete")
                    || e.target.classList.contains("qna-question-edit");
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

                QnaView.renderAnswerSection(q, currentContainer);
                currentContainer.style.display = "block";
            });
        });
    },

    /** 답변 렌더링 */
    renderAnswerSection(question, container) {
        const answersHtml = (question.answers || []).map(a => {
            const editBtnHtml = a.editable ? `<button class="qna-answer-edit" data-id="${a.id}">수정</button>` : "";
            const deleteBtnHtml = a.deletable ? `<button class="qna-answer-delete" data-id="${a.id}">삭제</button>` : "";
            return `
                <div class="qna-answer-box">
                    <div class="qna-answer-header">
                        <span class="writer">${a.teacherName || "익명"}</span>
                        <span class="created">${new Date(a.createdAt).toLocaleString()}</span>
                        <span class="qna-actions">${editBtnHtml}${deleteBtnHtml}</span>
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

        /** 답변 수정 버튼 (토글형) */
        container.querySelectorAll(".qna-answer-edit").forEach(btn => {
            btn.addEventListener("click", async (e) => {
                e.stopPropagation();
                const answerBox = btn.closest(".qna-answer-box");
                const contentElem = answerBox.querySelector(".qna-answer-content");

                if (!btn.classList.contains("editing")) {
                    const originalText = contentElem.textContent.trim();
                    contentElem.innerHTML = `<textarea class="qna-edit-textarea">${originalText}</textarea>`;
                    btn.classList.add("editing");
                    btn.textContent = "저장";
                } else {
                    const textarea = contentElem.querySelector(".qna-edit-textarea");
                    const newContent = textarea.value.trim();
                    if (!newContent) return alert("내용을 입력하세요.");
                    try {
                        await QnaApi.updateAnswer(btn.dataset.id, newContent);
                        contentElem.textContent = newContent;
                        btn.classList.remove("editing");
                        btn.textContent = "수정";
                    } catch (err) {
                        console.error(err);
                        alert("답변 수정 중 오류가 발생했습니다.");
                    }
                }
            });
        });

        /** 답변 삭제 버튼 */
        container.querySelectorAll(".qna-answer-delete").forEach(btn => {
            btn.addEventListener("click", async (e) => {
                e.stopPropagation();
                if (!confirm("이 답변을 삭제하시겠습니까?")) return;
                try {
                    await QnaApi.deleteAnswer(btn.dataset.id);
                    QnaController.loadQnaList(QnaController.currentPage);
                } catch (err) {
                    console.error(err);
                    alert("답변 삭제 중 오류가 발생했습니다.");
                }
            });
        });

        /** 답변 등록 */
        const submitBtn = container.querySelector(".qna-answer-submit");
        const textarea = container.querySelector(".qna-answer-input");
        textarea.addEventListener("click", (e) => e.stopPropagation());
        submitBtn.addEventListener("click", async (e) => {
            e.stopPropagation();
            const content = textarea.value.trim();
            if (!content) return alert("답변 내용을 입력하세요.");
            await QnaController.handleAnswerSubmit(question.id, content);
        });
    },

    /** 페이지네이션 렌더링 */
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
            btn.addEventListener("click", (e) => {
                const page = parseInt(e.target.dataset.page);
                if (!isNaN(page)) QnaController.loadQnaList(page);
            });
        });
    },
};
