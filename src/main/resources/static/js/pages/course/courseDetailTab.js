document.addEventListener('DOMContentLoaded', () => {
    const tabs = document.querySelectorAll('.tab');
    const sections = document.querySelectorAll('#introduction, #curriculum, #review');
    const header = document.querySelector('header');
    const stickyDiv = document.querySelector('.leture-detail-contents');
    const headerHeight = header?.offsetHeight || 0;

    // 탭 클릭 시 스크롤 이동
    tabs.forEach(tab => {
        tab.addEventListener('click', () => {
            const targetId = tab.dataset.target;
            const target = document.getElementById(targetId);
            if (!target) return;

            // sticky div가 이미 상단에 붙어있다면 높이만큼 offset 추가
            // const stickyOffset = (window.scrollY >= stickyDiv.offsetTop) ? stickyDiv.offsetHeight : 0;

            const targetTop = target.offsetTop - headerHeight - stickyDiv.offsetHeight + 340;

            window.scrollTo({ top: targetTop, behavior: 'smooth' });
        });
    });

    // 스크롤 시 active 탭 업데이트
    window.addEventListener('scroll', () => {
        let current = '';
        sections.forEach(section => {
            const sectionTop = section.offsetTop - headerHeight - stickyDiv.offsetHeight - 0; // 여유 마진
            if (window.scrollY >= sectionTop) {
                current = section.getAttribute('id');
            }
        });

        tabs.forEach(tab => {
            tab.classList.remove('active');
            if (tab.dataset.target === current) {
                tab.classList.add('active');
            }
        });
    });
});