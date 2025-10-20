document.addEventListener("DOMContentLoaded", () => {
    const editBtn = document.getElementById("editBtn");
    const saveBtn = document.getElementById("saveBtn");
    const viewEls = document.querySelectorAll(".view-mode");
    const editEls = document.querySelectorAll(".edit-mode");

    editBtn.addEventListener("click", () => {
        viewEls.forEach(el => el.classList.add("hidden"));
        editEls.forEach(el => el.classList.remove("hidden"));
        editBtn.classList.add("hidden");
        saveBtn.classList.remove("hidden");
    });
});
