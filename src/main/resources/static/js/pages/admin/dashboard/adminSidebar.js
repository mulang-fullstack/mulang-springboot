document.addEventListener('DOMContentLoaded', () => {
    const sections = document.querySelectorAll('.menu-section');

    sections.forEach(section => {
        const button = section.querySelector('button');
        const submenu = section.querySelector('.submenu');

        button.addEventListener('click', e => {
            e.stopPropagation();

            // 다른 메뉴 닫기
            sections.forEach(s => {
                if (s !== section) s.classList.remove('open');
            });

            // 현재 메뉴 토글
            section.classList.toggle('open');
        });
    });

    // 바깥 클릭 시 모두 닫기
    document.addEventListener('click', () => {
        sections.forEach(s => s.classList.remove('open'));
    });
});
