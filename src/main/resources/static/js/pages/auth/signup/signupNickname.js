/**
 * 닉네임 실시간 중복 확인
 */
let nicknameCheckTimeout = null;

/**
 * 닉네임 중복 확인 (비동기)
 */
async function checkNicknameAsync(nickname) {
    const nicknameInput = document.getElementById('nickname');

    // 2자 미만이면 검사하지 않음
    if (nickname.length < 2) {
        signupState.isNicknameVerified = false;
        clearFieldStatus(nicknameInput);
        return;
    }

    try {
        const response = await fetch(`/auth/nickname/check`, {
            method: 'POST',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            body: new URLSearchParams({nickname})
        });

        if (!response.ok) throw new Error('서버 오류');
        const result = await response.text();

        // 입력값이 변경되었으면 무시
        if (nicknameInput.value.trim() !== nickname) return;

        if (result === "duplicate") {
            setFieldStatus(nicknameInput, 'error', '이미 사용 중인 닉네임입니다.');
            signupState.isNicknameVerified = false;
            signupState.nicknameValue = '';
        } else if (result === "available") {
            setFieldStatus(nicknameInput, 'success', '사용 가능한 닉네임입니다.');
            signupState.isNicknameVerified = true;
            signupState.nicknameValue = nickname;
        }
    } catch (error) {
        console.error('닉네임 확인 오류:', error);
        signupState.isNicknameVerified = false;
        signupState.nicknameValue = '';
    }
}