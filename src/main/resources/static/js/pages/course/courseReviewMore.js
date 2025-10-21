document.querySelectorAll('.review-more').forEach(btn => {
    btn.addEventListener('click', () => {
        const content = btn.previousElementSibling;
        const isExpanded = content.classList.toggle('expanded');
        btn.textContent = isExpanded ? '접기' : '더보기';
    });
});