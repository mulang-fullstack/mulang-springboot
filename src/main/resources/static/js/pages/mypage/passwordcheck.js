// 페이지 로드 시 비밀번호 필드 초기화
document.addEventListener('DOMContentLoaded', function() {
    // 모든 비밀번호 입력 필드 초기화
    document.querySelectorAll('input[type="password"]').forEach(input => {
        input.value = '';
    });
});

// 뒤로가기 대응
window.addEventListener('pageshow', function(event) {
    if (event.persisted || (window.performance && window.performance.navigation.type === 2)) {
        // 뒤로가기로 돌아온 경우 비밀번호 필드 초기화
        document.querySelectorAll('input[type="password"]').forEach(input => {
            input.value = '';
        });
    }
});

// 수정 버튼 클릭 시 모달 열기
document.querySelector('.submit-btn').addEventListener('click', function (e) {
    e.preventDefault();
    // 모달 열기 전에 비밀번호 필드 초기화
    const passwordInput = document.querySelector('#passwordCheckForm input[type="password"]');
    if (passwordInput) passwordInput.value = '';
    document.getElementById('passwordCheckModal').style.display = 'flex';
});

// 비밀번호 변경 버튼 클릭 시 모달 열기
document.querySelector('.passwordsubmit-btn').addEventListener('click', function (e) {
    e.preventDefault();
    // 모달 열기 전에 비밀번호 필드 초기화
    const passwordInput = document.querySelector('#passwordCheckFormpc input[type="password"]');
    if (passwordInput) passwordInput.value = '';
    document.getElementById('passwordCheckModalpc').style.display = 'flex';
});

// 모달 닫기 함수
function closePasswordModalor() {
    document.getElementById('passwordCheckModal').style.display = 'none';
    // 모달 닫을 때도 비밀번호 필드 초기화
    const passwordInput = document.querySelector('#passwordCheckForm input[type="password"]');
    if (passwordInput) passwordInput.value = '';
}

// 모달 닫기 함수
function closePasswordModal() {
    document.getElementById('passwordCheckModalpc').style.display = 'none';
    // 모달 닫을 때도 비밀번호 필드 초기화
    const passwordInput = document.querySelector('#passwordCheckFormpc input[type="password"]');
    if (passwordInput) passwordInput.value = '';
}

// 폼 제출 후 비밀번호 필드 초기화
const passwordCheckForm = document.getElementById('passwordCheckForm');
if (passwordCheckForm) {
    passwordCheckForm.addEventListener('submit', function() {
        setTimeout(() => {
            const passwordInput = this.querySelector('input[type="password"]');
            if (passwordInput) passwordInput.value = '';
        }, 100);
    });
}

const passwordCheckFormPc = document.getElementById('passwordCheckFormpc');
if (passwordCheckFormPc) {
    passwordCheckFormPc.addEventListener('submit', function() {
        setTimeout(() => {
            const passwordInput = this.querySelector('input[type="password"]');
            if (passwordInput) passwordInput.value = '';
        }, 100);
    });
}
