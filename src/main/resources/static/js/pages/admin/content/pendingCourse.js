// ==================== 전역 변수 ====================
let isSearching = false; // 중복 요청 방지

// ==================== API 요청 ====================
async function fetchCourseList(params) {
    if (isSearching) return null;

    try {
        isSearching = true;
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
        alert('데이터를 불러오는데 실패했습니다.');
        return null;
    } finally {
        isSearching = false;
    }
}

// ==================== 파라미터 수집 ====================
function collectSearchParams() {
    const startDateInput = document.getElementById('startDate');
    const endDateInput = document.getElementById('endDate');
    const languageRadio = document.querySelector('input[name="language"]:checked');
    const statusRadio = document.querySelector('input[name="status"]:checked');
    const keywordInput = document.getElementById('searchKeyword');
    const sortSelect = document.getElementById('sortSelect');

    const startDate = startDateInput?.value || '';
    const endDate = endDateInput?.value || '';
    const language = languageRadio?.value || 'ALL';
    const status = statusRadio?.value || 'ALL';
    const keyword = keywordInput?.value.trim() || '';
    const sortValue = sortSelect?.value || 'LATEST';

    // 정렬 파라미터 변환
    let sortBy = 'createdAt';
    let sortDirection = 'DESC';

    switch (sortValue) {
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
        page: 0,
        size: 10,
        sortBy: sortBy,
        sortDirection: sortDirection
    };

    // 날짜 필터 - 값이 있을 때만 추가
    if (startDate) {
        params.startDate = startDate + 'T00:00:00';
    }
    if (endDate) {
        params.endDate = endDate + 'T23:59:59';
    }

    // 언어 - ALL이 아닐 때만 추가
    if (language && language !== 'ALL') {
        params.languageId = language;
    }

    // 상태 - ALL이 아닐 때만 추가
    if (status && status !== 'ALL') {
        params.status = status;
    }

    // 검색어 - 값이 있을 때만 추가
    if (keyword) {
        params.keyword = keyword;
    }

    console.log('📋 최종 파라미터:', params);
    return params;
}

// ==================== 렌더링 ====================
function renderCourseTable(courses, currentPage, pageSize) {
    const tbody = document.querySelector('table tbody');

    if (!tbody) {
        return;
    }

    if (!courses || courses.length === 0) {
        tbody.innerHTML = '<tr><td colspan="8" class="no-data">등록된 강좌가 없습니다.</td></tr>';
        return;
    }

    tbody.innerHTML = courses.map((course, index) => {
        const rowNumber = currentPage * pageSize + index + 1;

        const courseTitle = course.courseName || course.title || '제목 없음';

        let languageBadge = '';
        switch(course.languageName) {
            case '영어':
            case 'English':
                languageBadge = '<span class="language-badge english">영어</span>';
                break;
            case '중국어':
            case 'Chinese':
                languageBadge = '<span class="language-badge chinese">중국어</span>';
                break;
            case '일본어':
            case 'Japanese':
                languageBadge = '<span class="language-badge japanese">일본어</span>';
                break;
            default:
                languageBadge = `<span class="language-badge default">${course.languageName || '-'}</span>`;
        }

        let statusBadge = '';
        const statusText = course.status === 'PUBLIC' ? '공개' : '비공개';
        statusBadge = `<span class="status-badge ${course.status === 'PUBLIC' ? 'public' : 'private'}">${statusText}</span>`;

        return `
            <tr data-id="${course.id}">
                <td>${rowNumber}</td>
                <td class="course-title">${courseTitle}</td>
                <td>${languageBadge}</td>
                <td>${course.teacherName || '-'}</td>
                <td>${course.teacherNickname || '-'}</td>
                <td>${course.createdAt || '-'}</td>
                <td>${statusBadge}</td>
                <td class="actions">
                    <button class="btn-edit" onclick="editCourse(${course.id})">정보 수정</button>
                </td>
            </tr>
        `;
    }).join('');
}

function updatePagination(currentPage, totalPages) {
    window.paginationData = {
        currentPage: currentPage,
        totalPages: totalPages
    };

    if (typeof window.renderPagination === 'function') {
        window.renderPagination();
    }
}

// ==================== 검색 및 필터 ====================
async function performSearch() {
    const params = collectSearchParams();
    const data = await fetchCourseList(params);

    if (data) {
        renderCourseTable(data.courses, data.currentPage, data.size);
        updatePagination(data.currentPage, data.totalPages);
    }
}

// 페이지 변경
window.changePage = async function (page) {
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
    // 날짜 초기화 (값 비우기)
    const startDateInput = document.getElementById('startDate');
    const endDateInput = document.getElementById('endDate');

    if (startDateInput) startDateInput.value = '';
    if (endDateInput) endDateInput.value = '';

    // 라디오 버튼 초기화
    const allLanguageRadio = document.querySelector('input[name="language"][value="ALL"]');
    const allStatusRadio = document.querySelector('input[name="status"][value="ALL"]');

    if (allLanguageRadio) allLanguageRadio.checked = true;
    if (allStatusRadio) allStatusRadio.checked = true;

    // 검색어 초기화
    const keywordInput = document.getElementById('searchKeyword');
    if (keywordInput) keywordInput.value = '';

    // 정렬 초기화
    const sortSelect = document.getElementById('sortSelect');
    if (sortSelect) sortSelect.value = 'LATEST';

    // 검색 실행
    performSearch();
}

// ==================== 유틸리티 ====================
function initializeDateFilters() {
    // 날짜 필터를 빈 상태로 초기화 (null로 전송되도록)
    const startDateInput = document.getElementById('startDate');
    const endDateInput = document.getElementById('endDate');

    if (startDateInput) startDateInput.value = '';
    if (endDateInput) endDateInput.value = '';
}

// ==================== 이벤트 리스너 ====================
document.addEventListener('DOMContentLoaded', function () {
    console.log('🚀 페이지 초기화');

    // 날짜 필터 빈 상태로 초기화
    initializeDateFilters();

    // 검색 버튼
    const searchBtn = document.querySelector('.search-btn');
    if (searchBtn) {
        searchBtn.addEventListener('click', function(e) {
            e.preventDefault();
            performSearch();
        });
    }

    // 검색어 엔터키
    const searchKeyword = document.getElementById('searchKeyword');
    if (searchKeyword) {
        searchKeyword.addEventListener('keypress', function (e) {
            if (e.key === 'Enter') {
                e.preventDefault();
                performSearch();
            }
        });
    }

    // 정렬 선택
    const sortSelect = document.getElementById('sortSelect');
    if (sortSelect) {
        sortSelect.addEventListener('change', performSearch);
    }

    // 날짜 필터
    const startDateInput = document.getElementById('startDate');
    const endDateInput = document.getElementById('endDate');

    if (startDateInput) {
        startDateInput.addEventListener('change', performSearch);
    }
    if (endDateInput) {
        endDateInput.addEventListener('change', performSearch);
    }

    // 언어 라디오
    document.querySelectorAll('input[name="language"]').forEach(radio => {
        radio.addEventListener('change', performSearch);
    });

    // 상태 라디오
    document.querySelectorAll('input[name="status"]').forEach(radio => {
        radio.addEventListener('change', performSearch);
    });

    // 초기 데이터 로드
    performSearch();
});