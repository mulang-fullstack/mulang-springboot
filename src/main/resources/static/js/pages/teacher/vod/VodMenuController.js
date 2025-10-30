/**
 * VOD 메뉴 컨트롤러
 * - 커리큘럼 / Q&A 패널 전환
 * - QnAController 초기화 연동
 */

document.addEventListener("DOMContentLoaded", () => {

    const menuButtons = document.querySelectorAll(".menu-btn");
    const panels = document.querySelectorAll(".panel");

    if (!menuButtons.length) return;

    /**
     * 메뉴 클릭 이벤트
     */
    menuButtons.forEach(btn => {
        btn.addEventListener("click", () => {
            // 1. 메뉴 버튼 active 전환
            menuButtons.forEach(b => b.classList.remove("active"));
            btn.classList.add("active");

            // 2. 패널 표시 전환
            const target = btn.dataset.target;
            panels.forEach(p => p.classList.remove("active"));
            const activePanel = document.getElementById(`panel-${target}`);
            if (activePanel) activePanel.classList.add("active");

            // 3. Q&A 패널 클릭 시 QnAController 초기화
            if (target === "qna" && window.QnaController && window.MulangContext) {
                // 중복 초기화 방지
                if (!activePanel.dataset.initialized) {
                    QnaController.init(window.MulangContext.courseId);
                    activePanel.dataset.initialized = "true";
                }
            }
        });
    });

    /**
     * 초기 상태: 커리큘럼 패널 활성
     */
    const defaultPanel = document.getElementById("panel-curriculum");
    if (defaultPanel) defaultPanel.classList.add("active");
});
