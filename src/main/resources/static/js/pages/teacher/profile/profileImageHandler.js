document.addEventListener("DOMContentLoaded", () => {
    const area = document.getElementById("profileArea");
    const fileInput = document.getElementById("fileInput");
    if (!area || !fileInput) return;

    const fallback = "/img/dummy/default_profile.png";
    const current = area.dataset.current || '';
    const img = document.createElement("img");
    img.src = current || fallback;
    img.alt = "프로필 이미지";
    img.className = "profile-photo";
    img.onerror = () => img.src = fallback;
    area.appendChild(img);

    const btn = document.createElement("button");
    btn.type = "button";
    btn.className = "camera-btn";
    btn.innerHTML = '<img src="/img/icon/bx-camera.svg" alt="사진 변경">';
    area.appendChild(btn);
    btn.addEventListener("click", () => fileInput.click());

    fileInput.addEventListener("change", e => {
        const file = e.target.files[0];
        if (!file) return;
        const reader = new FileReader();
        reader.onload = ev => img.src = ev.target.result;
        reader.readAsDataURL(file);
    });
});
