// ==================== 필터 및 검색 관련 ====================

// 날짜 포맷팅 함수
function formatDate(date) {
    const y = date.getFullYear();
    const m = String(date.getMonth() + 1).padStart(2, '0');
    const d = String(date.getDate()).padStart(2, '0');
    return `${y}-${m}-${d}`;
}

// 검색 파라미터 수집
function collectSearchParams() {
    let startDate = document.querySelector('.date-filter input[type="date"]:first-child')?.value;
    let endDate = document.querySelector('.date-filter input[type="date"]:last-child')?.value;

    // 🔧 날짜가 비어있으면 기본값 설정 (이번 달 1일 ~ 오늘)
    if (!startDate) {
        const firstDay = new Date();
        firstDay.setDate(1);
        startDate = formatDate(firstDay);
    }

    if (!endDate) {
        endDate = formatDate(new Date());
    }

    const action = document.querySelector('input[name="logType"]:checked')?.value;
    const keyword = document.getElementById('searchKeyword')?.value.trim() || '';
    const sortValue = document.getElementById('sortSelect')?.value || 'LATEST';

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
        case 'NAME_ASC':
            sortBy = 'username';
            sortDirection = 'ASC';
            break;
        case 'NAME_DESC':
            sortBy = 'username';
            sortDirection = 'DESC';
            break;
    }

    const params = {
        page: 0,
        size: 10
    };

    // 날짜 파라미터 추가
    if (startDate) {
        params.startDate = startDate + 'T00:00:00';
    }
    if (endDate) {
        params.endDate = endDate + 'T23:59:59';
    }

    // action이 'ALL'이 아닐 때만 추가
    if (action && action !== 'ALL') {
        params.action = action;
    }

    // keyword가 빈 문자열이 아닐 때만 추가
    if (keyword) {
        params.keyword = keyword;
    }

    // 정렬 파라미터
    params.sortBy = sortBy;
    params.sortDirection = sortDirection;

    // 🔍 디버깅: 수집된 파라미터 출력
    console.log('📋 Collected Search Params:', params);

    return params;
}

// 비동기 요청 보내기
async function fetchUserLogList(params) {
    try {
        const queryString = new URLSearchParams(params).toString();
        const url = `/admin/user/log/api?${queryString}`;

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
        console.error('❌ Error fetching user logs:', error);
        alert('데이터를 불러오는데 실패했습니다.\n' + error.message);
        return null;
    }
}

// 테이블 렌더링
function renderUserLogTable(logs, currentPage, pageSize) {
    const tbody = document.querySelector('.table-wrap tbody');

    if (!tbody) {
        console.error('❌ Table tbody not found');
        return;
    }

    if (!logs || logs.length === 0) {
        tbody.innerHTML = '<tr><td colspan="7" class="no-data">발생한 로그가 없습니다.</td></tr>';
        console.log('ℹ️ No logs to display');
        return;
    }

    // 🔍 디버깅: 렌더링할 로그 개수 출력
    console.log(`✅ Rendering ${logs.length} logs (Page ${currentPage + 1})`);

    tbody.innerHTML = logs.map((log, index) => {
        const rowNumber = currentPage * pageSize + index + 1;
        const logClass = log.action === 'LOGIN' ? 'login' : 'logout';
        const logText = log.action === 'LOGIN' ? '로그인' : '로그아웃';

        return `
            <tr data-id="${log.id}" data-action="${log.action}">
                <td>${rowNumber}</td>
                <td>${log.username || '-'}</td>
                <td>${log.email || '-'}</td>
                <td>${log.ip || '-'}</td>
                <td>${log.userAgent || '-'}</td>
                <td>${log.createdAt || '-'}</td>
                <td><span class="log-badge ${logClass}">${logText}</span></td>
            </tr>
        `;
    }).join('');
}

// 페이지네이션 업데이트
function updatePagination(currentPage, totalPages) {
    window.paginationData = {
        currentPage: currentPage,
        totalPages: totalPages,
        baseUrl: '/admin/user/log',
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
    const data = await fetchUserLogList(params);

    if (data) {
        renderUserLogTable(data.logs, data.currentPage, data.size);
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

    const data = await fetchUserLogList(params);

    if (data) {
        renderUserLogTable(data.logs, data.currentPage, data.size);
        updatePagination(data.currentPage, data.totalPages);
    }
};

// 필터 초기화
function resetFilters() {
    console.log('🔄 필터 초기화');

    const today = new Date();
    const firstDay = new Date(today.getFullYear(), today.getMonth(), 1);

    const dateInputs = document.querySelectorAll('.date-filter input[type="date"]');
    if (dateInputs.length >= 2) {
        dateInputs[0].value = formatDate(firstDay);
        dateInputs[1].value = formatDate(today);
    }

    // 로그 타입 ALL 선택
    const allRadio = document.querySelector('input[name="logType"][value="ALL"]');
    if (allRadio) allRadio.checked = true;

    // 검색어 초기화
    const keywordInput = document.getElementById('searchKeyword');
    if (keywordInput) keywordInput.value = '';

    // 정렬 초기화
    const sortSelect = document.getElementById('sortSelect');
    if (sortSelect) sortSelect.value = 'LATEST';

    // 검색 실행
    performSearch();
}

// 초기 날짜 설정
function initializeDateFilters() {
    const today = new Date();
    const firstDay = new Date(today.getFullYear(), today.getMonth(), 1);

    const dateInputs = document.querySelectorAll('.date-filter input[type="date"]');
    if (dateInputs.length >= 2) {
        // 이미 값이 설정되어 있지 않으면 기본값 설정
        if (!dateInputs[0].value) {
            dateInputs[0].value = formatDate(firstDay);
        }
        if (!dateInputs[1].value) {
            dateInputs[1].value = formatDate(today);
        }

        console.log('📅 날짜 필터 초기화:', {
            startDate: dateInputs[0].value,
            endDate: dateInputs[1].value
        });
    }
}

// ==================== 이벤트 리스너 ====================

document.addEventListener('DOMContentLoaded', function () {
    console.log('🚀 UserLog.js 초기화 시작');

    // 🔧 날짜 필터 초기화
    initializeDateFilters();

    // 검색 버튼
    const searchBtn = document.querySelector('.search-btn');
    if (searchBtn) {
        searchBtn.addEventListener('click', performSearch);
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

    // 날짜 변경
    const dateInputs = document.querySelectorAll('.date-filter input[type="date"]');
    dateInputs.forEach(input => {
        input.addEventListener('change', performSearch);
    });
    if (dateInputs.length > 0) {
        console.log('✅ 날짜 필터 이벤트 등록');
    }

    // 로그 타입 라디오
    const logTypeRadios = document.querySelectorAll('input[name="logType"]');
    logTypeRadios.forEach(radio => {
        radio.addEventListener('change', performSearch);
    });
    if (logTypeRadios.length > 0) {
        console.log('✅ 로그 타입 필터 이벤트 등록');
    }

    // 정렬 변경
    const sortSelect = document.getElementById('sortSelect');
    if (sortSelect) {
        sortSelect.addEventListener('change', performSearch);
        console.log('✅ 정렬 셀렉트 이벤트 등록');
    }

    // 초기화 버튼 (전역 함수로 이미 정의됨)
    console.log('ℹ️ 초기화 버튼은 onclick으로 연결됨');

    console.log('🎉 UserLog.js 초기화 완료');

    // 🔧 초기 데이터 로드
    performSearch();
});