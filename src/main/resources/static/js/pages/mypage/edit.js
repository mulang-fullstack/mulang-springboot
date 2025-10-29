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

// 상태 관리
const editState = {
    isEmailChanged: false,
    isEmailCodeVerified: false,
    originalEmail: '',
    emailValue: '',
    isVerifying: false  // 추가
};

// 페이지 로드 시 초기화
document.addEventListener('DOMContentLoaded', function() {
    const originalEmailInput = document.getElementById('originalEmail');
    if (originalEmailInput) {
        editState.originalEmail = originalEmailInput.value;
    }

    // 이메일 변경 감지
    const emailInput = document.getElementById('email');
    if (emailInput) {
        emailInput.addEventListener('input', function() {
            const currentEmail = this.value.trim();
            editState.isEmailChanged = (currentEmail !== editState.originalEmail);

            if (editState.isEmailChanged) {
                // 이메일이 변경되면 인증 초기화
                editState.isEmailCodeVerified = false;

                const verifyBtn = document.querySelector('.verify-btn');
                if (verifyBtn) {
                    verifyBtn.textContent = '인증하기';
                    verifyBtn.classList.remove('resend');
                }
            }
        });
    }

    // 파일 선택 시 미리보기
    const fileInput = document.getElementById('fileInput');
    if (fileInput) {
        fileInput.addEventListener('change', function(e) {
            const file = e.target.files[0];
            if (file) {
                const reader = new FileReader();
                reader.onload = function(e) {
                    document.getElementById('profile-preview').src = e.target.result;
                }
                reader.readAsDataURL(file);
            }
        });
    }
});

/**
 * 인증하기 버튼 클릭 - 인증코드 입력창 표시
 */
function verifyEmail() {
    // 이미 처리 중이면 무시
    if (editState.isVerifying) {
        return;
    }

    const emailInput = document.getElementById('email');
    const email = emailInput.value.trim();
    const emailCodeGroup = document.querySelector('.email-code-group');
    const verifyBtn = event.target;

    // 인증코드 입력창 표시
    if (emailCodeGroup) {
        emailCodeGroup.classList.remove('hidden');
        emailCodeGroup.classList.add('show');
    }

    const codeInput = document.getElementById('emailCode');
    if (codeInput) {
        codeInput.disabled = false;
        codeInput.value = '';
        codeInput.focus();
    }

    // 이메일 값 저장
    editState.emailValue = email;

    // 버튼 텍스트를 "재전송"으로 변경
    verifyBtn.textContent = '재전송';
    verifyBtn.classList.add('resend');
}

/**
 * 인증코드 확인 POST 요청
 */
async function submitVerifyCode() {
    // 이미 처리 중이면 무시
    if (editState.isVerifying) {
        return;
    }

    const email = document.getElementById('email').value.trim();
    const emailCode = document.getElementById('emailCode').value.trim();
    const verifyBtn = document.querySelector('.verify-code-btn');

    // 로딩 상태 시작
    editState.isVerifying = true;
    verifyBtn.disabled = true;
    verifyBtn.textContent = '확인 중...';

    try {
        const response = await fetch('/student/verifycode', {
            method: 'POST',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            body: new URLSearchParams({
                email: email,
                emailCode: emailCode
            })
        });

        if (response.ok) {
            // 새로고침 대신 HTML 응답을 받아서 페이지 교체
            const html = await response.text();
            document.open();
            document.write(html);
            document.close();
        } else {
            alert('인증 확인 중 오류가 발생했습니다.');
        }
    } catch (error) {
        console.error('에러:', error);
        alert('서버 요청 중 오류가 발생했습니다.');
    } finally {
        // 로딩 상태 종료
        editState.isVerifying = false;
        verifyBtn.disabled = false;
        verifyBtn.textContent = '확인';
    }
}
