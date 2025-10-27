// ==================== 필터 및 검색 관련 ====================

// 검색 파라미터 수집
function collectSearchParams() {
    const startDate = document.getElementById('startDate').value;
    const endDate = document.getElementById('endDate').value;
    const language = document.querySelector('input[name="language"]:checked')?.value;
    const status = document.querySelector('input[name="status"]:checked')?.value;
    const keyword = document.getElementById('searchKeyword').value.trim();
    const sortValue = document.getElementById('sortSelect').value;

    // 정렬 파라미터 변환
    let sortBy = 'createdAt';
    let sortDirection = 'DESC';

    switch(sortValue) {
        case 'LATEST':
            sortBy = 'createdAt';
            sortDirection = 'DESC';
            break;
        case 'OLDEST':
            sortBy = 'createdAt';
            sortDirection = 'ASC';
            break;
        case 'COURSE_NAME_ASC':
            sortBy = 'title';
            sortDirection = 'ASC';
            break;
        case 'COURSE_NAME_DESC':
            sortBy = 'title';
            sortDirection = 'DESC';
            break;
        case 'TEACHER_NAME_ASC':
            sortBy = 'teacherName';
            sortDirection = 'ASC';
            break;
        case 'TEACHER_NAME_DESC':
            sortBy = 'teacherName';
            sortDirection = 'DESC';
            break;
    }

    const params = {
        page: 0, // 검색 시 첫 페이지로
        size: 10
    };

    // startDate와 endDate 처리
    if (startDate) {
        params.startDate = startDate + 'T00:00:00';
    }
    if (endDate) {
        params.endDate = endDate + 'T23:59:59';
    }

    // language 처리 (ALL이 아닌 경우만)
    if (language && language !== 'ALL') {
        params.languageId = language;
    }

    // status 처리 (ALL이 아닌 경우만)
    if (status && status !== 'ALL') {
        params.status = status;
    }

    // keyword 처리
    if (keyword) {
        params.keyword = keyword;
    }

    // 정렬
    params.sortBy = sortBy;
    params.sortDirection = sortDirection;

    return params;
}

