// 찜 해제 함수 (페이지 새로고침 없이)
function removeFavorite(courseId, favoriteId) {
    if (!confirm('찜을 해제하시겠습니까?')) {
        return;
    }

    fetch(`/delete/${courseId}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (response.ok) {
                // 해당 항목만 DOM에서 제거
                const item = document.querySelector(`[data-favorite-id="${favoriteId}"]`);
                if (item) {
                    item.style.transition = 'opacity 0.3s';
                    item.style.opacity = '0';
                    setTimeout(() => {
                        item.remove();

                        // 목록이 비었는지 확인
                        const list = document.querySelector('.likes-list');
                        const items = list.querySelectorAll('.like-item');
                        if (items.length === 0) {
                            list.innerHTML = '<p style="text-align: center; padding: 50px;">찜한 강좌가 없습니다.</p>';
                        }
                    }, 300);
                }

                alert('찜이 해제되었습니다.');
            } else {
                alert('찜 해제에 실패했습니다.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('오류가 발생했습니다.');
        });
}
