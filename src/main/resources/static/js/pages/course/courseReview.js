document.addEventListener("DOMContentLoaded", () => {
    const courseId = document.querySelector("main").dataset.courseId || window.courseId || document.querySelector("[name=courseId]")?.value;
    const reviewContainer = document.getElementById("review-container");

    let currentPage = 0;
    let sortBy = "rating";

    // 리뷰 불러오기
    function loadReviews(page = 0, sort = sortBy) {
        fetch(`/course/courseDetail/reviews?courseId=${courseId}&page=${page}&sortBy=${sort}`)
            .then(res => res.json())
            .then(data => {
                renderReviews(data);
                currentPage = data.currentPage;
                sortBy = data.sortBy;
            });
    }

    // 리뷰 렌더링
    function renderReviews(data) {
        const { reviews, currentPage, totalPages, sortBy } = data;

        let html = `
        <div class="review-sort">
            <button class="sort-item ${sortBy === 'rating' ? 'active' : ''}" data-sort="rating">별점순</button>
            <button class="sort-item ${sortBy === 'createdAt' ? 'active' : ''}" data-sort="createdAt">최신순</button>
        </div>
        <div class="review-list">
        `;

        if (reviews.length === 0) {
            html += `
                <div class="empty-review">                  
                    <p>현재 등록된 리뷰가 없습니다.</p>
                </div>`;
        } else {
            reviews.forEach(review => {
                html += `
            <div class="review-item" data-review-id="${review.id}">          
                <div class="review-profile-border">
                    <img src="${review.profileImg}" alt="프로필 이미지" class="review-profile-img">      
                </div>
                <div class="review-name">${review.studentName}</div>
                <div class="rating">
                <span class="stars">
                    ${[1,2,3,4,5].map(i =>
                    `<img src="/img/icon/star-${i <= review.rating ? 'full' : 'empty'}.svg" alt="별">`
                ).join('')}
                </span>
                    <span class="review-score-text">${review.rating}</span>
                    ${review.studentId === currentUserId ? `
                <div class="review-actions">
                    <button class="edit-review">수정</button>
                    <button class="delete-review">삭제</button>
                </div>
                ` : ''}
                </div>               
                <div class="review-content-wrapper">
                    <div class="review-content">${review.content}</div>
                    <div class="review-more">더보기</div>
                </div>                
            </div>`;
            });
        }
        html += `</div><section class="pagination">`;

        if (currentPage > 0)
            html += `<button class="prev" data-page="${currentPage - 1}"><img src="/img/icon/page-left.svg"></button>`;
        html += `<span class="page-numbers">`;
        for (let i = 0; i < totalPages; i++) {
            html += `<button class="page-btn ${i === currentPage ? 'current' : ''}" data-page="${i}">${i + 1}</button>`;
        }
        html += `</span>`;
        if (currentPage < totalPages - 1)
            html += `<button class="next" data-page="${currentPage + 1}"><img src="/img/icon/page-right.svg"></button>`;

        html += `</section>`;
        reviewContainer.innerHTML = html;
        initReviewMore();
    }

    // 이벤트 위임
    reviewContainer.addEventListener("click", (e) => {
        // 수정 버튼
        if (e.target.closest(".edit-review")) {
            const reviewId = e.target.closest(".review-item").dataset.reviewId;
            editReview(reviewId);
            return;
        }

        // 삭제 버튼
        if (e.target.closest(".delete-review")) {
            const reviewId = e.target.closest(".review-item").dataset.reviewId;
            deleteReview(reviewId);
            return;
        }

        // 페이징
        const target = e.target.closest(".page-btn, .prev, .next");
        if (target) {
            const page = target.dataset.page;
            loadReviews(parseInt(page), sortBy);
            return; // 아래 코드 실행 막음
        }

        // 정렬
        if (e.target.closest(".sort-item")) {
            const sort = e.target.closest(".sort-item").dataset.sort;
            if (sort !== sortBy) {
                loadReviews(0, sort);
            }
        }
    });
    // 초기 로드
    loadReviews();
});
function initReviewMore() {
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
            moreBtn.onclick = () => {
                content.classList.toggle('expanded');
                moreBtn.textContent = content.classList.contains('expanded') ? '접기' : '더보기';
            };
        }
    });
}

function editReview(reviewId) {
    // 1. 수정 폼 표시
    const reviewItem = document.querySelector(`.review-item[data-review-id="${reviewId}"]`);
    const contentDiv = reviewItem.querySelector('.review-content');
    const originalContent = contentDiv.textContent;

    // 간단한 textarea로 교체
    contentDiv.innerHTML = `<textarea class="edit-text">${originalContent}</textarea>
                            <button class="save-edit">저장</button>
                            <button class="cancel-edit">취소</button>`;

    // 2. 저장 버튼 이벤트
    reviewItem.querySelector('.save-edit').onclick = () => {
        const newContent = reviewItem.querySelector('.edit-text').value;

        // 서버에 PATCH/PUT 요청
        fetch(`/courseDetail/reviews/${reviewId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ content: newContent })
        })
            .then(res => res.json())
            .then(data => {
                if (data.success) {
                    contentDiv.textContent = newContent;
                } else {
                    alert('수정 실패');
                }
            });
    };

    // 취소 버튼 이벤트
    reviewItem.querySelector('.cancel-edit').onclick = () => {
        contentDiv.textContent = originalContent;
    };
}