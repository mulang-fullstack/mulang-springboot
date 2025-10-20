// 수정 버튼 클릭 시 모달 열기
document.querySelector('.submit-btn').addEventListener('click', function (e) {
    e.preventDefault(); // 폼 바로 제출 방지
    document.getElementById('passwordCheckModal').style.display = 'flex';
});

// 모달 닫기 함수
function closePasswordModal() {
    document.getElementById('passwordCheckModal').style.display = 'none';
}