// 비동기 요청 보내기
async function fetchCourseList(params) {
    try {
        const queryString = new URLSearchParams(params).toString();
        const response = await fetch(`/admin/content/course/api?${queryString}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': document.querySelector('meta[name="csrf-token"]')?.content
            }
        });

        if (!response.ok) {
            throw new Error('데이터를 불러오는데 실패했습니다.');
        }

        const data = await response.json();
        return data;
    } catch (error) {
        console.error('Error fetching course list:', error);
        alert('데이터를 불러오는데 실패했습니다.');
        return null;
    }
}

// 테이블 렌더링
function renderCourseTable(courses, currentPage, pageSize) {
    const tbody = document.querySelector('table tbody');

    if (!courses || courses.length === 0) {
        tbody.innerHTML = '<tr><td colspan="8" class="no-data">등록된 강좌가 없습니다.</td></tr>';
        return;
    }

    tbody.innerHTML = courses.map((course, index) => {
        const rowNumber = currentPage * pageSize + index + 1;

        // 언어 뱃지
        let languageBadge = '';
        switch(course.languageId) {
            case 1:
                languageBadge = '<span class="language-badge english">영어</span>';
                break;
            case 2:
                languageBadge = '<span class="language-badge chinese">중국어</span>';
                break;
            case 3:
                languageBadge = '<span class="language-badge japanese">일본어</span>';
                break;
        }

        // 상태 뱃지
        let statusBadge = '';
        switch(course.status) {
            case 'PUBLIC':
                statusBadge = '<span class="status-badge public">공개</span>';
                break;
            case 'PRIVATE':
                statusBadge = '<span class="status-badge private">비공개</span>';
                break;
            case 'PENDING':
                statusBadge = '<span class="status-badge pending">심사대기</span>';
                break;
        }

        return `
            <tr data-id="${course.id}"
                data-language="${course.languageId}"
                data-status="${course.status}"
                data-date="${course.createdAt}">
                <td>${rowNumber}</td>
                <td class="course-title">${course.title}</td>
                <td>${languageBadge}</td>
                <td>${course.teacherName}</td>
                <td>${course.teacherNickname}</td>
                <td>${course.createdAt}</td>
                <td>${statusBadge}</td>
                <td class="actions">
                    <button class="btn-detail" onclick="viewCourseDetail(${course.id})">상세</button>
                    <button class="btn-edit" onclick="editCourse(${course.id})">수정</button>
                    <button class="btn-delete" onclick="confirmDelete(${course.id}, '${course.title}')">삭제</button>
                </td>
            </tr>
        `;
    }).join('');
}

// 페이지네이션 업데이트
function updatePagination(currentPage, totalPages) {
    window.paginationData = {
        currentPage: currentPage,
        totalPages: totalPages,
        baseUrl: '/admin/content/course',
        asyncMode: true
    };

    if (typeof renderPagination === 'function') {
        renderPagination();
    }
}

// 검색 실행
async function performSearch() {
    const params = collectSearchParams();
    const data = await fetchCourseList(params);

    if (data) {
        renderCourseTable(data.courses, data.currentPage, data.size);
        updatePagination(data.currentPage, data.totalPages);
    }
}

// 페이지 변경 (pagination.js에서 호출될 함수)
window.changePage = async function(page) {
    const params = collectSearchParams();
    params.page = page;

    const data = await fetchCourseList(params);

    if (data) {
        renderCourseTable(data.courses, data.currentPage, data.size);
        updatePagination(data.currentPage, data.totalPages);
    }
};

// 필터 초기화
function resetFilters() {
    // 날짜 초기화 (현재 월의 1일 ~ 오늘)
    const today = new Date();
    const firstDay = new Date(today.getFullYear(), today.getMonth(), 1);

    document.getElementById('startDate').value = formatDate(firstDay);
    document.getElementById('endDate').value = formatDate(today);

    // 라디오 버튼 초기화
    document.querySelector('input[name="language"][value="ALL"]').checked = true;
    document.querySelector('input[name="status"][value="ALL"]').checked = true;

    // 검색어 초기화
    document.getElementById('searchKeyword').value = '';

    // 정렬 초기화
    document.getElementById('sortSelect').value = 'LATEST';

    // 검색 실행
    performSearch();
}

// 날짜 포맷팅 함수
function formatDate(date) {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
}

// ==================== 이벤트 리스너 ====================

document.addEventListener('DOMContentLoaded', function() {
    // 날짜 초기값 설정
    const today = new Date();
    const firstDay = new Date(today.getFullYear(), today.getMonth(), 1);

    document.getElementById('startDate').value = formatDate(firstDay);
    document.getElementById('endDate').value = formatDate(today);

    // 초기 데이터 로드
    performSearch();

    // 검색 버튼 클릭
    document.querySelector('.search-btn').addEventListener('click', performSearch);

    // 검색어 입력 시 엔터키
    document.getElementById('searchKeyword').addEventListener('keypress', function(e) {
        if (e.key === 'Enter') {
            e.preventDefault();
            performSearch();
        }
    });

    // 날짜 필터 변경
    document.getElementById('startDate').addEventListener('change', performSearch);
    document.getElementById('endDate').addEventListener('change', performSearch);

    // 언어 라디오 버튼
    document.querySelectorAll('input[name="language"]').forEach(radio => {
        radio.addEventListener('change', performSearch);
    });

    // 상태 라디오 버튼
    document.querySelectorAll('input[name="status"]').forEach(radio => {
        radio.addEventListener('change', performSearch);
    });

    // 정렬 선택
    document.getElementById('sortSelect').addEventListener('change', performSearch);

    // 초기화 버튼은 이미 onclick으로 연결되어 있음
});

// ==================== 기존 함수들 (모달 등) ====================

let courseToDelete = null;

function confirmDelete(courseId, courseTitle) {
    courseToDelete = courseId;

    // 삭제 확인 모달이 있다면
    const modal = document.getElementById('deleteModal');
    if (modal) {
        document.getElementById('deleteCourseTitle').textContent = courseTitle;
        modal.style.display = 'flex';
    } else {
        // 모달이 없으면 confirm 사용
        if (confirm(`"${courseTitle}" 강좌를 정말 삭제하시겠습니까?\n이 작업은 되돌릴 수 없습니다.`)) {
            executeDelete();
        }
    }
}

function closeDeleteModal() {
    const modal = document.getElementById('deleteModal');
    if (modal) {
        modal.style.display = 'none';
    }
    courseToDelete = null;
}

async function executeDelete() {
    if (!courseToDelete) return;

    try {
        const response = await fetch(`/admin/content/course/${courseToDelete}`, {
            method: 'DELETE',
            headers: {
                'X-CSRF-TOKEN': document.querySelector('meta[name="csrf-token"]')?.content
            }
        });

        if (response.ok) {
            alert('강좌가 삭제되었습니다.');
            closeDeleteModal();
            performSearch(); // 목록 새로고침
        } else {
            alert('삭제에 실패했습니다.');
        }
    } catch (error) {
        console.error('Delete error:', error);
        alert('삭제 중 오류가 발생했습니다.');
    }
}

function viewCourseDetail(courseId) {
    // 강좌 상세 페이지로 이동
    window.location.href = `/admin/content/course/${courseId}`;
}

function editCourse(courseId) {
    // 강좌 수정 페이지로 이동
    window.location.href = `/admin/content/course/edit/${courseId}`;
}

// 모달 외부 클릭 시 닫기
window.onclick = function(event) {
    const modal = document.getElementById('deleteModal');
    if (modal && event.target === modal) {
        closeDeleteModal();
    }
};