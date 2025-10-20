/**
 * 공통 유틸 함수 모음
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
        z-index: 10000;
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
        animation: slideInRight 0.3s ease;
    `;

    const colors = {
        success: '#22c55e',
        error: '#dc3545',
        warning: '#f59e0b',
        info: '#3498db'
    };
    toast.style.backgroundColor = colors[type] || colors.info;

    document.body.appendChild(toast);
    setTimeout(() => toast.remove(), 3000);
}

/**
 * 버튼 로딩 상태 관리
 */
function setButtonLoading(button, isLoading) {
    if (isLoading) {
        button.disabled = true;
        button.dataset.originalText = button.textContent;
        button.textContent = '...';
    } else {
        button.disabled = false;
        if (button.dataset.originalText) button.textContent = button.dataset.originalText;
    }
}

/**
 * 필드 상태 표시
 */
function setFieldStatus(input, status, message) {
    const formGroup = input.closest('.form-group');
    formGroup.classList.remove('error', 'success');
    const old = formGroup.querySelector('.error-message, .success-message');
    if (old) old.remove();

    formGroup.classList.add(status);
    if (message) {
        const div = document.createElement('div');
        div.className = `${status}-message`;
        div.textContent = message;
        formGroup.appendChild(div);
    }
}

/**
 * 필드 상태 초기화
 */
function clearFieldStatus(input) {
    const formGroup = input.closest('.form-group');
    formGroup.classList.remove('error', 'success');
    const msg = formGroup.querySelector('.error-message, .success-message');
    if (msg) msg.remove();
}
