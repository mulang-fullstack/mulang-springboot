// ==================== 필터 및 검색 관련 ====================

// 검색 파라미터 수집
function collectSearchParams() {
    const languageId = document.querySelector('input[name="language"]:checked')?.value;
    const status = document.querySelector('input[name="status"]:checked')?.value;
    const keyword = document.getElementById('searchKeyword')?.value.trim() || '';
    const sortValue = document.getElementById('sortSelect')?.value || 'LATEST';

    const params = {
        page: 0,
        size: 10
    };

    // 언어 파라미터 추가 (전체가 아닐 때만, "on"도 제외)
    if (languageId && languageId !== 'ALL' && languageId !== 'on') {
        params.languageId = languageId;
    }

    // 상태 파라미터 추가 (전체가 아닐 때만, "on"도 제외)
    if (status && status !== 'ALL' && status !== 'on') {
        params.status = status;
    }

    // 검색어가 빈 문자열이 아닐 때만 추가
    if (keyword) {
        params.keyword = keyword;
    }

    // 정렬 파라미터
    params.sort = sortValue;

    // 🔍 디버깅: 수집된 파라미터 출력
    console.log('📋 Collected Search Params:', params);

    return params;
}

// 비동기 요청 보내기
async function fetchCourseRequestList(params) {
    try {
        const queryString = new URLSearchParams(params).toString();
        const url = `/admin/content/course/api?${queryString}`;

        // 🔍 디버깅: 요청 URL 출력
        console.log('🌐 Request URL:', url);

        const response = await fetch(url, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': document.querySelector('meta[name="csrf-token"]')?.content || ''
            }
        });

        if (!response.ok) {
            throw new Error(`HTTP ${response.status}: 데이터를 불러오는데 실패했습니다.`);
        }

        const data = await response.json();

        // 🔍 디버깅: 응답 데이터 출력
        console.log('📦 Response Data:', data);

        return data;
    } catch (error) {
        console.error('❌ Error fetching course requests:', error);
        alert('데이터를 불러오는데 실패했습니다.\n' + error.message);
        return null;
    }
}

