/**
 * 폼 UI 인터랙션 설정
 */
function setupFormUI() {
    const radios = document.querySelectorAll('.radio-label');
    radios.forEach(r => {
        r.addEventListener('click', () => {
            radios.forEach(l => l.classList.remove('active'));
            r.classList.add('active');
            r.style.transform = 'scale(0.98)';
            setTimeout(() => (r.style.transform = 'scale(1)'), 100);
        });
    });
}
