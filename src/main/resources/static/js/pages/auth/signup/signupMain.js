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

document.addEventListener('DOMContentLoaded', function() {
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
        // e.preventDefault(); // 테스트 단계에서만 막을지 결정

        const formData = {
            username: document.getElementById('username').value.trim(),
            nickname: document.getElementById('nickname').value.trim(),
            email: document.getElementById('email').value.trim(),
            password: document.getElementById('password').value,
            passwordConfirm: document.getElementById('passwordConfirm').value,
            accountType: document.querySelector('input[name="accountType"]:checked').value
        };

        const validation = validateForm(formData, signupState);
        if (!validation.isValid) {
            showMessage(validation.message, 'error');
            if (validation.field) document.getElementById(validation.field).focus();
            return;
        }

        const submitBtn = document.querySelector('.btn-primary');
        setButtonLoading(submitBtn, true);
        submitBtn.textContent = '처리중...';

        try {
            await simulateAPICall(2000);
            showMessage('회원가입이 완료되었습니다!', 'success');
        } catch {
            showMessage('회원가입 중 오류가 발생했습니다.', 'error');
        } finally {
            submitBtn.textContent = '가입하기';
            setButtonLoading(submitBtn, false);
        }
    });
}