// 테이블 렌더링
function renderCourseRequestTable(courses, currentPage, pageSize, totalElements) {
    const tbody = document.querySelector('.table-wrap tbody');

    if (!tbody) {
        console.error('❌ Table tbody not found');
        return;
    }

    if (!courses || courses.length === 0) {
        tbody.innerHTML = '<tr><td colspan="8" class="no-data">신청된 강의가 없습니다.</td></tr>';
        console.log('ℹ️ No course requests to display');
        return;
    }

    // 🔍 디버깅: 렌더링할 강의 신청 개수 출력
    console.log(`✅ Rendering ${courses.length} course requests (Page ${currentPage + 1})`);

    tbody.innerHTML = courses.map((course, index) => {
        const rowNumber = totalElements - (currentPage * pageSize + index);

        // 상태 배지 클래스 및 텍스트
        let statusClass = '';
        let statusText = '';

        switch (course.status) {
            case 'PENDING':
                statusClass = 'pending';
                statusText = '심사대기';
                break;
            case 'REVIEW':
                statusClass = 'review';
                statusText = '심사중';
                break;
            default:
                statusClass = '';
                statusText = course.status;
        }

        // 날짜 포맷팅 (ISO 문자열을 YYYY-MM-DD로 변환)
        const createdAt = course.createdAt ? course.createdAt.substring(0, 10) : '-';

        return `
            <tr>
                <td>${rowNumber}</td>
                <td>${course.courseName || '-'}</td>
                <td>${course.languageName || '-'}</td>
                <td>${course.teacherName || '-'}</td>
                <td>${course.teacherNickname || '-'}</td>
                <td>${createdAt}</td>
                <td><span class="status-badge ${statusClass}">${statusText}</span></td>
                <td>
                    <button class="btn-approve" onclick="approveCourse(${course.id})">승인</button>
                    <button class="btn-reject" onclick="rejectCourse(${course.id})">거절</button>
                    <button class="btn-edit" onclick="location.href='/admin/content/request/${course.id}'">상세</button>
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
        baseUrl: '/admin/content/request',
        asyncMode: true
    };

    // 🔍 디버깅: 페이지네이션 정보 출력
    console.log('📄 Pagination Updated:', window.paginationData);

    if (typeof renderPagination === 'function') {
        renderPagination();
    } else {
        console.warn('⚠️ renderPagination function not found');
    }
}

// 검색 실행
async function performSearch() {
    console.log('🔍 === 검색 시작 ===');

    const params = collectSearchParams();
    const data = await fetchCourseRequestList(params);

    if (data) {
        renderCourseRequestTable(data.courses, data.currentPage, data.size, data.totalElements);
        updatePagination(data.currentPage, data.totalPages);
        console.log('✅ === 검색 완료 ===');
    } else {
        console.error('❌ === 검색 실패 ===');
    }
}

// 페이지 변경 (pagination.js에서 호출될 함수)
window.changePage = async function (page) {
    console.log(`📄 페이지 변경: ${page + 1}페이지로 이동`);

    const params = collectSearchParams();
    params.page = page;

    const data = await fetchCourseRequestList(params);

    if (data) {
        renderCourseRequestTable(data.courses, data.currentPage, data.size, data.totalElements);
        updatePagination(data.currentPage, data.totalPages);
    }
};

// 필터 초기화
function resetFilters() {
    console.log('🔄 필터 초기화');

    // 언어 전체 선택
    const allLanguageRadio = document.querySelector('input[name="language"][value="ALL"]');
    if (allLanguageRadio) allLanguageRadio.checked = true;

    // 상태 전체 선택
    const allStatusRadio = document.querySelector('input[name="status"][value="ALL"]');
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

// 강의 승인
async function approveCourse(courseId) {
    if (!confirm('이 강의를 승인하시겠습니까?')) {
        return;
    }

    try {
        const response = await fetch(`/admin/content/request/${courseId}/status?status=PUBLIC`, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': document.querySelector('meta[name="csrf-token"]')?.content || ''
            }
        });

        if (response.ok) {
            alert('강의가 승인되었습니다.');
            performSearch(); // 목록 새로고침
        } else {
            const errorData = await response.json().catch(() => ({}));
            alert(errorData.message || '강의 승인에 실패했습니다.');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('강의 승인 중 오류가 발생했습니다.');
    }
}

// 강의 거절
async function rejectCourse(courseId) {
    if (!confirm('이 강의를 거절하시겠습니까?')) {
        return;
    }

    try {
        const response = await fetch(`/admin/content/request/${courseId}/status?status=REJECTED`, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': document.querySelector('meta[name="csrf-token"]')?.content || ''
            }
        });

        if (response.ok) {
            alert('강의가 거절되었습니다.');
            performSearch(); // 목록 새로고침
        } else {
            const errorData = await response.json().catch(() => ({}));
            alert(errorData.message || '강의 거절에 실패했습니다.');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('강의 거절 중 오류가 발생했습니다.');
    }
}

// ==================== 이벤트 리스너 ====================

document.addEventListener('DOMContentLoaded', function () {
    console.log('🚀 CourseRequest.js 초기화 시작');

    // 검색 버튼
    const searchBtn = document.querySelector('.search-btn');
    if (searchBtn) {
        searchBtn.addEventListener('click', function(e) {
            e.preventDefault();
            performSearch();
        });
        console.log('✅ 검색 버튼 이벤트 등록');
    } else {
        console.warn('⚠️ 검색 버튼을 찾을 수 없습니다');
    }

    // 엔터 검색
    const searchKeyword = document.getElementById('searchKeyword');
    if (searchKeyword) {
        searchKeyword.addEventListener('keypress', function (e) {
            if (e.key === 'Enter') {
                e.preventDefault();
                performSearch();
            }
        });
        console.log('✅ 검색어 입력 이벤트 등록');
    }

    // 언어 라디오 버튼
    const languageRadios = document.querySelectorAll('input[name="language"]');
    languageRadios.forEach(radio => {
        radio.addEventListener('change', performSearch);
    });
    if (languageRadios.length > 0) {
        console.log('✅ 언어 필터 이벤트 등록');
    }

    // 상태 라디오 버튼
    const statusRadios = document.querySelectorAll('input[name="status"]');
    statusRadios.forEach(radio => {
        radio.addEventListener('change', performSearch);
    });
    if (statusRadios.length > 0) {
        console.log('✅ 상태 필터 이벤트 등록');
    }

    // 정렬 변경
    const sortSelect = document.getElementById('sortSelect');
    if (sortSelect) {
        sortSelect.addEventListener('change', performSearch);
        console.log('✅ 정렬 셀렉트 이벤트 등록');
    }

    // 초기화 버튼 (전역 함수로 이미 정의됨)
    console.log('ℹ️ 초기화 버튼은 onclick으로 연결됨');

    console.log('🎉 CourseRequest.js 초기화 완료');

    // 🔧 초기 데이터 로드 - 서버에서 이미 렌더링했으면 스킵
    const tbody = document.querySelector('.table-wrap tbody');
    const hasServerData = tbody && !tbody.querySelector('.no-data') && tbody.querySelectorAll('tr').length > 0;

    if (hasServerData) {
        console.log('ℹ️ 초기 데이터는 서버에서 렌더링됨 (동기 방식)');
        // 페이지네이션만 초기화
        if (typeof renderPagination === 'function') {
            renderPagination();
        }
    } else {
        console.log('ℹ️ 초기 데이터 비동기 로드 시작');
        performSearch();
    }
});