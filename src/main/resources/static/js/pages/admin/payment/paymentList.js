// ==================== 전역 변수 ====================
let isSearching = false; // 중복 요청 방지

// ==================== API 요청 ====================
async function fetchPaymentList(params) {
    if (isSearching) return null;

    try {
        isSearching = true;
        const queryString = new URLSearchParams(params).toString();
        const response = await fetch(`/admin/payment/api?${queryString}`, {
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
        console.log('📦 받은 데이터:', data); // 디버깅용
        return data;
    } catch (error) {
        console.error('Error fetching payment list:', error);
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
    const paymentMethodRadio = document.querySelector('input[name="paymentMethod"]:checked');
    const statusRadio = document.querySelector('input[name="status"]:checked');
    const keywordInput = document.getElementById('searchKeyword');
    const sortSelect = document.getElementById('sortSelect');

    const startDate = startDateInput?.value || '';
    const endDate = endDateInput?.value || '';
    const paymentMethod = paymentMethodRadio?.value || 'ALL';
    const status = statusRadio?.value || 'ALL';
    const keyword = keywordInput?.value.trim() || '';
    const sortValue = sortSelect?.value || 'LATEST';

    // 정렬 파라미터 변환
    let sortBy = 'approvedAt';
    let sortDirection = 'DESC';

    switch (sortValue) {
        case 'LATEST':
            sortBy = 'approvedAt';
            sortDirection = 'DESC';
            break;
        case 'OLDEST':
            sortBy = 'approvedAt';
            sortDirection = 'ASC';
            break;
        case 'COURSE_DESC':
            sortBy = 'orderName';
            sortDirection = 'DESC';
            break;
        case 'COURSE_ASC':
            sortBy = 'orderName';
            sortDirection = 'ASC';
            break;
        case 'AMOUNT_DESC':
            sortBy = 'amount';
            sortDirection = 'DESC';
            break;
        case 'AMOUNT_ASC':
            sortBy = 'amount';
            sortDirection = 'ASC';
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

    // 결제 수단 - ALL이 아닐 때만 추가
    if (paymentMethod && paymentMethod !== 'ALL') {
        params.paymentMethod = paymentMethod;
    }

    // 상태 - ALL이 아닐 때만 추가
    if (status && status !== 'ALL') {
        params.status = status;
    }

    // 검색어 - 값이 있을 때만 추가
    if (keyword) {
        params.keyword = keyword;
    }

    console.log('📋 검색 파라미터:', params); // 디버깅용
    return params;
}

// ==================== 렌더링 ====================
function renderPaymentTable(payments, currentPage, pageSize) {
    const tbody = document.querySelector('table tbody');

    if (!tbody) {
        console.error('❌ Table tbody not found');
        return;
    }

    if (!payments || payments.length === 0) {
        tbody.innerHTML = '<tr><td colspan="8" class="no-data">등록된 결제 내역이 없습니다.</td></tr>';
        return;
    }

    console.log('🎨 렌더링할 결제 수:', payments.length); // 디버깅용

    tbody.innerHTML = payments.map((payment, index) => {
        const rowNumber = currentPage * pageSize + index + 1;

        // 결제 금액 포맷팅
        const formattedAmount = payment.amount?.toLocaleString() || '0';

        // 결제 수단 한글 변환
        let paymentMethodText = payment.paymentMethod || '-';
        switch (payment.paymentMethod) {
            case 'CARD':
                paymentMethodText = '카드';
                break;
            case 'VIRTUAL_ACCOUNT':
                paymentMethodText = '가상계좌';
                break;
            case 'TRANSFER':
                paymentMethodText = '계좌이체';
                break;
            case 'MOBILE_PHONE':
                paymentMethodText = '휴대폰';
                break;
            case 'CULTURE_GIFT_CARD':
                paymentMethodText = '문화상품권';
                break;
        }

        // 결제 수단 상세 정보 추가
        if (payment.paymentMethodDetail) {
            paymentMethodText += ` (${payment.paymentMethodDetail})`;
        }

        // 상태 배지
        let statusBadge = '';
        let statusClass = '';
        let statusText = '';

        switch (payment.status) {
            case 'COMPLETED':
                statusClass = 'success';
                statusText = '완료';
                break;
            case 'PENDING':
                statusClass = 'pending';
                statusText = '대기';
                break;
            case 'FAILED':
                statusClass = 'failed';
                statusText = '실패';
                break;
            case 'REFUNDED':
                statusClass = 'refunded';
                statusText = '환불';
                break;
            default:
                statusClass = 'default';
                statusText = payment.status;
        }

        statusBadge = `<span class="status-badge ${statusClass}">${statusText}</span>`;

        // 날짜 포맷팅
        const approvedAt = payment.approvedAt || '-';

        return `
            <tr data-id="${payment.id}">
                <td>${rowNumber}</td>
                <td class="course-name">${payment.orderName || '-'}</td>
                <td>${payment.username || '-'}</td>
                <td class="amount">${formattedAmount}원</td>
                <td>${paymentMethodText}</td>
                <td>${approvedAt}</td>
                <td>${statusBadge}</td>
            </tr>
        `;
    }).join('');
}

function updatePagination(currentPage, totalPages) {
    window.paginationData = {
        currentPage: currentPage,
        totalPages: totalPages,
        baseUrl: '/admin/payment',
        asyncMode: true
    };

    if (typeof window.renderPagination === 'function') {
        window.renderPagination();
    }
}

// ==================== 검색 및 필터 ====================
async function performSearch() {
    const params = collectSearchParams();
    const data = await fetchPaymentList(params);

    if (data) {
        console.log('✅ 데이터 수신 성공:', data); // 디버깅용
        renderPaymentTable(data.payments, data.currentPage, data.size);
        updatePagination(data.currentPage, data.totalPages);
    }
}

// 페이지 변경
window.changePage = async function (page) {
    const params = collectSearchParams();
    params.page = page;

    const data = await fetchPaymentList(params);

    if (data) {
        renderPaymentTable(data.payments, data.currentPage, data.size);
        updatePagination(data.currentPage, data.totalPages);
    }
};

// 필터 초기화
function resetFilters() {
    // 날짜 초기화
    const startDateInput = document.getElementById('startDate');
    const endDateInput = document.getElementById('endDate');

    if (startDateInput) startDateInput.value = '';
    if (endDateInput) endDateInput.value = '';

    // 상태 초기화
    const allStatusRadio = document.querySelector('input[name="status"][value="COMPLETED"]');
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
    // 날짜 필터를 빈 상태로 초기화
    const startDateInput = document.getElementById('startDate');
    const endDateInput = document.getElementById('endDate');

    if (startDateInput) startDateInput.value = '';
    if (endDateInput) endDateInput.value = '';
}

// ==================== 액션 함수 ====================
function viewPaymentDetail(paymentId) {
    window.location.href = `/admin/payment/${paymentId}`;
}

function confirmRefund(paymentId, orderName) {
    if (confirm(`"${orderName}" 결제를 환불하시겠습니까?\n이 작업은 되돌릴 수 없습니다.`)) {
        executeRefund(paymentId);
    }
}

async function executeRefund(paymentId) {
    try {
        const response = await fetch(`/admin/payment/${paymentId}/refund`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': document.querySelector('meta[name="csrf-token"]')?.content
            },
            body: JSON.stringify({
                cancelReason: '관리자 환불 처리'
            })
        });

        if (response.ok) {
            alert('환불이 완료되었습니다.');
            performSearch(); // 목록 새로고침
        } else {
            const errorData = await response.json().catch(() => ({}));
            alert(errorData.message || '환불 처리에 실패했습니다.');
        }
    } catch (error) {
        console.error('Refund error:', error);
        alert('환불 처리 중 오류가 발생했습니다.');
    }
}

// ==================== 이벤트 리스너 ====================
document.addEventListener('DOMContentLoaded', function () {
    console.log('🚀 paymentList.js 초기화'); // 디버깅용

    // 날짜 필터 빈 상태로 초기화
    initializeDateFilters();

    // 검색 버튼
    const searchBtn = document.querySelector('.search-btn');
    if (searchBtn) {
        searchBtn.addEventListener('click', function (e) {
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

    // 결제 수단 라디오
    document.querySelectorAll('input[name="paymentMethod"]').forEach(radio => {
        radio.addEventListener('change', performSearch);
    });

    // 상태 라디오
    document.querySelectorAll('input[name="status"]').forEach(radio => {
        radio.addEventListener('change', performSearch);
    });

    // 초기 데이터 로드
    performSearch();
});