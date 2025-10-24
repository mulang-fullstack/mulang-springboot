/**
 * 전역 비동기 페이지네이션 스크립트
 * 모든 페이지에서 공용 사용 (changePage() 호출 기반)
 *
 * 필요:
 *   window.paginationData = { currentPage, totalPages }
 *   window.changePage(page) 함수 존재해야 함
 *   <div id="pagination"></div> 컨테이너 존재
 */

(function () {

    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', initPagination);
    } else {
        initPagination();
    }

    function initPagination() {
        const container = document.getElementById('pagination');
        if (!container) return;
        if (typeof window.paginationData === 'undefined') return;

        const { currentPage = 0, totalPages = 1 } = window.paginationData;

        container.innerHTML = '';

        // 처음
        container.appendChild(createButton('«', 0, currentPage === 0));
        // 이전
        container.appendChild(createButton('‹', currentPage - 1, currentPage === 0));

        // 번호 버튼
        getPageNumbers(currentPage, totalPages).forEach(num => {
            if (num === '...') {
                const span = document.createElement('span');
                span.textContent = '...';
                span.className = 'ellipsis';
                container.appendChild(span);
            } else {
                const btn = createButton(num + 1, num, false, num === currentPage);
                container.appendChild(btn);
            }
        });

        // 다음
        container.appendChild(createButton('›', currentPage + 1, currentPage === totalPages - 1));
        // 마지막
        container.appendChild(createButton('»', totalPages - 1, currentPage === totalPages - 1));

        // 페이지 정보
        const info = document.createElement('span');
        info.className = 'page-info';
        info.textContent = `${currentPage + 1} / ${totalPages} 페이지`;
        container.appendChild(info);
    }

    function createButton(label, page, disabled = false, active = false) {
        const btn = document.createElement('button');
        btn.textContent = label;
        if (active) btn.classList.add('active');
        btn.disabled = disabled;
        btn.addEventListener('click', () => {
            if (typeof window.changePage === 'function') {
                window.changePage(page);
            } else {
            }
        });
        return btn;
    }

    function getPageNumbers(currentPage, totalPages) {
        const delta = 2;
        const range = [];
        const withDots = [];
        let last;

        range.push(0);
        for (let i = currentPage - delta; i <= currentPage + delta; i++) {
            if (i > 0 && i < totalPages - 1) range.push(i);
        }
        if (totalPages > 1) range.push(totalPages - 1);

        for (let i of range) {
            if (last !== undefined) {
                if (i - last === 2) withDots.push(last + 1);
                else if (i - last > 1) withDots.push('...');
            }
            withDots.push(i);
            last = i;
        }
        return withDots.filter(i => i === '...' || (i >= 0 && i < totalPages));
    }

    // 외부에서 재호출 가능하도록 export
    window.renderPagination = initPagination;
})();
