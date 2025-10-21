/**
 * 회원가입 유효성 검사 관련 함수
 * 이메일, 비밀번호, 이름 등 검증 로직 관리
 */

/**
 * 실시간 유효성 검사 설정
 */
function setupRealTimeValidation() {

    // 이름 길이 검사
    const nameInput = document.getElementById('username');
    if (nameInput) {
        nameInput.addEventListener('blur', function() {
            if (this.value && this.value.length < 2) {
                setFieldStatus(this, 'error', '이름은 2자 이상, 4자 이하이어야 합니다.');
            } else if (this.value) {
                clearFieldStatus(this);
            }
        });
    }

    // 닉네임 길이 검사
    const nicknameInput = document.getElementById('nickname');
    if (nicknameInput) {
        nicknameInput.addEventListener('blur', function() {
            if (this.value && this.value.length < 2) {
                setFieldStatus(this, 'error', '닉네임은 2자 이상, 8자 이하이어야 합니다.');
            } else if (this.value) {
                clearFieldStatus(this);
            }
        });
    }

    // 이메일 형식 검사
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

    // 비밀번호 검사
    const passwordInput = document.getElementById('password');
    if (passwordInput) {
        passwordInput.addEventListener('blur', function() {
            if (this.value && !validatePassword(this.value)) {
                setFieldStatus(this, 'error', '6자리 이상, 영문자와 숫자를 포함해야 합니다.');
            } else if (this.value) {
                setFieldStatus(this, 'success', '사용 가능한 비밀번호입니다.');
            }
        });
    }

    passwordInput.addEventListener('input', function() {
        validatePasswordStrength(this);
    });


    // 비밀번호 확인 일치 검사
    const passwordConfirm = document.getElementById('passwordConfirm');
    if (passwordConfirm) {
        passwordConfirm.addEventListener('blur', function() {
            const pw = document.getElementById('password').value;
            if (this.value && this.value !== pw) {
                setFieldStatus(this, 'error', '비밀번호가 일치하지 않습니다.');
            } else if (this.value && this.value === pw) {
                setFieldStatus(this, 'success', '비밀번호가 일치합니다.');
            }
        });
    }
}

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
    const re = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d!@#$%^&*()_+\-={}\[\]:;"'<>,.?/~`|\\]{6,}$/;
    return re.test(password);
}

/**
 * 비밀번호 강도 검증 (강도 → 메시지 순서 고정)
 */
function validatePasswordStrength(input) {
    const password = input.value;
    const formGroup = input.closest('.form-group') || input.parentElement;

    // 기존 강도 표시 제거
    const existingStrength = formGroup.querySelector('.password-strength');
    if (existingStrength) existingStrength.remove();

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
            animation: fadeIn 0.3s ease;
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
            width: ${(strength / 4) * 100}%;
            background: ${strengthColor};
            transition: all 0.3s ease;
        `;

        strengthBar.appendChild(strengthFill);
        strengthDiv.innerHTML = `<span>비밀번호 강도: ${strengthText}</span>`;
        strengthDiv.appendChild(strengthBar);

        const nextMessage = formGroup.querySelector('.error-message, .success-message');
        if (nextMessage) {
            formGroup.insertBefore(strengthDiv, nextMessage);
        } else {
            formGroup.appendChild(strengthDiv);
        }
    }
}


/**
 * 폼 전체 유효성 검사
 */
function validateForm(formData, signupState) {
    if (!formData.username || formData.username.length < 2)
        return { isValid: false, message: '이름을 올바르게 입력해주세요.', field: 'username' };

    if (!formData.nickname || formData.nickname.length < 2)
        return { isValid: false, message: '닉네임을 올바르게 입력해주세요.', field: 'nickname' };

    if (!signupState.isEmailVerified)
        return { isValid: false, message: '이메일 인증 요청을 진행해주세요.', field: 'email' };

    if (!signupState.isEmailCodeVerified)
        return { isValid: false, message: '이메일 인증을 완료해주세요.', field: 'emailCode' };

    if (!validatePassword(formData.password))
        return { isValid: false, message: '비밀번호 조건을 확인해주세요.', field: 'password' };

    if (formData.password !== formData.passwordConfirm)
        return { isValid: false, message: '비밀번호가 일치하지 않습니다.', field: 'passwordConfirm' };
    return { isValid: true };
}
