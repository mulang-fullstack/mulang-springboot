document.getElementById('editForm').addEventListener('submit', async function(e) {
    e.preventDefault(); // 기존 form 제출 막기 (새로고침 방지)

    try {
        // fetch로 GET 요청 보내기
        const response = await fetch('/mypage/edit', { method: 'GET' });

        if (response.ok) {
            // 정상적으로 응답이 왔다면 다음 페이지로 이동
            // 방법 ① 서버에서 JSON을 받는다면 JSON 처리
            // const data = await response.json();
            // console.log('응답 데이터:', data);

            // 방법 ② 단순히 다른 페이지로 이동시키기
            window.location.href = '/mypage/edit';
        } else {
            alert('요청 실패! 서버 응답 상태: ' + response.status);
        }
    } catch (error) {
        console.error('에러 발생:', error);
        alert('서버 요청 중 오류가 발생했습니다.');
    }
});