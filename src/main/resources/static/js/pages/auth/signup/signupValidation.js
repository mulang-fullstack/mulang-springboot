/**
 * 회원가입 유효성 검사 관련 함수
 * 이메일, 비밀번호, 이름 등 검증 로직 관리
 */

/**
 * 실시간 유효성 검사 설정
 */
function setupRealTimeValidation() {
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

    // 이름 길이 검사
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
 * 폼 전체 유효성 검사
 */
function validateForm(formData, signupState) {
    if (!signupState.isEmailVerified)
        return { isValid: false, message: '이메일 인증 요청을 진행해주세요.', field: 'email' };

    if (!signupState.isEmailCodeVerified)
        return { isValid: false, message: '이메일 인증을 완료해주세요.', field: 'emailCode' };

    if (!validatePassword(formData.password))
        return { isValid: false, message: '비밀번호 조건을 확인해주세요.', field: 'password' };

    if (formData.password !== formData.passwordConfirm)
        return { isValid: false, message: '비밀번호가 일치하지 않습니다.', field: 'passwordConfirm' };

    if (!formData.name || formData.name.length < 2)
        return { isValid: false, message: '이름을 올바르게 입력해주세요.', field: 'name' };

    return { isValid: true };
}
