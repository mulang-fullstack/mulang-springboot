document.addEventListener("DOMContentLoaded", () => {
    const fileInput = document.getElementById("fileInput");
    const preview = document.getElementById("profile-preview");
    if (!fileInput || !preview) return;

    fileInput.addEventListener("change", () => {
        const file = fileInput.files[0];
        if (!file) return;
        const reader = new FileReader();
        reader.onload = e => preview.src = e.target.result;
        reader.readAsDataURL(file);
    });
});
