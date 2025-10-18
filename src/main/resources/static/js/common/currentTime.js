document.addEventListener("DOMContentLoaded", () => {
    const el = document.getElementById("current-time");

    const updateTime = () => {
        const now = new Date();
        const formatted =
            now.getFullYear() + '.' +
            String(now.getMonth() + 1).padStart(2, '0') + '.' +
            String(now.getDate()).padStart(2, '0') + ' ' +
            String(now.getHours()).padStart(2, '0') + ':' +
            String(now.getMinutes()).padStart(2, '0') + ':' +
            String(now.getSeconds()).padStart(2, '0');
        el.textContent = formatted;
    };

    updateTime();           // 최초 표시
    setInterval(updateTime, 1000); // 1초마다 갱신
});