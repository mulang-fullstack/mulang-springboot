document.addEventListener('DOMContentLoaded', () => {
    const aside = document.querySelector('aside');
    if (!aside) return;

    const activeMenu = aside.dataset.activeMenu;
    const activeSub = aside.dataset.activeSubmenu;

    if (activeMenu) {
        const section = aside.querySelector(`.menu-section[data-menu="${activeMenu}"]`);
        if (section) section.classList.add('active');
    }

    if (activeSub) {
        const link = aside.querySelector(`.submenu a[data-submenu="${activeSub}"]`);
        if (link) link.classList.add('active');
    }
});
