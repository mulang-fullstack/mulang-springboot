document.addEventListener('DOMContentLoaded', function() {
    const reviewForm = document.getElementById('reviewForm');
    const courseSelect = document.getElementById('title');
    const ratingInputs = document.querySelectorAll('input[name="rating"]');
    const contentTextarea = document.getElementById('content');

    reviewForm.addEventListener('submit', function(e) {
        // 강좌 선택 검증
        if (courseSelect.value === '') {
            e.preventDefault();
            courseSelect.setCustomValidity('강좌를 선택하세요');
            courseSelect.reportValidity();
            return;
        }
        courseSelect.setCustomValidity('');

        // 별점 선택 검증
        const isRatingSelected = Array.from(ratingInputs).some(input => input.checked);
        if (!isRatingSelected) {
            e.preventDefault();
            alert('별점을 선택하세요');
            return;
        }

        // 내용 입력 검증
        if (contentTextarea.value.trim() === '') {
            e.preventDefault();
            contentTextarea.setCustomValidity('리뷰 내용을 입력하세요');
            contentTextarea.reportValidity();
            return;
        }
        contentTextarea.setCustomValidity('');
    });

    // 강좌 선택 시 에러 제거
    courseSelect.addEventListener('change', function() {
        this.setCustomValidity('');
    });

    // 내용 입력 시 에러 제거
    contentTextarea.addEventListener('input', function() {
        this.setCustomValidity('');
    });
});
