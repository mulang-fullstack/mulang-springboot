document.addEventListener('DOMContentLoaded', async () => {
    try {
        // API로부터 방문자 통계 데이터 가져오기
        const response = await fetch('/admin/dashboard/visitor/api');
        if (!response.ok) {
            throw new Error('데이터를 불러오는데 실패했습니다.');
        }

        const data = await response.json();

        // 통계 카드 업데이트
        updateStatCards(data);

        // 차트 렌더링
        renderVisitorChart(data);

        // 테이블 업데이트
        updateVisitorTable(data);

    } catch (error) {
        console.error('Error loading visitor data:', error);
        // 에러 시 기본 차트라도 표시
        renderVisitorChart({ weeklyLogins: [], weeklyNewUsers: [] });
        showErrorMessage();
    }
});

/**
 * 통계 카드 업데이트
 */
function updateStatCards(data) {
    // 오늘 로그인
    const todayLoginElement = document.querySelector('.stat-card:nth-child(1) .stat-number');
    if (todayLoginElement && data.todayLogins !== undefined) {
        todayLoginElement.textContent = formatNumber(data.todayLogins);
    }

    // 오늘 로그인 증감률
    const loginTrendElement = document.querySelector('.stat-card:nth-child(1) .stat-trend');
    if (loginTrendElement && data.loginChangeRate !== undefined && data.loginChangeRate !== null) {
        const rate = data.loginChangeRate;
        const isPositive = rate >= 0;
        loginTrendElement.className = `stat-trend ${isPositive ? 'up' : 'down'}`;
        loginTrendElement.textContent = `${isPositive ? '↑' : '↓'} ${Math.abs(rate).toFixed(1)}% 어제 대비`;
    }

    // 현재 활성 사용자
    const activeSessionsElement = document.querySelector('.stat-card:nth-child(2) .stat-number');
    if (activeSessionsElement && data.activeSessions !== undefined) {
        activeSessionsElement.textContent = formatNumber(data.activeSessions);
    }

    // 신규 사용자
    const todayNewUsersElement = document.querySelector('.stat-card:nth-child(3) .stat-number');
    if (todayNewUsersElement && data.todayNewUsers !== undefined) {
        todayNewUsersElement.textContent = formatNumber(data.todayNewUsers);
    }

    // 신규 사용자 증감률
    const signupTrendElement = document.querySelector('.stat-card:nth-child(3) .stat-trend');
    if (signupTrendElement && data.signupChangeRate !== undefined && data.signupChangeRate !== null) {
        const rate = data.signupChangeRate;
        const isPositive = rate >= 0;
        signupTrendElement.className = `stat-trend ${isPositive ? 'up' : 'down'}`;
        signupTrendElement.textContent = `${isPositive ? '↑' : '↓'} ${Math.abs(rate).toFixed(1)}% 어제 대비`;
    }

    // 전체 사용자
    const totalUsersElement = document.querySelector('.stat-card:nth-child(4) .stat-number');
    if (totalUsersElement && data.totalUsers !== undefined) {
        totalUsersElement.textContent = formatNumber(data.totalUsers);
    }
}

/**
 * 차트 렌더링
 */
