document.addEventListener("click", e => {
    // 메뉴 버튼 열기
    if (e.target.classList.contains("menu-btn")) {
        const dropdown = e.target.nextElementSibling;
        dropdown.classList.toggle("show");
    }

    // 드롭다운 외부 클릭 시 닫기
    if (!e.target.closest(".menu-wrap")) {
        document.querySelectorAll(".menu-dropdown.show")
            .forEach(d => d.classList.remove("show"));
    }
});
