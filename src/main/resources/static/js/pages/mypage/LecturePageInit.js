// ============================
// LecturePageInit.js
// 강의 시청 페이지 초기화 스크립트
// ============================

document.addEventListener("DOMContentLoaded", () => {
    // 비디오 재생 속도 버튼 제어
    const video = document.getElementById("lectureVideo");
    window.setPlaybackRate = rate => { video.playbackRate = rate; };

    // QnA 초기화
    if (window.MulangContext && window.MulangContext.courseId) {
        QnaController.init(window.MulangContext.courseId);
    } else {
        console.warn("courseId가 정의되지 않았습니다.");
    }
});
