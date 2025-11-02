document.addEventListener('DOMContentLoaded', function() {
    // 프로필 드롭다운 토글
    const profileToggle = document.querySelector('.profile-toggle');
    const profileDropdown = document.querySelector('.profile-dropdown');

    if (profileToggle && profileDropdown) {
        profileToggle.addEventListener('click', function(e) {
            e.stopPropagation();
            profileDropdown.classList.toggle('active');
        });

        // 외부 클릭시 드롭다운 닫기
        document.addEventListener('click', function(e) {
            if (!e.target.closest('.profile-area')) {
                profileDropdown.classList.remove('active');
            }
        });
    }

    // 모바일 메뉴 토글
    const mobileToggle = document.querySelector('.mobile-menu-toggle');
    const mobilePanel = document.getElementById('mobilePanel');
    const overlay = document.querySelector('.overlay');

    if (mobileToggle && mobilePanel && overlay) {
        mobileToggle.addEventListener('click', function() {
            const isExpanded = this.getAttribute('aria-expanded') === 'true';

            // 토글 상태 변경
            this.setAttribute('aria-expanded', !isExpanded);
            mobilePanel.setAttribute('aria-hidden', isExpanded);

            // 오버레이 표시/숨김
            if (isExpanded) {
                overlay.setAttribute('hidden', '');
            } else {
                overlay.removeAttribute('hidden');
            }

            // body 스크롤 제어
            document.body.style.overflow = isExpanded ? '' : 'hidden';
        });

        // 오버레이 클릭시 메뉴 닫기
        overlay.addEventListener('click', function() {
            mobileToggle.setAttribute('aria-expanded', 'false');
            mobilePanel.setAttribute('aria-hidden', 'true');
            overlay.setAttribute('hidden', '');
            document.body.style.overflow = '';
        });

        // ESC 키로 메뉴 닫기
        document.addEventListener('keydown', function(e) {
            if (e.key === 'Escape') {
                const isExpanded = mobileToggle.getAttribute('aria-expanded') === 'true';
                if (isExpanded) {
                    mobileToggle.setAttribute('aria-expanded', 'false');
                    mobilePanel.setAttribute('aria-hidden', 'true');
                    overlay.setAttribute('hidden', '');
                    document.body.style.overflow = '';
                }
            }
        });
    }

    // 작성 버튼 클릭 이벤트 (필요시 수정)
    const writeButton = document.querySelector('.write-button');
    if (writeButton) {
        writeButton.addEventListener('click', function() {
            // 작성 페이지로 이동하거나 모달 열기
            window.location.href = '/write.do'; // 실제 경로로 수정하세요
        });
    }
});