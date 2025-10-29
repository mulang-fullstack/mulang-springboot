// ==================== 강좌 액션 함수들 ====================
let courseToDelete = null;

/**
 * 강좌 수정 페이지로 이동
 */
function editCourse(courseId) {
    window.location.href = `/admin/content/course/edit/${courseId}`;
}