function renderVisitorChart(data) {
    const ctx = document.getElementById('visitorChart');
    if (!ctx) {
        console.error('visitorChart 요소를 찾을 수 없습니다.');
        return;
    }

    const chartContext = ctx.getContext('2d');

    // 데이터가 없으면 빈 차트 표시
    const weeklyLogins = data.weeklyLogins || [];
    const weeklyNewUsers = data.weeklyNewUsers || [];

    const labels = weeklyLogins.length > 0
        ? weeklyLogins.map(item => item.date)
        : ['데이터 없음'];

    const loginCounts = weeklyLogins.length > 0
        ? weeklyLogins.map(item => item.count)
        : [0];

    const signupCounts = weeklyNewUsers.length > 0
        ? weeklyNewUsers.map(item => item.count)
        : [0];

    new Chart(chartContext, {
        type: 'line',
        data: {
            labels: labels,
            datasets: [
                {
                    label: '로그인 수',
                    data: loginCounts,
                    borderColor: '#4B7BEC',
                    backgroundColor: 'rgba(75,123,236,0.1)',
                    borderWidth: 2,
                    pointRadius: 4,
                    pointBackgroundColor: '#4B7BEC',
                    tension: 0.3,
                    fill: true
                },
                {
                    label: '신규 가입자 수',
                    data: signupCounts,
                    borderColor: '#2ECC71',
                    backgroundColor: 'rgba(46,204,113,0.1)',
                    borderWidth: 2,
                    pointRadius: 4,
                    pointBackgroundColor: '#2ECC71',
                    tension: 0.3,
                    fill: true
                }
            ]
        },
        options: {
            maintainAspectRatio: false,
            responsive: true,
            interaction: {
                intersect: false,
                mode: 'index'
            },
            scales: {
                x: {
                    grid: { display: false },
                    ticks: { color: '#666', font: { size: 12 } }
                },
                y: {
                    beginAtZero: true,
                    grid: { color: '#eef1f4' },
                    ticks: {
                        color: '#666',
                        stepSize: 1,
                        callback: function(value) {
                            return Number.isInteger(value) ? value : '';
                        }
                    }
                }
            },
            plugins: {
                legend: {
                    display: true,
                    position: 'bottom',
                    labels: {
                        usePointStyle: true,
                        padding: 15,
                        font: { size: 12 }
                    }
                },
                tooltip: {
                    backgroundColor: '#333',
                    titleFont: { size: 13, weight: '600' },
                    bodyFont: { size: 12 },
                    callbacks: {
                        label: function(context) {
                            return context.dataset.label + ': ' + formatNumber(context.parsed.y) + '명';
                        }
                    }
                }
            }
        }
    });
}

/**
 * 테이블 업데이트
 */
function updateVisitorTable(data) {
    const tbody = document.querySelector('.table-body tbody');
    if (!tbody) return;

    const weeklyLogins = data.weeklyLogins || [];
    const weeklyNewUsers = data.weeklyNewUsers || [];

    if (weeklyLogins.length === 0) {
        tbody.innerHTML = '<tr><td colspan="4" class="no-data">최근 7일간 데이터가 없습니다.</td></tr>';
        return;
    }

    // 테이블 내림차순 정렬 (최신 날짜가 위로)
    const sortedLogins = [...weeklyLogins].reverse();
    const sortedNewUsers = [...weeklyNewUsers].reverse();

    tbody.innerHTML = sortedLogins.map((login, index) => {
        const newUser = sortedNewUsers[index] || { count: 0 };

        return `
            <tr>
                <td>${login.date}</td>
                <td>${formatNumber(login.count)}명</td>
                <td>${formatNumber(newUser.count)}명</td>
            </tr>
        `;
    }).join('');
}

/**
 * 에러 메시지 표시
 */
function showErrorMessage() {
    const chartSection = document.querySelector('.chart-section .chart-body');
    if (chartSection) {
        const errorDiv = document.createElement('div');
        errorDiv.className = 'error-message';
        errorDiv.style.cssText = 'padding: 40px; text-align: center; color: #e74c3c;';
        errorDiv.innerHTML = `
            <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" style="margin-bottom: 16px;">
                <circle cx="12" cy="12" r="10"/>
                <line x1="12" y1="8" x2="12" y2="12"/>
                <line x1="12" y1="16" x2="12.01" y2="16"/>
            </svg>
            <h3 style="margin: 0 0 8px 0; font-size: 16px;">데이터를 불러올 수 없습니다</h3>
            <p style="margin: 0; font-size: 14px; color: #999;">잠시 후 다시 시도해주세요.</p>
        `;

        // 기존 canvas 숨기기
        const canvas = document.getElementById('visitorChart');
        if (canvas) {
            canvas.style.display = 'none';
        }

        chartSection.appendChild(errorDiv);
    }
}

/**
 * 숫자 포맷 (1,234)
 */
function formatNumber(value) {
    if (value === null || value === undefined) return '0';
    return Number(value).toLocaleString('ko-KR');
}