let playerWindow = null;

function openPlayer(courseId) {
    const url = `/student/course/${courseId}/play`;
    const options = "width=1200,height=800,top=100,left=200,resizable=yes,scrollbars=no";

    // 이미 열린 창이 있으면 포커스
    if (playerWindow && !playerWindow.closed) {
        playerWindow.focus();
        playerWindow.location.href = url;
    } else {
        playerWindow = window.open(url, "lecturePlayer", options);
    }
}

document.addEventListener("DOMContentLoaded", () => {
    const video = document.querySelector("video");
    const timeDisplay = document.getElementById("currentTime");

    // 재생속도 변경
    window.setPlaybackRate = (rate) => {
        if (!video) return;
        video.playbackRate = rate;
    };

    // 영상 바꾸기
    window.loadVideo = (src) => {
        if (!video) return;
        video.src = src;
        video.play();
    };

    // 현재 시청 시간 표시
    if (video && timeDisplay) {
        video.addEventListener("timeupdate", () => {
            const minutes = Math.floor(video.currentTime / 60);
            const seconds = Math.floor(video.currentTime % 60);
            timeDisplay.textContent =
                String(minutes).padStart(2, "0") + ":" + String(seconds).padStart(2, "0");
        });
    }
});

// ⭐ 이것만 추가
function filterBySubject() {
    const languageId = document.getElementById('subjectFilter').value;
    const keyword = document.getElementById('searchInput').value;

    const urlParams = new URLSearchParams();

    if (languageId) {
        urlParams.set('languageId', languageId);
    }

    if (keyword) {
        urlParams.set('keyword', keyword);
    }

    window.location.href = `/student/course?${urlParams.toString()}`;
}

// 검색 함수
function searchCourse(event) {
    event.preventDefault();  // 폼 기본 제출 방지

    const keyword = document.getElementById('searchInput').value.trim();
    const languageId = document.getElementById('subjectFilter').value;

    if (!keyword) {
        alert('검색어를 입력해주세요.');
        return false;
    }

    const urlParams = new URLSearchParams();

    if (keyword) {
        urlParams.set('keyword', keyword);
    }

    if (languageId) {
        urlParams.set('languageId', languageId);
    }

    urlParams.set('page', '0');
    window.location.href = `/student/course?${urlParams.toString()}`;

    return false;
}
