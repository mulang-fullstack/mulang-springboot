/**
 * 로그인 메인 스크립트
 * 초기화, 상태관리, 제출 로직
 */

const loginState = {
    isValid: false,
    rememberMe: false,
    attemptCount: 0,
    maxAttempts: 5
};

/**
 * 페이지 로드 시 초기화
 */
document.addEventListener('DOMContentLoaded', function() {
    initializeLogin();
    setupEventListeners();
    checkRememberedEmail();
});

/**
 * 로그인 폼 초기화
 */
function initializeLogin() {
    // 저장된 이메일이 있다면 자동 입력
    const savedEmail = localStorage.getItem('rememberedEmail');
    if (savedEmail) {
        document.getElementById('email').value = savedEmail;
        document.getElementById('rememberMe').checked = true;
    }

    // 포커스 설정
    const emailInput = document.getElementById('email');
    if (!emailInput.value) {
        emailInput.focus();
    } else {
        document.getElementById('password').focus();
    }
}

/**
 * 이벤트 리스너 설정
 */
function setupEventListeners() {
    // 폼 제출 이벤트
    const loginForm = document.getElementById('loginForm');
    if (loginForm) {
        loginForm.addEventListener('submit', handleLogin);
    }

    // Enter 키 이벤트
    document.addEventListener('keypress', function(e) {
        if (e.key === 'Enter' && e.target.type !== 'submit') {
            const form = e.target.closest('form');
            if (form && form.id === 'loginForm') {
                e.preventDefault();
                handleLogin(e);
            }
        }
    });

    // 로그인 상태 유지 체크박스
    const rememberMe = document.getElementById('rememberMe');
    if (rememberMe) {
        rememberMe.addEventListener('change', function() {
            loginState.rememberMe = this.checked;
        });
    }
}

/**
 * 로그인 처리
 */
async function handleLogin(e) {
    e.preventDefault();

    const form = e.target.closest('form') || document.getElementById('loginForm');
    const formData = {
        email: document.getElementById('email').value.trim(),
        password: document.getElementById('password').value,
        rememberMe: document.getElementById('rememberMe').checked
    };

    // 유효성 검사
    const validation = validateLoginForm(formData);
    if (!validation.isValid) {
        showMessage(validation.message, 'error');
        if (validation.field) {
            document.getElementById(validation.field).focus();
        }
        return;
    }

    // 로그인 시도 횟수 체크
    if (loginState.attemptCount >= loginState.maxAttempts) {
        showMessage('로그인 시도 횟수를 초과했습니다. 잠시 후 다시 시도해주세요.', 'error');
        disableLoginForm(30000); // 30초 동안 비활성화
        return;
    }

    // 버튼 로딩 상태
    const loginBtn = document.querySelector('.login-btn');
    setButtonLoading(loginBtn, true);
    loginBtn.textContent = '로그인 중...';

    try {
        // 서버 로그인 요청
        const result = await submitLogin(formData);

        if (result.success) {
            // 로그인 상태 유지 처리
            if (formData.rememberMe) {
                localStorage.setItem('rememberedEmail', formData.email);
            } else {
                localStorage.removeItem('rememberedEmail');
            }

            showMessage('로그인되었습니다.', 'success');

            // 리다이렉트 또는 페이지 이동
            setTimeout(() => {
                window.location.href = result.redirectUrl || '/dashboard';
            }, 500);
        } else {
            loginState.attemptCount++;
            handleLoginError(result.message || '로그인에 실패했습니다.');
        }
    } catch (error) {
        console.error('Login error:', error);
        handleLoginError('서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요.');
    } finally {
        setButtonLoading(loginBtn, false);
        loginBtn.textContent = '로그인';
    }
}

/**
 * 서버에 로그인 요청
 */
async function submitLogin(formData) {
    try {
        // 실제 구현 시 서버 요청
        const response = await fetch('/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                email: formData.email,
                password: formData.password,
                rememberMe: formData.rememberMe
            })
        });

        if (!response.ok) {
            const errorData = await response.json().catch(() => ({}));
            return {
                success: false,
                message: errorData.message || '로그인에 실패했습니다.'
            };
        }

        const data = await response.json();
        return {
            success: true,
            redirectUrl: data.redirectUrl
        };

    } catch (error) {
        console.error('Submit login error:', error);

        // 테스트용 임시 코드
        await simulateAPICall(1500);

        // 테스트: admin@test.com / password123 으로 성공
        if (formData.email === 'admin@test.com' && formData.password === 'password123') {
            return { success: true, redirectUrl: '/dashboard' };
        }

        return { success: false, message: '이메일 또는 비밀번호가 일치하지 않습니다.' };
    }
}

/**
 * 로그인 에러 처리
 */
function handleLoginError(message) {
    showMessage(message, 'error');

    // 비밀번호 필드 초기화
    const passwordInput = document.getElementById('password');
    passwordInput.value = '';
    passwordInput.focus();

    // 남은 시도 횟수 표시
    const remainingAttempts = loginState.maxAttempts - loginState.attemptCount;
    if (remainingAttempts <= 2 && remainingAttempts > 0) {
        showMessage(`남은 로그인 시도 횟수: ${remainingAttempts}회`, 'warning');
    }
}

/**
 * 저장된 이메일 확인
 */
function checkRememberedEmail() {
    const savedEmail = localStorage.getItem('rememberedEmail');
    if (savedEmail) {
        const emailInput = document.getElementById('email');
        const rememberMe = document.getElementById('rememberMe');

        if (emailInput && rememberMe) {
            emailInput.value = savedEmail;
            rememberMe.checked = true;
            loginState.rememberMe = true;
        }
    }
}

/**
 * 로그인 폼 일시 비활성화
 */
function disableLoginForm(duration) {
    const form = document.getElementById('loginForm');
    const inputs = form.querySelectorAll('input, button');

    inputs.forEach(input => {
        input.disabled = true;
    });

    let remainingTime = duration / 1000;
    const loginBtn = document.querySelector('.login-btn');
    const originalText = loginBtn.textContent;

    const timer = setInterval(() => {
        remainingTime--;
        loginBtn.textContent = `${remainingTime}초 후 재시도 가능`;

        if (remainingTime <= 0) {
            clearInterval(timer);
            inputs.forEach(input => {
                input.disabled = false;
            });
            loginBtn.textContent = originalText;
            loginState.attemptCount = 0;
        }
    }, 1000);
}