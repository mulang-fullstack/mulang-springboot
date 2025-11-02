/**
 * 이메일 중복 확인 및 인증코드 발송
 */
async function verifyEmail() {
    const emailInput = document.getElementById('email');
    const email = emailInput.value.trim();
    const verifyBtn = event.target;
    const emailCodeGroup = document.querySelector('.email-code-group');

    // [1] 유효성 검사
    if (!email) {
        emailInput.focus();
        return;
    }
    if (!validateEmail(email)) {
        emailInput.focus();
        return;
    }

    setButtonLoading(verifyBtn, true);

    try {
        const response = await fetch(`/auth/email/send`, {
            method: 'POST',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            body: new URLSearchParams({email})
        });
        if (!response.ok) throw new Error('서버 오류');
        const result = await response.text();

        if (result === "duplicate") {
            setFieldStatus(emailInput, 'error', '이미 사용 중인 이메일입니다.');
            return;
        }
        if (result === "sent") {
            setFieldStatus(emailInput, 'success', '인증코드가 전송되었습니다.');

            // [5] 인증코드 입력창 표시
            emailCodeGroup.classList.remove('hidden');
            emailCodeGroup.classList.add('show');

            const codeInput = document.getElementById('emailCode');
            codeInput.disabled = false;
            codeInput.parentElement.style.opacity = '1';
            codeInput.focus();

            // 상태 갱신 - isEmailVerified를 true로 설정 (인증코드 발송됨)
            signupState.isEmailVerified = true;
            signupState.isEmailCodeVerified = false; // 아직 코드 인증은 안됨
            signupState.emailValue = email;

            // [4] 버튼 텍스트를 "재전송"으로 변경
            setTimeout(() => {
                verifyBtn.textContent = '재전송';
                verifyBtn.classList.add('resend');
            }, 100);
        }
    } catch (error) {
        showMessage('서버 오류가 발생했습니다.', 'error');
        return false;
    } finally {
        setButtonLoading(verifyBtn, false);
    }
}