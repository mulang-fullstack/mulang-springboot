/**
 * API 관련 함수 (Mock or 실제 통신)
 */
function simulateAPICall(delay = 1000) {
    return new Promise(resolve => setTimeout(resolve, delay));
}
