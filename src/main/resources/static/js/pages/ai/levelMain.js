/**
 * 팝업 열기
 */
function openAiLevelPopup() {
    const width = 900;
    const height = 900;
    const left = (screen.width / 2) - (width / 2);
    const top = (screen.height / 2) - (height / 2);

    const popup = window.open(
        '/ai/level/test',
        'aiLevelTest',
        `width=${width},height=${height},left=${left},top=${top},scrollbars=yes,resizable=yes`
    );

    if (!popup) {
        alert('팝업이 차단되었습니다. 팝업 차단을 해제해주세요.');
    }
}