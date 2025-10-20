 const fileInput = document.getElementById('fileInput');
    const profilePreview = document.getElementById('profile-preview');

    fileInput.addEventListener('change', () => {
        const file = fileInput.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = e => profilePreview.src = e.target.result;
            reader.readAsDataURL(file);
        }
    });
