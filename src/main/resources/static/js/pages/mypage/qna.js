document.querySelector('.sort-select').addEventListener('change', function() {
    const sortValue = this.value;
    window.location.href = '/student/qna?sort=' + sortValue;
});
