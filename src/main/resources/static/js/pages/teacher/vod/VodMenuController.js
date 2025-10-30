document.addEventListener("DOMContentLoaded", () => {
    const menuButtons = document.querySelectorAll(".menu-btn");
    const vodPanel = document.querySelector(".vod-panel");
    const curriculumPanel = document.getElementById("panel-curriculum");
    const qnaPanel = document.getElementById("panel-qna");

    if (!vodPanel || !curriculumPanel || !qnaPanel) return;

    /** 패널 닫기 */
    const closePanel = () => {
        vodPanel.classList.remove("slide-open");
        curriculumPanel.style.display = "none";
        qnaPanel.style.display = "none";
    };

    /** 패널 열기 */
    const openPanel = (target) => {
        // 모든 패널 숨김
        curriculumPanel.style.display = "none";
        qnaPanel.style.display = "none";

        // 선택된 패널만 표시
        if (target === "curriculum") curriculumPanel.style.display = "block";
        if (target === "qna") qnaPanel.style.display = "block";

        // QnA 초기화 (한 번만 실행)
        if (target === "qna" && window.QnaController && window.MulangContext) {
            if (!qnaPanel.dataset.initialized) {
                QnaController.init(window.MulangContext.courseId);
                qnaPanel.dataset.initialized = "true";
            }
        }

        // 슬라이드 오픈
        vodPanel.classList.add("slide-open");
    };

    /** 메뉴 클릭 이벤트 */
    menuButtons.forEach((btn) => {
        btn.addEventListener("click", () => {
            const target = btn.dataset.target;
            const isActive = btn.classList.contains("active");

            // 동일 탭 다시 클릭 → 닫기
            if (isActive) {
                btn.classList.remove("active");
                closePanel();
                return;
            }

            // 다른 탭 클릭 → 교체
            menuButtons.forEach((b) => b.classList.remove("active"));
            btn.classList.add("active");
            openPanel(target);
        });
    });

    // 초기 상태는 닫힘
    closePanel();
});
