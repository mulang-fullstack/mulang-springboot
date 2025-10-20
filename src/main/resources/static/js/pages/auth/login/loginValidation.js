/**
 * 회원가입 페이지 JavaScript
 * 개선된 UX와 애니메이션 효과 포함
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
    initializeForm();
    setupEventListeners();
    setupRealTimeValidation();
});

/**
 * 폼 초기화
 */
function initializeForm() {
    // 이메일 인증코드 필드 비활성화
    const emailCodeInput = document.getElementById('emailCode');
    if (emailCodeInput) {
        emailCodeInput.disabled = true;
        emailCodeInput.parentElement.style.opacity = '0.5';
    }
}

/**
 * 이벤트 리스너 설정
 */
function setupEventListeners() {
    // 계정타입 라디오 버튼
    const radioLabels = document.querySelectorAll('.radio-label');
    radioLabels.forEach(label => {
        label.addEventListener('click', function() {
            radioLabels.forEach(l => l.classList.remove('active'));
            this.classList.add('active');

            // 선택 효과 애니메이션
            this.style.transform = 'scale(0.98)';
            setTimeout(() => {
                this.style.transform = 'scale(1)';
            }, 100);
        });
    });

    // 폼 제출
    const signupForm = document.getElementById('signupForm');
    if (signupForm) {
        signupForm.addEventListener('submit', handleSubmit);
    }

    // 이메일 입력 필드 변경 감지
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
 * 이메일 중복 확인
 */
async function verifyEmail() {
    const emailInput = document.getElementById('email');
    const email = emailInput.value.trim();
    const verifyBtn = event.target;

    // 유효성 검사
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

    // 로딩 상태
    setButtonLoading(verifyBtn, true);

    try {
        // 실제 구현 시 fetch API 사용
        await simulateAPICall(1000);

        // 중복 체크 시뮬레이션
        if (email === 'example@test.com') {
            showMessage('이미 사용 중인 이메일입니다.', 'error');
            setFieldStatus(emailInput, 'error', '이미 사용 중인 이메일입니다.');
            signupState.isEmailVerified = false;
        } else {
            showMessage('사용 가능한 이메일입니다. 인증코드를 발송했습니다.', 'success');
            setFieldStatus(emailInput, 'success', '사용 가능한 이메일입니다.');

            // 인증코드 필드 활성화
            const emailCodeInput = document.getElementById('emailCode');
            emailCodeInput.disabled = false;
            emailCodeInput.parentElement.style.opacity = '1';
            emailCodeInput.focus();

            // 상태 업데이트
            signupState.isEmailVerified = true;
            signupState.emailValue = email;

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
 * 이메일 인증코드 확인
 */
async function verifyEmailCode() {
    const codeInput = document.getElementById('emailCode');
    const code = codeInput.value.trim();
    const verifyBtn = event.target;

    if (!code) {
        showMessage('인증코드를 입력해주세요.', 'warning');
        codeInput.focus();
        return;
    }

    if (!signupState.isEmailVerified) {
        showMessage('먼저 이메일 중복 확인을 진행해주세요.', 'warning');
        return;
    }

    // 로딩 상태
    setButtonLoading(verifyBtn, true);

    try {
        await simulateAPICall(1000);

        // 인증코드 검증 시뮬레이션
        if (code === '123456') {
            showMessage('인증이 완료되었습니다.', 'success');
            setFieldStatus(codeInput, 'success', '인증이 완료되었습니다.');

            signupState.isEmailCodeVerified = true;
            signupState.verificationCode = code;

            // 필드 읽기 전용 설정
            codeInput.readOnly = true;
            verifyBtn.textContent = '인증완료';
            verifyBtn.classList.add('verified');
            verifyBtn.disabled = true;
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
 * 비밀번호 표시/숨김 토글
 */
function togglePassword(inputId) {
    const passwordInput = document.getElementById(inputId);
    const button = event.currentTarget;
    const eyeIcon = button.querySelector('.eye-icon');

    if (passwordInput.type === 'password') {
        passwordInput.type = 'text';
        eyeIcon.textContent = '👁‍🗨';
        button.style.color = 'var(--text-point-main)';
    } else {
        passwordInput.type = 'password';
        eyeIcon.textContent = '👁';
        button.style.color = 'var(--text-tertiary)';
    }
}

/**
 * 실시간 유효성 검사 설정
 */
function setupRealTimeValidation() {
    // 이메일 검증
    const emailInput = document.getElementById('email');
    if (emailInput) {
        emailInput.addEventListener('blur', function() {
            if (this.value && !validateEmail(this.value)) {
                setFieldStatus(this, 'error', '올바른 이메일 형식이 아닙니다.');
            } else if (this.value) {
                clearFieldStatus(this);
            }
        });
    }

    // 비밀번호 검증
    const passwordInput = document.getElementById('password');
    if (passwordInput) {
        passwordInput.addEventListener('input', function() {
            validatePasswordStrength(this);
        });

        passwordInput.addEventListener('blur', function() {
            if (this.value && !validatePassword(this.value)) {
                setFieldStatus(this, 'error', '6자리 이상, 영문자와 숫자를 포함해야 합니다.');
            }
        });
    }

    // 비밀번호 확인 검증
    const passwordConfirmInput = document.getElementById('passwordConfirm');
    if (passwordConfirmInput) {
        passwordConfirmInput.addEventListener('blur', function() {
            const password = document.getElementById('password').value;
            if (this.value && this.value !== password) {
                setFieldStatus(this, 'error', '비밀번호가 일치하지 않습니다.');
            } else if (this.value && this.value === password) {
                setFieldStatus(this, 'success', '비밀번호가 일치합니다.');
            }
        });
    }

    // 이름 검증
    const nameInput = document.getElementById('name');
    if (nameInput) {
        nameInput.addEventListener('blur', function() {
            if (this.value && this.value.length < 2) {
                setFieldStatus(this, 'error', '이름은 2자 이상이어야 합니다.');
            } else if (this.value) {
                clearFieldStatus(this);
            }
        });
    }
}

/**
 * 폼 제출 처리
 */
async function handleSubmit(e) {
    e.preventDefault();

    // 폼 데이터 수집
    const formData = {
        accountType: document.querySelector('input[name="accountType"]:checked').value,
        email: document.getElementById('email').value.trim(),
        password: document.getElementById('password').value,
        passwordConfirm: document.getElementById('passwordConfirm').value,
        name: document.getElementById('name').value.trim()
    };

    // 유효성 검사
    const validationResult = validateForm(formData);
    if (!validationResult.isValid) {
        showMessage(validationResult.message, 'error');
        if (validationResult.field) {
            document.getElementById(validationResult.field).focus();
        }
        return;
    }

    // 제출 버튼 로딩 상태
    const submitBtn = document.querySelector('.btn-primary');
    setButtonLoading(submitBtn, true);
    submitBtn.textContent = '처리중...';

    try {
        // 서버 전송 시뮬레이션
        await simulateAPICall(2000);

        // 성공 처리
        showMessage('회원가입이 완료되었습니다!', 'success');

        // 로그인 페이지로 리다이렉트 (실제 구현 시)
        setTimeout(() => {
            // window.location.href = '/login';
            console.log('회원가입 성공:', formData);
        }, 1500);

    } catch (error) {
        showMessage('회원가입 중 오류가 발생했습니다.', 'error');
        submitBtn.textContent = '가입하기';
        setButtonLoading(submitBtn, false);
    }
}

/* ===== 유틸리티 함수들 ===== */

/**
 * 이메일 유효성 검사
 */
function validateEmail(email) {
    const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return re.test(email);
}

/**
 * 비밀번호 유효성 검사
 */
function validatePassword(password) {
    // 6자리 이상, 영문자와 숫자 포함
    const re = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d@$!%*#?&]{6,}$/;
    return re.test(password);
}

/**
 * 비밀번호 강도 검증
 */
function validatePasswordStrength(input) {
    const password = input.value;
    const formGroup = input.closest('.form-group');

    // 기존 강도 표시 제거
    const existingStrength = formGroup.querySelector('.password-strength');
    if (existingStrength) {
        existingStrength.remove();
    }

    if (password.length > 0) {
        let strength = 0;
        if (password.length >= 6) strength++;
        if (password.length >= 10) strength++;
        if (/[a-z]/.test(password) && /[A-Z]/.test(password)) strength++;
        if (/\d/.test(password)) strength++;
        if (/[@$!%*#?&]/.test(password)) strength++;

        const strengthText = ['매우 약함', '약함', '보통', '강함', '매우 강함'][strength];
        const strengthColor = ['#dc3545', '#f59e0b', '#fbbf24', '#84cc16', '#22c55e'][strength];

        const strengthDiv = document.createElement('div');
        strengthDiv.className = 'password-strength';
        strengthDiv.style.cssText = `
            margin-top: 6px;
            font-size: 12px;
            color: ${strengthColor};
            display: flex;
            align-items: center;
            gap: 8px;
        `;

        const strengthBar = document.createElement('div');
        strengthBar.style.cssText = `
            flex: 1;
            height: 4px;
            background: #e5e5e5;
            border-radius: 2px;
            overflow: hidden;
        `;

        const strengthFill = document.createElement('div');
        strengthFill.style.cssText = `
            height: 100%;
            width: ${(strength / 5) * 100}%;
            background: ${strengthColor};
            transition: all 0.3s ease;
        `;

        strengthBar.appendChild(strengthFill);
        strengthDiv.innerHTML = `<span>비밀번호 강도: ${strengthText}</span>`;
        strengthDiv.appendChild(strengthBar);

        formGroup.appendChild(strengthDiv);
    }
}

/**
 * 폼 전체 유효성 검사
 */
function validateForm(formData) {
    if (!signupState.isEmailVerified) {
        return { isValid: false, message: '이메일 중복 확인을 진행해주세요.', field: 'email' };
    }

    if (!signupState.isEmailCodeVerified) {
        return { isValid: false, message: '이메일 인증을 완료해주세요.', field: 'emailCode' };
    }

    if (!validatePassword(formData.password)) {
        return { isValid: false, message: '비밀번호는 6자리 이상, 영문자와 숫자를 포함해야 합니다.', field: 'password' };
    }

    if (formData.password !== formData.passwordConfirm) {
        return { isValid: false, message: '비밀번호가 일치하지 않습니다.', field: 'passwordConfirm' };
    }

    if (!formData.name || formData.name.length < 2) {
        return { isValid: false, message: '이름을 올바르게 입력해주세요.', field: 'name' };
    }

    return { isValid: true };
}

/**
 * 필드 상태 설정
 */
function setFieldStatus(input, status, message) {
    const formGroup = input.closest('.form-group');

    // 기존 상태 제거
    formGroup.classList.remove('error', 'success');
    const existingMessage = formGroup.querySelector('.error-message, .success-message');
    if (existingMessage) {
        existingMessage.remove();
    }

    // 새 상태 추가
    formGroup.classList.add(status);

    if (message) {
        const messageDiv = document.createElement('div');
        messageDiv.className = `${status}-message`;
        messageDiv.textContent = message;
        formGroup.appendChild(messageDiv);
    }
}

/**
 * 필드 상태 초기화
 */
function clearFieldStatus(input) {
    const formGroup = input.closest('.form-group');
    formGroup.classList.remove('error', 'success');
    const existingMessage = formGroup.querySelector('.error-message, .success-message');
    if (existingMessage) {
        existingMessage.remove();
    }
}

/**
 * 버튼 로딩 상태 설정
 */
function setButtonLoading(button, isLoading) {
    if (isLoading) {
        button.disabled = true;
        button.classList.add('loading');
        button.dataset.originalText = button.textContent;
        button.textContent = '';
    } else {
        button.disabled = false;
        button.classList.remove('loading');
        if (button.dataset.originalText) {
            button.textContent = button.dataset.originalText;
        }
    }
}

/**
 * 이메일 인증 초기화
 */
function resetEmailVerification() {
    signupState.isEmailVerified = false;
    signupState.isEmailCodeVerified = false;
    signupState.emailValue = '';

    // 인증코드 필드 초기화
    const emailCodeInput = document.getElementById('emailCode');
    if (emailCodeInput) {
        emailCodeInput.value = '';
        emailCodeInput.disabled = true;
        emailCodeInput.readOnly = false;
        emailCodeInput.parentElement.style.opacity = '0.5';
        clearFieldStatus(emailCodeInput);
    }

    // 버튼 상태 초기화
    const verifyBtns = document.querySelectorAll('.btn-verify');
    verifyBtns.forEach(btn => {
        btn.classList.remove('verified');
        btn.disabled = false;
        if (btn.textContent === '발송완료' || btn.textContent === '인증완료') {
            btn.textContent = btn.textContent === '발송완료' ? '중복확인' : '인증확인';
        }
    });
}

/**
 * 메시지 표시 (토스트 알림)
 */
function showMessage(message, type = 'info') {
    // 기존 토스트 제거
    const existingToast = document.querySelector('.toast-message');
    if (existingToast) {
        existingToast.remove();
    }

    // 토스트 생성
    const toast = document.createElement('div');
    toast.className = `toast-message toast-${type}`;
    toast.textContent = message;

    // 스타일 설정
    toast.style.cssText = `
        position: fixed;
        top: 20px;
        right: 20px;
        padding: 16px 24px;
        border-radius: 8px;
        font-size: 14px;
        font-weight: 500;
        z-index: 10000;
        animation: slideInRight 0.3s ease;
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
        max-width: 320px;
        word-break: keep-all;
    `;

    // 타입별 색상 설정
    const colors = {
        success: { bg: '#22c55e', text: '#ffffff' },
        error: { bg: '#dc3545', text: '#ffffff' },
        warning: { bg: '#f59e0b', text: '#ffffff' },
        info: { bg: '#3498db', text: '#ffffff' }
    };

    const color = colors[type] || colors.info;
    toast.style.backgroundColor = color.bg;
    toast.style.color = color.text;

    // DOM에 추가
    document.body.appendChild(toast);

    // 3초 후 제거
    setTimeout(() => {
        toast.style.animation = 'slideOutRight 0.3s ease';
        setTimeout(() => toast.remove(), 300);
    }, 3000);
}

/**
 * API 호출 시뮬레이션
 */
function simulateAPICall(delay = 1000) {
    return new Promise(resolve => setTimeout(resolve, delay));
}

/**
 * 애니메이션 스타일 추가
 */
const style = document.createElement('style');
style.textContent = `
    @keyframes slideInRight {
        from {
            transform: translateX(100%);
            opacity: 0;
        }
        to {
            transform: translateX(0);
            opacity: 1;
        }
    }
    
    @keyframes slideOutRight {
        from {
            transform: translateX(0);
            opacity: 1;
        }
        to {
            transform: translateX(100%);
            opacity: 0;
        }
    }
    
    .password-strength {
        animation: fadeIn 0.3s ease;
    }
    
    /* 토스트 메시지 호버 효과 */
    .toast-message:hover {
        transform: scale(1.02);
        transition: transform 0.2s ease;
    }
    
    /* 입력 필드 포커스 애니메이션 */
    input:focus {
        animation: focusPulse 0.3s ease;
    }
    
    @keyframes focusPulse {
        0% {
            box-shadow: 0 0 0 0 rgba(0, 79, 154, 0);
        }
        100% {
            box-shadow: 0 0 0 4px rgba(0, 79, 154, 0.08);
        }
    }
`;
document.head.appendChild(style);

/**
 * 디버깅용 콘솔 로그 (개발 환경에서만 사용)
 */
const DEBUG = false; // 프로덕션에서는 false로 설정

function debugLog(message, data) {
    if (DEBUG) {
        console.log(`[Signup] ${message}`, data || '');
    }
}

// 전역 에러 핸들러
window.addEventListener('error', function(event) {
    console.error('Signup Error:', event.error);
    showMessage('예기치 않은 오류가 발생했습니다.', 'error');
});