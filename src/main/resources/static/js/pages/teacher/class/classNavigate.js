document.addEventListener("DOMContentLoaded", function() {

    // 모든 클래스 제목 영역(title-wrap)에 클릭 이벤트 연결
    const titleWrapList = document.querySelectorAll(".title-wrap");

    titleWrapList.forEach(function(el) {
        el.addEventListener("click", function() {
            const courseId = el.dataset.id;
            if (!courseId) {
                console.warn("courseId 누락됨:", el);
                return;
            }
            // 통합된 공용 URL로 이동
            window.location.href = `/course/${courseId}/vod`;
        });
        // 커서 모양 변경
        el.style.cursor = "pointer";
    });

});
