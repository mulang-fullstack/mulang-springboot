// 프로필 이미지 미리보기
const fileInput = document.getElementById('fileInput');
const profilePreview = document.getElementById('profile-preview');

if (fileInput && profilePreview) {
    fileInput.addEventListener('change', () => {
        const file = fileInput.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = e => profilePreview.src = e.target.result;
            reader.readAsDataURL(file);
        }
    });
}

// 상태 관리
const editState = {
    isEmailChanged: false,
    isEmailCodeVerified: false,
    originalEmail: '',
    emailValue: '',
    isVerifying: false
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
                    verifyBtn.classList.remove('verified');
                    verifyBtn.disabled = false;
                    verifyBtn.style.backgroundColor = '';
                    verifyBtn.style.color = '';
                }

                // 인증코드 입력창 숨기기
                const emailCodeGroup = document.querySelector('.email-code-group');
                if (emailCodeGroup) {
                    emailCodeGroup.classList.remove('show');
                    emailCodeGroup.classList.add('hidden');
                }
            }
        });
    }
});

/**
 * 인증하기 버튼 클릭 - 서버에 인증코드 전송 요청
 */
async function verifyEmail() {
    // 이미 처리 중이면 무시
    if (editState.isVerifying) {
        return;
    }

    const emailInput = document.getElementById('email');
    const email = emailInput.value.trim();
    const verifyBtn = event.target;

    // 이메일 유효성 검사
    if (!email || !email.includes('@')) {
        alert('올바른 이메일 주소를 입력해주세요.');
        return;
    }

    editState.isVerifying = true;
    verifyBtn.disabled = true;
    const originalText = verifyBtn.textContent;
    verifyBtn.textContent = '전송 중...';

    try {
        // 서버에 인증코드 전송 요청
        const response = await fetch('/student/editemail', {
            method: 'POST',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            body: new URLSearchParams({ email: email })
        });

        if (response.ok) {
            // 페이지 새로고침으로 인증코드 입력창 표시
            const html = await response.text();
            document.open();
            document.write(html);
            document.close();
        } else {
            alert('인증코드 전송에 실패했습니다.');
            verifyBtn.textContent = originalText;
        }
    } catch (error) {
        console.error('에러:', error);
        alert('서버 요청 중 오류가 발생했습니다.');
        verifyBtn.textContent = originalText;
    } finally {
        editState.isVerifying = false;
        verifyBtn.disabled = false;
    }
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

    // 인증코드 유효성 검사
    if (!emailCode || emailCode.length !== 6) {
        alert('6자리 인증코드를 입력해주세요.');
        return;
    }

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
            // HTML 응답을 받아서 페이지 교체
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
/**
 * 인증하기 버튼 클릭 - 로딩 스피너 포함
 */
async function verifyEmailWithLoading() {
    const verifyBtn = document.getElementById('verifyEmailBtn');
    if (!verifyBtn) return;

    const btnText = verifyBtn.querySelector('.btn-text');
    const spinner = verifyBtn.querySelector('.spinner');
    const emailInput = document.getElementById('email');

    if (!emailInput) {
        alert('이메일 입력란을 찾을 수 없습니다.');
        return;
    }

    const email = emailInput.value.trim();

    // 이메일 유효성 검사
    if (!email || !email.includes('@')) {
        alert('올바른 이메일 주소를 입력해주세요.');
        return;
    }

    // 버튼 비활성화 및 스피너 표시
    verifyBtn.disabled = true;
    if (btnText) btnText.style.display = 'none';
    if (spinner) spinner.style.display = 'inline-block';

    try {
        const response = await fetch('/student/editemail', {
            method: 'POST',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            body: new URLSearchParams({ email: email })
        });

        if (response.ok) {
            // 서버에서 받은 HTML로 페이지 전체 교체
            const html = await response.text();
            document.open();
            document.write(html);
            document.close();
        } else {
            const errorText = await response.text();
            alert('인증코드 전송에 실패했습니다.');
            console.error('서버 응답:', errorText);

            // 복원
            if (btnText) btnText.style.display = 'inline';
            if (spinner) spinner.style.display = 'none';
            verifyBtn.disabled = false;
        }
    } catch (error) {
        console.error('에러:', error);
        alert('서버 요청 중 오류가 발생했습니다.');

        // 복원
        if (btnText) btnText.style.display = 'inline';
        if (spinner) spinner.style.display = 'none';
        verifyBtn.disabled = false;
    }
}
