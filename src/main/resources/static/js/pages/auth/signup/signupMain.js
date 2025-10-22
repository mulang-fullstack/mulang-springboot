/**
 * 회원가입 메인 스크립트
 * 초기화, 상태관리, 제출 로직
 */
const signupState = {
    isEmailVerified: false,
    isEmailCodeVerified: false,
    emailValue: '',
    verificationCode: ''
};

document.addEventListener('DOMContentLoaded', function () {
    setupFormUI();
    setupRealTimeValidation();
    setupSubmitHandler();
});

/**
 * 폼 제출 이벤트 처리
 */
function setupSubmitHandler() {
    const signupForm = document.getElementById('signupForm');
    if (!signupForm) return;

    signupForm.addEventListener('submit', async (e) => {
        e.preventDefault();

        const formData = {
            username: document.getElementById('username').value.trim(),
            nickname: document.getElementById('nickname').value.trim(),
            email: document.getElementById('email').value.trim(),
            password: document.getElementById('password').value,
            passwordConfirm: document.getElementById('passwordConfirm').value,
            accountType: document.querySelector('input[name="accountType"]:checked').value
        };
        // [1] 이메일 인증 여부 확인
        if (!signupState.isEmailVerified) {
            showMessage('이메일 인증을 먼저 진행해주세요.', 'warning');
            document.getElementById('email').focus();
            return;
        }

        // [2] 인증코드 확인 여부
        if (!signupState.isEmailCodeVerified) {
            showMessage('이메일 인증코드를 확인해주세요.', 'warning');
            document.getElementById('emailCode').focus();
            return;
        }

        // [3] 기본 폼 유효성 검사
        const validation = validateForm(formData, signupState);
        if (!validation.isValid) {
            showMessage(validation.message, 'error');
            if (validation.field) document.getElementById(validation.field).focus();
            return;
        }

        // [4] 버튼 상태 변경
        const submitBtn = document.querySelector('.btn-primary');
        setButtonLoading(submitBtn, true);
        submitBtn.textContent = '처리중...';

        try {
            // 실제 회원가입 API 호출로 대체 예정
            // await simulateAPICall(2000);
            // showMessage('회원가입이 완료되었습니다!', 'success');
            // 실제 서버 요청으로 변경
            const response = await fetch('/auth/signup', {
                method: 'POST',
                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                body: new URLSearchParams(formData)
            });

            const result = await response.text();

            if (response.ok && result === 'success') {
                showMessage('회원가입이 완료되었습니다!', 'success');
                setTimeout(() => location.href = '/auth/login', 1500);
            } else {
                showMessage('회원가입에 실패했습니다. 다시 시도해주세요.', 'error');
                location.href = '/auth/signup'
            }

        } catch {
            showMessage('회원가입 중 오류가 발생했습니다.', 'error');
        } finally {
            submitBtn.textContent = '가입하기';
            setButtonLoading(submitBtn, false);
        }
    });
}