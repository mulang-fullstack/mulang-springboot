document.addEventListener('DOMContentLoaded', () => {
    const area = document.getElementById('profileArea');
    const fileInput = document.getElementById('fileInput');
    if (!area || !fileInput) return;


    const current = area.dataset.current || '';
    const fallback = area.dataset.default || '/img/dummy/default_profile.png';
    const img = document.createElement('img');
    img.src = current || fallback;
    img.className = 'profile-photo';
    img.alt = '프로필 이미지';
    img.onerror = () => img.src = fallback;
    area.appendChild(img);


    const cameraBtn = document.createElement('button');
    cameraBtn.type = 'button';
    cameraBtn.className = 'camera-btn';
    cameraBtn.innerHTML = '<img src="/img/icon/bx-camera.svg" alt="사진 변경">';
    area.appendChild(cameraBtn);


    cameraBtn.addEventListener('click', () => fileInput.click());


    fileInput.addEventListener('change', e => {
        const file = e.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = ev => (img.src = ev.target.result);
            reader.readAsDataURL(file);
        }
    });
});
document.addEventListener("DOMContentLoaded", () => {
    const imgEl = document.getElementById("profileImg");
    if (!imgEl) return;

    const fallback = "/img/dummy/default-profile.png";

    if (!imgEl.src || imgEl.src.trim() === "" || imgEl.src.endsWith("/null")) {
        imgEl.src = fallback;
    }

    imgEl.onerror = () => {
        imgEl.src = fallback;
    };
});
document.addEventListener("DOMContentLoaded", () => {
    const input = document.getElementById("fileInput");
    const img = document.getElementById("profileImg");

    if (!input || !img) return;

    input.addEventListener("change", e => {
        const file = e.target.files[0];
        if (!file) return;

        const reader = new FileReader();
        reader.onload = ev => {
            img.src = ev.target.result;
        };
        reader.readAsDataURL(file);
    });
});
