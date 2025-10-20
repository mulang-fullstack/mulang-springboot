/**
 * 로그인 공통 유틸 함수 모음
 * 메시지, 버튼 로딩, 필드 상태 표시
 */

/**
 * 메시지 표시 (토스트)
 */
function showMessage(message, type = 'info') {
    const existingToast = document.querySelector('.toast-message');
    if (existingToast) existingToast.remove();

    const toast = document.createElement('div');
    toast.className = `toast-message toast-${type}`;
    toast.textContent = message;

    toast.style.cssText = `
        position: fixed;
        top: 20px;
        right: 20px;
        padding: 16px 24px;
        border-radius: 8px;
        color: #fff;
        font-size: 14px;
        font-weight: 500;
        z-index: 10000;
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
        animation: slideInRight 0.3s ease;
        max-width: 320px;
        word-break: keep-all;
    `;

    const colors = {
        success: '#22c55e',
        error: '#dc3545',
        warning: '#f59e0b',
        info: '#3498db'
    };
    toast.style.backgroundColor = colors[type] || colors.info;

    document.body.appendChild(toast);

    setTimeout(() => {
        toast.style.animation = 'slideOutRight 0.3s ease';
        setTimeout(() => toast.remove(), 300);
    }, 3000);
}

/**
 * 버튼 로딩 상태 관리
 */
function setButtonLoading(button, isLoading) {
    if (!button) return;

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
 * 필드 상태 표시
 */
function setFieldStatus(input, status, message) {
    const formGroup = input.closest('.form-group');
    if (!formGroup) return;

    // 기존 상태 제거
    formGroup.classList.remove('error', 'success');
    const existingMessage = formGroup.querySelector('.error-message, .success-message');
    if (existingMessage) existingMessage.remove();

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
    if (!formGroup) return;

    formGroup.classList.remove('error', 'success');
    const msg = formGroup.querySelector('.error-message, .success-message');
    if (msg) msg.remove();
}

/**
 * 비밀번호 표시/숨김 토글
 */
function togglePassword(inputId) {
    const passwordInput = document.getElementById(inputId);
    if (!passwordInput) return;

    const button = event?.currentTarget || event?.target;
    if (!button) return;

    const isVisible = passwordInput.type === 'text';
    passwordInput.type = isVisible ? 'password' : 'text';

    // 아이콘 토글 (data-icon 속성 활용)
    if (button.tagName === 'BUTTON' || button.tagName === 'IMG' || button.tagName === 'I') {
        button.classList.toggle('visible', !isVisible);
        if (button.tagName === 'IMG') {
            button.src = isVisible ? '/img/icon/eyeoff.svg' : '/img/icon/eyeshow.svg';
        }
    }
}