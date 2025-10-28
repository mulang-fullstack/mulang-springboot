// 과목 필터
function filterBySubject() {
    const languageId = document.getElementById('subjectFilter').value;
    const keyword = document.getElementById('searchInput').value.trim();
    const urlParams = new URLSearchParams();

    if (languageId) {
        urlParams.set('languageId', languageId);
    }

    if (keyword) {
        urlParams.set('keyword', keyword);
    }

    urlParams.set('page', '0');
    window.location.href = `/student/save?${urlParams.toString()}`;
}

// 검색 함수
function searchCourse(event) {
    event.preventDefault();  // 폼 기본 제출 방지

    const keyword = document.getElementById('searchInput').value.trim();
    const languageId = document.getElementById('subjectFilter').value;

    if (!keyword) {
        alert('검색어를 입력해주세요.');
        return false;
    }

    const urlParams = new URLSearchParams();

    if (keyword) {
        urlParams.set('keyword', keyword);
    }

    if (languageId) {
        urlParams.set('languageId', languageId);
    }

    urlParams.set('page', '0');
    window.location.href = `/student/save?${urlParams.toString()}`;

    return false;
}

// 찜 해제 함수
function removeFavorite(courseId, favoriteId) {
    if (!confirm('찜을 해제하시겠습니까?')) {
        return;
    }

    const urlParams = new URLSearchParams(window.location.search);
    const currentPage = urlParams.get('page') || 0;
    const languageId = urlParams.get('languageId') || '';
    const keyword = urlParams.get('keyword') || '';

    fetch(`/delete/${courseId}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (response.ok) {
                alert('찜이 해제되었습니다.');

                const items = document.querySelectorAll('.like-item');
                let redirectUrl = '/student/save?';

                if (items.length === 1 && currentPage > 0) {
                    redirectUrl += `page=${currentPage - 1}`;
                } else {
                    redirectUrl += `page=${currentPage}`;
                }

                if (languageId) {
                    redirectUrl += `&languageId=${languageId}`;
                }

                if (keyword) {
                    redirectUrl += `&keyword=${encodeURIComponent(keyword)}`;
                }

                window.location.href = redirectUrl;
            } else {
                alert('찜 해제에 실패했습니다.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('오류가 발생했습니다.');
        });
}
