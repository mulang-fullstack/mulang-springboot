/**
 * 이메일 인증코드 검증
 */
async function verifyEmailCode() {
    const codeInput = document.getElementById('emailCode');
    const code = codeInput.value.trim();
    const verifyBtn = event.target;

    // [1] 유효성 검사
    if (!code) {
        showMessage('인증코드를 입력해주세요.', 'warning');
        return;
    }
    if (!signupState.isEmailVerified) {
        showMessage('먼저 이메일 인증 요청을 진행해주세요.', 'warning');
        return;
    }

    setButtonLoading(verifyBtn, true);

    try {
        // [2] 실제 서버 검증 요청 (임시 코드 비교 가능)
        const isValid = await verifyEmailCodeServer(signupState.emailValue, code);

        // [3] 성공 여부에 따른 처리
        if (isValid) {
            showMessage('이메일 인증이 완료되었습니다.', 'success');
            setFieldStatus(codeInput, 'success', '인증이 완료되었습니다.');
            signupState.isEmailCodeVerified = true;

            // 완료 시 필드 잠금
            codeInput.readOnly = true;
            verifyBtn.textContent = '인증완료';
            verifyBtn.disabled = true;
            verifyBtn.classList.add('verified');
        } else {
            showMessage('인증코드가 일치하지 않습니다.', 'error');
            setFieldStatus(codeInput, 'error', '인증코드가 일치하지 않습니다.');
            signupState.isEmailCodeVerified = false;
        }
    } catch (error) {
        console.error(error);
        showMessage('서버 오류가 발생했습니다.', 'error');
    } finally {
        setButtonLoading(verifyBtn, false);
    }
}

/**
 * 실제 서버 검증 요청 (또는 임시 로직)
 */
async function verifyEmailCodeServer(email, code) {
    try {
        // 서버 요청 버전 (실제 구현 시)
        // const response = await fetch('/auth/verify-code', {
        //     method: 'POST',
        //     headers: { 'Content-Type': 'application/json' },
        //     body: JSON.stringify({ email, code })
        // });
        // const result = await response.text();
        // return result === 'success';

        // [테스트용 임시 비교]
        await simulateAPICall(800);
        return code === String(signupState.verificationCode); // 임시 저장된 코드와 비교
    } catch {
        return false;
    }
}
