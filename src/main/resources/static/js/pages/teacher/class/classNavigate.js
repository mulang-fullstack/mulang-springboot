document.addEventListener("DOMContentLoaded", function() {

    // 문서 전체에 클릭 이벤트 위임
    document.addEventListener("click", function(e) {
        const el = e.target.closest(".title-wrap");
        if (!el) return;

        const courseId = el.dataset.id;
        if (!courseId) {
            console.warn("courseId 누락됨:", el);
            return;
        }

        window.location.href = `/player/${courseId}`;
    });

});
