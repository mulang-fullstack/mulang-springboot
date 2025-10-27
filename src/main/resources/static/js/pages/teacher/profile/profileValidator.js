fetch("/teacher/mypage/profile/update", {
    method: "POST",
    body: formData
})
    .then(res => {
        if (!res.ok) {
            return res.text().then(msg => { throw new Error(msg); });
        }
        return res;
    })
    .catch(err => {
        alert(err.message);
    });
