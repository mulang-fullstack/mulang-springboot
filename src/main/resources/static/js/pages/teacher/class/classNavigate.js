document.addEventListener("DOMContentLoaded", function() {

    // 모든 클래스 제목 영역(title-wrap)에 클릭 이벤트 연결
    const titleWrapList = document.querySelectorAll(".title-wrap");

    titleWrapList.forEach(function(el) {
        // 클릭 시 이동
        el.addEventListener("click", function() {
            const courseId = el.dataset.id;

            if (!courseId) {
                console.warn("courseId 누락됨:", el);
                return;
            }

            // 새 창으로 이동하려면 window.open, 같은 탭이면 location.href
            window.location.href = `/teacher/mypage/course/${courseId}`;
        });

        // 커서 모양 변경
        el.style.cursor = "pointer";
    });

});
