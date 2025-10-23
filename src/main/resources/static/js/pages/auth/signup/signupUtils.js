/**
 * 공통 유틸 함수 모음
 * 메시지, 버튼 로딩, 필드 상태 표시
 */
/**
 * 버튼 로딩 상태 관리 (스피너 애니메이션 추가)
 */
function setButtonLoading(button, isLoading) {
    if (isLoading) {
        button.disabled = true;
        button.dataset.originalText = button.textContent;

        // 스피너 HTML 생성
        const spinner = document.createElement('span');
        spinner.className = 'button-spinner';
        spinner.style.cssText = `
            display: inline-block;
            width: 14px;
            height: 14px;
            border: 2px solid rgba(255, 255, 255, 0.3);
            border-top-color: #fff;
            border-radius: 50%;
            animation: spin 0.6s linear infinite;
        `;

        button.innerHTML = '';
        button.appendChild(spinner);

        // 스피너 애니메이션 CSS 추가 (한 번만)
        if (!document.getElementById('spinner-animation')) {
            const style = document.createElement('style');
            style.id = 'spinner-animation';
            style.textContent = `
                @keyframes spin {
                    to { transform: rotate(360deg); }
                }
            `;
            document.head.appendChild(style);
        }
    } else {
        button.disabled = false;
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

/**
 * API 호출 시뮬레이션 (테스트용)
 */
async function simulateAPICall(delay = 1000) {
    return new Promise((resolve) => setTimeout(resolve, delay));
}