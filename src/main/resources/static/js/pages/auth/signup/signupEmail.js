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
        showMessage('이메일을 입력해주세요.', 'warning');
        emailInput.focus();
        return;
    }
    if (!validateEmail(email)) {
        showMessage('올바른 이메일 형식이 아닙니다.', 'error');
        emailInput.focus();
        return;
    }
    setButtonLoading(verifyBtn, true);

    try {
        // [2] 실제 서버에 중복 확인 요청
        const available = await checkEmailDuplicate(email);
        if (!available) {
            // 이미 존재하는 이메일이면 중단
            setButtonLoading(verifyBtn, false);
            return;
        }
        // [3] 인증번호 요청
        await requestEmailCode(email);

        // [4] 버튼 상태 변경
        verifyBtn.textContent = '발송완료';
        verifyBtn.classList.add('verified');
    } catch (error) {
        showMessage('서버 오류가 발생했습니다. 다시 시도해주세요.', 'error');
    } finally {
        setButtonLoading(verifyBtn, false);
    }
}


/**
 * 이메일 중복 확인
 */
async function checkEmailDuplicate(email) {
    try {
        const response = await fetch(`/auth/checkEmail?email=${encodeURIComponent(email)}`);
        if (!response.ok) throw new Error('서버 오류');
        const result = await response.text();

        if (result === "error") {
            setFieldStatus(document.getElementById('email'), 'error', '이미 사용 중인 이메일입니다.');
            showMessage('이미 사용 중인 이메일입니다.', 'error');
            return false;
        } else if(result === "success") {
            setFieldStatus(document.getElementById('email'), 'success', '사용 가능한 이메일입니다.');
            showMessage('사용 가능한 이메일입니다.', 'success');
            return true;
        }
    } catch (error) {
        showMessage('서버 오류가 발생했습니다.', 'error');
        return false;
    }
}

/**
 * 이메일 인증번호 요청 (임시 테스트용)
 */
async function requestEmailCode(email) {
    // 실제 구현 시 서버에 이메일 발송 요청
    // ex: const response = await fetch('/auth/send-code', { method: 'POST', body: JSON.stringify({ email }) });

    // 임시 테스트
    const tempCode = Math.floor(100000 + Math.random() * 900000);
    console.log(`📧 임시 인증코드: ${tempCode}`);
    showMessage('인증코드가 발송되었습니다. (콘솔을 확인하세요)', 'info');

    // 상태 갱신 및 UI 처리
    signupState.isEmailVerified = true;
    signupState.emailValue = email;
    signupState.verificationCode = tempCode;

    const emailCodeGroup = document.querySelector('.email-code-group');
    emailCodeGroup.classList.remove('hidden');
    emailCodeGroup.classList.add('show');

    const codeInput = document.getElementById('emailCode');
    codeInput.disabled = false;
    codeInput.parentElement.style.opacity = '1';
    codeInput.focus();
}
