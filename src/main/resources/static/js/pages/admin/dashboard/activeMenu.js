document.addEventListener('DOMContentLoaded', () => {
    const activeMenu = document.body.dataset.activeMenu;
    const activeSub = document.body.dataset.activeSubmenu;

    if (activeMenu) {
        const section = document.querySelector(`.menu-section[data-menu="${activeMenu}"]`);
        if (section) section.classList.add('active');
    }

    if (activeSub) {
        const link = document.querySelector(`.submenu a[data-submenu="${activeSub}"]`);
        if (link) link.classList.add('active');
    }
});
