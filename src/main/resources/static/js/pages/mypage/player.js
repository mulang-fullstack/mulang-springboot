const video = document.querySelector("#lectureVideo");
const timeDisplay = document.getElementById("currentTime");

// 🔹 탭 전환
function showTab(tabName) {
    document.querySelectorAll('.tab-btn').forEach(btn => btn.classList.remove('active'));
    document.querySelectorAll('.tab-content').forEach(tab => tab.classList.remove('active'));

    if (tabName === 'lecture') {
        document.querySelector('#lecture-tab').classList.add('active');
        document.querySelectorAll('.tab-btn')[0].classList.add('active');
    } else if (tabName === 'timestamp') {
        document.querySelector('#timestamp-tab').classList.add('active');
        document.querySelectorAll('.tab-btn')[1].classList.add('active');
    } else {
        document.querySelector('#qna-tab').classList.add('active');
        document.querySelectorAll('.tab-btn')[2].classList.add('active');
    }
}

// 🔹 영상 재생 속도 변경
function setPlaybackRate(rate) {
    video.playbackRate = rate;
}

// 🔹 영상 교체
function loadVideo(src) {
    video.src = src;
    video.play();
}

// 🔹 타임스탬프 이동
function goToTime(seconds) {
    video.currentTime = seconds;
    video.play();
}

// 🔹 현재 시간 표시
video.addEventListener("timeupdate", () => {
    const minutes = Math.floor(video.currentTime / 60);
    const seconds = Math.floor(video.currentTime % 60);
    timeDisplay.textContent =
        String(minutes).padStart(2, "0") + ":" + String(seconds).padStart(2, "0");
});
