document.addEventListener("DOMContentLoaded", function () {
    const reviewWrappers = document.querySelectorAll('.review-content-wrapper');

    reviewWrappers.forEach(wrapper => {
        const content = wrapper.querySelector('.review-content');
        const moreBtn = wrapper.querySelector('.review-more');

        // CSS line-height 기반으로 실제 줄 수 계산
        const lineHeight = parseInt(window.getComputedStyle(content).lineHeight);
        const lines = Math.round(content.scrollHeight / lineHeight);

        if (lines <= 1) {
            // 한 줄이면 더보기 숨기기
            moreBtn.style.display = 'none';
        } else {
            moreBtn.style.display = 'inline';
            moreBtn.addEventListener('click', () => {
                content.classList.toggle('expanded');
                moreBtn.textContent = content.classList.contains('expanded') ? '접기' : '더보기';
            });
        }
    });
});