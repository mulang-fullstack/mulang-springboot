// 🎬 HTML에 있는 <video> 요소와 현재 시간을 표시할 요소(span)를 선택합니다.
const video = document.querySelector("#lectureVideo");
const timeDisplay = document.getElementById("currentTime");

/* ============================================================
   🔹 탭 전환 기능
   - 탭 버튼 클릭 시 해당 탭만 보이도록 처리합니다.
   - class="active" 를 붙였다 떼는 방식으로 화면 전환.
============================================================ */
function showTab(tabName) {
    // 모든 탭 버튼과 탭 콘텐츠의 active 클래스 제거
    document.querySelectorAll('.tab-btn').forEach(btn => btn.classList.remove('active'));
    document.querySelectorAll('.tab-content').forEach(tab => tab.classList.remove('active'));

    // 전달받은 탭 이름에 따라 해당 요소에 active 클래스 추가
    if (tabName === 'lecture') {
        document.querySelector('#lecture-tab').classList.add('active');
        document.querySelectorAll('.tab-btn')[0].classList.add('active');
    } else if (tabName === 'timestamp') {
        document.querySelector('#timestamp-tab').classList.add('active');
        document.querySelectorAll('.tab-btn')[1].classList.add('active');
    } else if (tabName === 'qna') {
        document.querySelector('#qna-tab').classList.add('active');
        document.querySelectorAll('.tab-btn')[2].classList.add('active');
    }
}

/* ============================================================
     영상 재생 속도 변경
   - 버튼 클릭 시 1.0x / 1.5x / 2.0x 등 원하는 배속으로 설정.
============================================================ */
function setPlaybackRate(rate) {
    if (!video) return; // video 요소가 없으면 종료
    video.playbackRate = rate;
}

/* ============================================================
      영상 교체
   - 강의 목록에서 다른 영상을 클릭하면 호출됩니다.
   - src(주소)를 새로 설정하고 자동으로 재생.
============================================================ */
function loadVideo(src) {
    if (!video) return;
    video.src = src;
    video.play();
}

/* ============================================================
     특정 타임스탬프로 이동
   - 초 단위로 재생 위치를 바로 변경합니다.
============================================================ */
function goToTime(seconds) {
    if (!video) return;
    video.currentTime = seconds;
    video.play();
}

/* ============================================================
   🔹 10초 단위 이동 (앞뒤로)
   - skip(-10) → 10초 뒤로
   - skip(10)  → 10초 앞으로
============================================================ */
function skip(seconds) {
    if (!video) return;
    // 최소값은 0, 최대값은 전체 길이(video.duration)
    video.currentTime = Math.max(0, Math.min(video.duration, video.currentTime + seconds));
}

/* ============================================================
   🔹 현재 시간 표시
   - 영상이 재생될 때마다 timeupdate 이벤트가 발생.
   - 00:00 형태로 현재 재생 시간을 갱신합니다.
============================================================ */
if (video) {
    video.addEventListener("timeupdate", () => {
        const minutes = Math.floor(video.currentTime / 60); // 분 계산
        const seconds = Math.floor(video.currentTime % 60); // 초 계산
        // 항상 2자리 숫자로 표시 (예: 03:07)
        timeDisplay.textContent =
            String(minutes).padStart(2, "0") + ":" + String(seconds).padStart(2, "0");
    });
}

/* ============================================================
   🔹 키보드 단축키
   - ← : 10초 뒤로
   - → : 10초 앞으로
   - Space : 재생 / 일시정지
   ※ keydown에서는 기본 동작(스크롤 등)을 막고
     keyup에서 실제 재생/일시정지를 처리하여
     중복 반응 문제를 방지합니다.
============================================================ */

// 방향키 처리 (keydown 시 바로 반응)
document.addEventListener("keydown", (e) => {
    if (!video) return;

    // 스페이스 기본 스크롤 방지
    if (e.key === " ") e.preventDefault();

    switch (e.key) {
        case "ArrowLeft":  // ← 10초 뒤로
            skip(-10);
            break;
        case "ArrowRight": // → 10초 앞으로
            skip(10);
            break;
    }
});

// 스페이스바 재생/일시정지는 keyup 시 한 번만 실행
document.addEventListener("keyup", (e) => {
    if (!video) return;
    if (e.key === " ") {
        e.preventDefault();
        e.stopPropagation();
        // 재생 중이면 일시정지, 일시정지 중이면 재생
        if (video.paused) video.play();
        else video.pause();
    }
});
//  영상 비율 자동 감지
video.addEventListener("loadedmetadata", () => {
    // 영상의 실제 가로(width)와 세로(height) 비율 비교
    if (video.videoHeight > video.videoWidth) {
        // 세로 영상이면 vertical 클래스 적용
        video.classList.add("vertical");
        video.classList.remove("horizontal");
    } else {
        // 가로 영상이면 horizontal 클래스 적용
        video.classList.add("horizontal");
        video.classList.remove("vertical");
    }
});

