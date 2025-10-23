/**
 * 전역 페이지네이션 스크립트
 * 모든 페이지에서 공용 사용 가능
 * 사용 조건:
 *   window.paginationData = { currentPage, totalPages, baseUrl?, query? }
 *   <div id="pagination"></div> 존재해야 함
 */

(function() {
    console.log('📄 Pagination.js 로드됨');

    // DOM이 완전히 로드된 후 실행
    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', initPagination);
    } else {
        initPagination();
    }

    function initPagination() {
        console.log('🔄 Pagination 초기화 시작');

        const container = document.getElementById('pagination');

        if (!container) {
            console.warn('⚠️ #pagination 컨테이너를 찾을 수 없습니다.');
            return;
        }

        if (typeof window.paginationData === 'undefined') {
            console.warn('⚠️ window.paginationData가 정의되지 않았습니다.');
            return;
        }

        let {
            currentPage = 1,
            totalPages = 1,
            baseUrl = window.location.pathname,
            query = ''
        } = window.paginationData;

        console.log('📊 Pagination 설정:', {
            currentPage,
            totalPages,
            baseUrl,
            query
        });

        // 페이지네이션 생성
        createPagination();

        function createPagination() {
            container.innerHTML = '';
            // 맨 처음
            container.appendChild(createButton('«', 1, currentPage === 1));
            // 이전
            container.appendChild(createButton('‹', currentPage - 1, currentPage === 1));
            // 번호 버튼
            getPageNumbers().forEach(num => {
                if (num === '...') {
                    const span = document.createElement('span');
                    span.textContent = '...';
                    span.className = 'ellipsis';
                    container.appendChild(span);
                } else {
                    const btn = createButton(num, num, false, num === currentPage);
                    container.appendChild(btn);
                }
            });

            // 다음
            container.appendChild(createButton('›', currentPage + 1, currentPage === totalPages));

            // 마지막
            container.appendChild(createButton('»', totalPages, currentPage === totalPages));

            // 페이지 정보
            const info = document.createElement('span');
            info.className = 'page-info';
            info.textContent = `${currentPage} / ${totalPages} 페이지`;
            container.appendChild(info);

            console.log('✅ Pagination 렌더링 완료');
        }

        // 버튼 생성
        function createButton(label, page, disabled = false, active = false) {
            const btn = document.createElement('button');
            btn.innerHTML = label;
            if (active) btn.classList.add('active');
            btn.disabled = disabled;
            btn.addEventListener('click', () => goToPage(page));
            return btn;
        }

        // 표시할 페이지 계산
        function getPageNumbers() {
            const delta = 2;
            const range = [];
            const withDots = [];
            let last;

            range.push(1);
            for (let i = currentPage - delta; i <= currentPage + delta; i++) {
                if (i > 1 && i < totalPages) range.push(i);
            }
            if (totalPages > 1) range.push(totalPages);

            for (let i of range) {
                if (last) {
                    if (i - last === 2) withDots.push(last + 1);
                    else if (i - last > 1) withDots.push('...');
                }
                withDots.push(i);
                last = i;
            }
            return withDots;
        }

        // 페이지 이동
        function goToPage(page) {
            if (page < 1 || page > totalPages) {
                console.warn('⚠️ 잘못된 페이지 번호:', page);
                return;
            }

            console.log('📍 페이지 이동:', page);

            const params = new URLSearchParams(window.location.search);
            params.set('page', page);

            if (query) {
                params.set('keyword', query);
            }

            window.location.href = `${baseUrl}?${params.toString()}`;
        }

        // goToPage를 전역으로 노출
        window.goToPage = goToPage;
    }
})();