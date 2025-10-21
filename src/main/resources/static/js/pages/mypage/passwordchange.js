// passwordchange.js
document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('passwordChangeForm');
    const newPassword = document.getElementById('newpassword');
    const confirmPassword = document.getElementById('confirmpassword');
    const errorMessage = document.getElementById('passworderror');

    // 비밀번호 확인 검증
    confirmPassword.addEventListener('input', function() {
        if (confirmPassword.value && newPassword.value !== confirmPassword.value) {
            errorMessage.textContent = '비밀번호가 일치하지 않습니다.';
            errorMessage.classList.add('show');
        } else {
            errorMessage.classList.remove('show');
        }
    });

    // 폼 제출
    form.addEventListener('submit', function(e) {
        e.preventDefault();

        // 비밀번호 길이 확인
        if (newPassword.value.length < 6) {
            errorMessage.textContent = '비밀번호는 6자 이상 입력하세요.';
            errorMessage.classList.add('show');
            return;
        }

        // 비밀번호 일치 확인
        if (newPassword.value !== confirmPassword.value) {
            errorMessage.textContent = '비밀번호가 일치하지 않습니다.';
            errorMessage.classList.add('show');
            return;
        }

        // 폼 제출
        form.submit();
    });
});
