/**
 * 이메일 인증코드 검증
 */
async function verifyEmailCode() {
    const codeInput = document.getElementById('emailCode');
    const code = codeInput.value.trim();
    const verifyBtn = codeInput.parentElement.querySelector('.btn-verify');

    // [1] 유효성 검사
    if (!code) {
        codeInput.focus();
        return;
    }
    if (!signupState.emailValue) {
        return;
    }

    setButtonLoading(verifyBtn, true);

    try {
        // [2] 실제 서버 검증 요청
        const result = await verifyEmailCodeServer(signupState.emailValue, code);

        // [3] 서버 응답 처리
        if (result === 'valid') {
            setFieldStatus(codeInput, 'success', '인증이 완료되었습니다.');
            signupState.isEmailCodeVerified = true;

            // 완료 시 필드 잠금
            codeInput.readOnly = true;
            verifyBtn.textContent = '인증완료';
            verifyBtn.disabled = true;
            verifyBtn.classList.add('verified');
        }
        else if (result === 'expired') {
            setFieldStatus(codeInput, 'error', '인증코드가 만료되었습니다.');
            signupState.isEmailCodeVerified = false;
        }
        else {
            setFieldStatus(codeInput, 'error', '인증코드가 일치하지 않습니다.');
            signupState.isEmailCodeVerified = false;
        }

    } catch (error) {
        console.error(error);
        showMessage('서버 오류가 발생했습니다. 다시 시도해주세요.', 'error');
        signupState.isEmailCodeVerified = false;
    } finally {
        setButtonLoading(verifyBtn, false);
    }
}

/**
 * 서버에 이메일 + 인증코드 검증 요청
 */
async function verifyEmailCodeServer(email, code) {
    try {
        const response = await fetch('/auth/email/verify', {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: new URLSearchParams({ email, code })
        });

        if (!response.ok) throw new Error('서버 오류');
        const result = await response.text();

        // result: "valid", "invalid", "expired"
        return result;
    } catch (error) {
        console.error('verifyEmailCodeServer error:', error);
        return 'error';
    }
}