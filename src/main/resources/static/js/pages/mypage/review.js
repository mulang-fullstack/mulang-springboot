document.querySelector('.sort-select').addEventListener('change', function() {
    const sortValue = this.value;
    window.location.href = '/student/review?sort=' + sortValue;
});
