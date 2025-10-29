document.addEventListener("DOMContentLoaded", () => {
    const el = document.getElementById("current-time");

    const updateTime = () => {
        const now = new Date();
        const formatted =
            now.getFullYear() + '.' +
            String(now.getMonth() + 1).padStart(2, '0') + '.' +
            String(now.getDate()).padStart(2, '0') + ' ' +
            String(now.getHours()).padStart(2, '0') + ':' +
            String(now.getMinutes()).padStart(2, '0') + ':' +
            String(now.getSeconds()).padStart(2, '0');
        el.textContent = formatted;
    };

    updateTime();           // 최초 표시
    setInterval(updateTime, 1000); // 1초마다 갱신
});

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
 * 텍스트 길이 제한 및 툴팁 표시
 * @param {string} text - 원본 텍스트
 * @param {number} maxLength - 최대 길이 (기본값: 10)
 * @returns {string} HTML 문자열
 */
function truncateText(text, maxLength = 10) {
    if (!text) return '-';

    const textString = String(text);

    if (textString.length <= maxLength) {
        return textString;
    }

    const truncated = textString.substring(0, maxLength) + '...';
    const escaped = textString.replace(/"/g, '&quot;').replace(/'/g, '&#39;');

    return `<span class="truncate-text" data-tooltip="${escaped}">${truncated}</span>`;
}

/**
 * 툴팁 초기화 (페이지 로드 시 한 번만 실행)
 */
function initTooltips() {
    // 이미 초기화되었으면 리턴
    if (window.tooltipsInitialized) return;

    // 스타일 추가
    if (!document.getElementById('tooltip-styles')) {
        const style = document.createElement('style');
        style.id = 'tooltip-styles';
        style.textContent = `
            .truncate-text {
                position: relative;
                cursor: help;
                display: inline-block;
            }
            
            .truncate-text::after {
                content: attr(data-tooltip);
                position: absolute;
                bottom: 125%;
                left: 50%;
                transform: translateX(-50%);
                padding: 8px 12px;
                background: rgba(0, 0, 0, 0.9);
                color: #fff;
                font-size: 13px;
                line-height: 1.4;
                border-radius: 6px;
                white-space: pre-wrap;
                word-break: break-word;
                max-width: 300px;
                z-index: 10001;
                opacity: 0;
                visibility: hidden;
                transition: opacity 0.2s ease, visibility 0.2s ease;
                pointer-events: none;
            }
            
            .truncate-text::before {
                content: '';
                position: absolute;
                bottom: 115%;
                left: 50%;
                transform: translateX(-50%);
                border: 6px solid transparent;
                border-top-color: rgba(0, 0, 0, 0.9);
                opacity: 0;
                visibility: hidden;
                transition: opacity 0.2s ease, visibility 0.2s ease;
                z-index: 10001;
                pointer-events: none;
            }
            
            .truncate-text:hover::after,
            .truncate-text:hover::before {
                opacity: 1;
                visibility: visible;
            }
            
            /* 툴팁이 화면 밖으로 나가지 않도록 조정 */
            @media (max-width: 768px) {
                .truncate-text::after {
                    max-width: 200px;
                    font-size: 12px;
                }
            }
        `;
        document.head.appendChild(style);
    }

    window.tooltipsInitialized = true;
}

// 페이지 로드 시 툴팁 초기화
if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', initTooltips);
} else {
    initTooltips();
}