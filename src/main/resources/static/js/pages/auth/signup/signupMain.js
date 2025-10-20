/**
 * 회원가입 메인 스크립트
 * 폼 이벤트, 상태 관리, 인증 처리 로직
 */

// 전역 상태 관리
const signupState = {
    isEmailVerified: false,
    isEmailCodeVerified: false,
    emailValue: '',
    verificationCode: ''
};

/**
 * 페이지 로드 시 초기화
 */
document.addEventListener('DOMContentLoaded', function() {
    setupEventListeners();
    setupRealTimeValidation();
});

/**
 * 이벤트 리스너 설정
 */
function setupEventListeners() {
    // 계정타입 라디오 버튼 클릭 애니메이션
    const radioLabels = document.querySelectorAll('.radio-label');
    radioLabels.forEach(label => {
        label.addEventListener('click', function() {
            radioLabels.forEach(l => l.classList.remove('active'));
            this.classList.add('active');
            this.style.transform = 'scale(0.98)';
            setTimeout(() => this.style.transform = 'scale(1)', 100);
        });
    });

    // 폼 제출 이벤트
    const signupForm = document.getElementById('signupForm');
    if (signupForm) {
        signupForm.addEventListener('submit', handleSubmit);
    }

    // 이메일 입력 변경 시 인증 초기화
    const emailInput = document.getElementById('email');
    if (emailInput) {
        emailInput.addEventListener('input', function() {
            if (signupState.isEmailVerified && this.value !== signupState.emailValue) {
                resetEmailVerification();
            }
        });
    }
}

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

    // [2] 로딩 상태
    setButtonLoading(verifyBtn, true);

    try {
        // [3] 서버 요청 시뮬레이션
        await simulateAPICall(1000);

        // [4] 중복 체크 예시
        if (email === 'example@test.com') {
            showMessage('이미 사용 중인 이메일입니다.', 'error');
            setFieldStatus(emailInput, 'error', '이미 사용 중인 이메일입니다.');
            signupState.isEmailVerified = false;
        } else {
            showMessage('사용 가능한 이메일입니다. 인증코드를 발송했습니다.', 'success');
            setFieldStatus(emailInput, 'success', '사용 가능한 이메일입니다.');

            // 상태 갱신
            signupState.isEmailVerified = true;
            signupState.emailValue = email;

            // [5] 인증코드 입력창 표시 및 활성화
            emailCodeGroup.classList.remove('hidden');
            emailCodeGroup.classList.add('show');
            const codeInput = document.getElementById('emailCode');
            codeInput.disabled = false;
            codeInput.parentElement.style.opacity = '1';
            codeInput.focus();

            // 버튼 상태 변경
            verifyBtn.textContent = '발송완료';
            verifyBtn.classList.add('verified');
        }
    } catch (error) {
        showMessage('서버 오류가 발생했습니다. 다시 시도해주세요.', 'error');
    } finally {
        setButtonLoading(verifyBtn, false);
    }
}

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

    // [2] 로딩 상태
    setButtonLoading(verifyBtn, true);

    try {
        await simulateAPICall(1000);

        // [3] 코드 일치 여부 검사
        if (code === '123456') {
            showMessage('이메일 인증이 완료되었습니다.', 'success');
            setFieldStatus(codeInput, 'success', '인증이 완료되었습니다.');
            signupState.isEmailCodeVerified = true;

            // 인증 완료 시 비활성화 처리
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
        showMessage('서버 오류가 발생했습니다.', 'error');
    } finally {
        setButtonLoading(verifyBtn, false);
    }
}

/**
 * 폼 제출 처리
 */
async function handleSubmit(e) {
    //e.preventDefault();

    // 폼 데이터 수집
    const formData = {
        accountType: document.querySelector('input[name="accountType"]:checked').value,
        email: document.getElementById('email').value.trim(),
        password: document.getElementById('password').value,
        passwordConfirm: document.getElementById('passwordConfirm').value,
        name: document.getElementById('name').value.trim()
    };

    // 폼 유효성 검사
    const validation = validateForm(formData, signupState);
    if (!validation.isValid) {
        showMessage(validation.message, 'error');
        if (validation.field) document.getElementById(validation.field).focus();
        return;
    }

    // 제출 버튼 상태 변경
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
}

/**
 * 이메일 인증 초기화
 */
function resetEmailVerification() {
    signupState.isEmailVerified = false;
    signupState.isEmailCodeVerified = false;
    signupState.emailValue = '';

    const emailCodeInput = document.getElementById('emailCode');
    if (emailCodeInput) {
        emailCodeInput.value = '';
        emailCodeInput.readOnly = false;
        emailCodeInput.parentElement.style.opacity = '0.5';
        clearFieldStatus(emailCodeInput);
    }

    const verifyBtns = document.querySelectorAll('.btn-verify');
    verifyBtns.forEach(btn => {
        btn.classList.remove('verified');
        btn.disabled = false;
        if (btn.textContent === '발송완료' || btn.textContent === '인증완료') {
            btn.textContent = '인증요청';
        }
    });
}
