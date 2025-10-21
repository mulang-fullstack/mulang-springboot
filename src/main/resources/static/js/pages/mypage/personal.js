
document.getElementById('editForm').addEventListener('submit', async function(e) {
    e.preventDefault(); // 기존 form 제출 막기 (새로고침 방지)

    try {
        // fetch로 GET 요청 보내기
        const response = await fetch('/mypage/edit', { method: 'GET' });

        if (response.ok) {
            // 정상적으로 응답이 왔다면 다음 페이지로 이동
            window.location.href = '/student/edit';
        } else {
            alert('요청 실패! 서버 응답 상태: ' + response.status);
        }
    } catch (error) {
        console.error('에러 발생:', error);
        alert('서버 요청 중 오류가 발생했습니다.');
    }
});

document.getElementById('editpassword').addEventListener('submit', async function(e) {
    e.preventDefault();

    try {
        // fetch 경로 수정
        const response = await fetch('/student/passwordchange', { method: 'GET' });

        if (response.ok) {
            window.location.href = '/student/passwordchange';
        } else {
            alert('요청 실패! 서버 응답 상태: ' + response.status);
        }
    } catch (error) {
        console.error('에러 발생:', error);
        alert('서버 요청 중 오류가 발생했습니다.');
    }
});




