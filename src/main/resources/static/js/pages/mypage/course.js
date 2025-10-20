function openPlayer(lectureId) {
    const url = `/mypage/player?lectureId=${lectureId}`;
    const options = "width=1200,height=800,top=100,left=200,resizable=yes,scrollbars=no";
    window.open(url, "lecturePlayer", options);
}

const video = document.querySelector("video");
const timeDisplay = document.getElementById("currentTime");

// 재생속도 변경
function setPlaybackRate(rate) {
    video.playbackRate = rate;
}

// 영상 바뀌기
function loadVideo(src) {
    video.src = src;
    video.play();
}

// 현재 시청 시간 표시
video.addEventListener("timeupdate", () => {
    const minutes = Math.floor(video.currentTime / 60);
    const seconds = Math.floor(video.currentTime % 60);
    timeDisplay.textContent =
        String(minutes).padStart(2, "0") + ":" + String(seconds).padStart(2, "0");
});