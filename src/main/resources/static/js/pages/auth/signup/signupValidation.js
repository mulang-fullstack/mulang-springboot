/**
 * 회원가입 유효성 검사 관련 함수
 * 이메일, 비밀번호, 이름 등 검증 로직 관리
 */

/**
 * 실시간 유효성 검사 설정
 */
function setupRealTimeValidation() {

    // 이름 길이 검사 (2~4자)
    const nameInput = document.getElementById('username');
    if (nameInput) {
        nameInput.addEventListener('input', function() {
            const name = this.value.trim();
            if (name.length > 0 && name.length < 2) {
                setFieldStatus(this, 'error', '이름은 2자 이상이어야 합니다.');
            } else if (name.length > 4) {
                setFieldStatus(this, 'error', '이름은 4자 이하이어야 합니다.');
            } else if (name.length >= 2 && name.length <= 4) {
                setFieldStatus(this, 'success', '사용 가능한 이름입니다.');
            } else {
                clearFieldStatus(this);
            }
        });
    }

    // 닉네임 실시간 중복 확인
    const nicknameInput = document.getElementById('nickname');
    if (nicknameInput) {
        nicknameInput.addEventListener('input', function() {
            const nickname = this.value.trim();

            // 이전 타이머 취소
            if (nicknameCheckTimeout) {
                clearTimeout(nicknameCheckTimeout);
            }

            // 상태 초기화
            signupState.isNicknameVerified = false;
            signupState.nicknameValue = '';

            // 2자 미만이면 에러 표시
            if (nickname.length > 0 && nickname.length < 2) {
                setFieldStatus(this, 'error', '닉네임은 2자 이상이어야 합니다.');
                return;
            }

            // 8자 초과하면 에러 표시
            if (nickname.length > 8) {
                setFieldStatus(this, 'error', '닉네임은 8자 이하이어야 합니다.');
                return;
            }

            // 입력이 멈추고 500ms 후에 중복 확인
            if (nickname.length >= 2) {
                clearFieldStatus(this);
                nicknameCheckTimeout = setTimeout(() => {
                    checkNicknameAsync(nickname);
                }, 500);
            } else {
                clearFieldStatus(this);
            }
        });
    }

    // 이메일 형식 및 길이 검사 (최대 50자)
    const emailInput = document.getElementById('email');
    if (emailInput) {
        emailInput.addEventListener('input', function() {
            const email = this.value.trim();
            if (email.length > 50) {
                setFieldStatus(this, 'error', '이메일은 50자 이하이어야 합니다.');
            } else {
                clearFieldStatus(this);
            }
        });

        emailInput.addEventListener('blur', function() {
            const email = this.value.trim();
            if (email && email.length > 50) {
                setFieldStatus(this, 'error', '이메일은 50자 이하이어야 합니다.');
            } else if (email && !validateEmail(email)) {
                setFieldStatus(this, 'error', '올바른 이메일 형식이 아닙니다.');
            } else if (email) {
                clearFieldStatus(this);
            }
        });
    }

    // 비밀번호 검사 (6~16자)
    const passwordInput = document.getElementById('password');
    if (passwordInput) {
        passwordInput.addEventListener('input', function() {
            const password = this.value;

            // 비밀번호 강도 표시
            validatePasswordStrength(this);

            // 16자 초과 체크
            if (password.length > 16) {
                setFieldStatus(this, 'error', '비밀번호는 16자 이하이어야 합니다.');
            }
        });

        passwordInput.addEventListener('blur', function() {
            const password = this.value;
            if (password && password.length > 16) {
                setFieldStatus(this, 'error', '비밀번호는 16자 이하이어야 합니다.');
            } else if (password && !validatePassword(password)) {
                setFieldStatus(this, 'error', '6~16자, 영문자와 숫자를 포함해야 합니다.');
            } else if (password && validatePassword(password)) {
                setFieldStatus(this, 'success', '사용 가능한 비밀번호입니다.');
            }
        });
    }

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
 * 비밀번호 유효성 검사 (6~16자, 영문+숫자)
 */
function validatePassword(password) {
    if (password.length < 6 || password.length > 16) return false;
    const re = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d!@#$%^&*()_+\-={}\[\]:;"'<>,.?/~`|\\]{6,16}$/;
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
    // 이름 검증 (2~4자)
    if (!formData.username || formData.username.length < 2)
        return { isValid: false, message: '이름은 2자 이상이어야 합니다.', field: 'username' };
    if (formData.username.length > 4)
        return { isValid: false, message: '이름은 4자 이하이어야 합니다.', field: 'username' };

    // 닉네임 검증 (2~8자)
    if (!formData.nickname || formData.nickname.length < 2)
        return { isValid: false, message: '닉네임은 2자 이상이어야 합니다.', field: 'nickname' };
    if (formData.nickname.length > 8)
        return { isValid: false, message: '닉네임은 8자 이하이어야 합니다.', field: 'nickname' };

    if (!signupState.isNicknameVerified)
        return { isValid: false, message: '사용 가능한 닉네임을 입력해주세요.', field: 'nickname' };

    // 이메일 검증 (최대 50자)
    if (!formData.email || !validateEmail(formData.email))
        return { isValid: false, message: '올바른 이메일을 입력해주세요.', field: 'email' };
    if (formData.email.length > 50)
        return { isValid: false, message: '이메일은 50자 이하이어야 합니다.', field: 'email' };

    if (!signupState.isEmailVerified)
        return { isValid: false, message: '이메일 인증 요청을 진행해주세요.', field: 'email' };

    if (!signupState.isEmailCodeVerified)
        return { isValid: false, message: '이메일 인증을 완료해주세요.', field: 'emailCode' };

    // 비밀번호 검증 (6~16자)
    if (!validatePassword(formData.password))
        return { isValid: false, message: '비밀번호는 6~16자, 영문자와 숫자를 포함해야 합니다.', field: 'password' };

    if (formData.password !== formData.passwordConfirm)
        return { isValid: false, message: '비밀번호가 일치하지 않습니다.', field: 'passwordConfirm' };

    return { isValid: true };
}