document.addEventListener('DOMContentLoaded', async () => {
    try {
        // API로부터 매출 통계 데이터 가져오기
        const response = await fetch('/admin/dashboard/sales/api');
        if (!response.ok) {
            throw new Error('데이터를 불러오는데 실패했습니다.');
        }

        const data = await response.json();

        // 통계 카드 업데이트
        updateStatCards(data);

        // 차트 렌더링
        renderSalesChart(data.chartData);

        // 테이블 업데이트
        updateSalesTable(data.tableData);

    } catch (error) {
        console.error('Error loading sales data:', error);
        // 에러 시 기본 차트라도 표시
        renderSalesChart([]);
    }
});

/**
 * 통계 카드 업데이트
 */
function updateStatCards(data) {
    // 월 매출
    const monthlyStatsElement = document.querySelector('.stat-card:nth-child(1)');
    if (monthlyStatsElement && data.monthlyStats) {
        const salesElement = monthlyStatsElement.querySelector('.stat-number');
        const trendElement = monthlyStatsElement.querySelector('.stat-trend');

        if (salesElement) {
            salesElement.textContent = formatCurrency(data.monthlyStats.totalSales);
        }
        if (trendElement && data.monthlyStats.growthRate !== null) {
            const rate = data.monthlyStats.growthRate;
            const isPositive = rate >= 0;
            trendElement.className = `stat-trend ${isPositive ? 'up' : 'down'}`;
            trendElement.textContent = `${isPositive ? '↑' : '↓'} ${Math.abs(rate).toFixed(1)}% 전월 대비`;
        }
    }

    // 오늘 매출
    const todayStatsElement = document.querySelector('.stat-card:nth-child(2)');
    if (todayStatsElement && data.todayStats) {
        const salesElement = todayStatsElement.querySelector('.stat-number');
        const trendElement = todayStatsElement.querySelector('.stat-trend');

        if (salesElement) {
            salesElement.textContent = formatCurrency(data.todayStats.totalSales);
        }
        if (trendElement && data.todayStats.growthRate !== null) {
            const rate = data.todayStats.growthRate;
            const isPositive = rate >= 0;
            trendElement.className = `stat-trend ${isPositive ? 'up' : 'down'}`;
            trendElement.textContent = `${isPositive ? '↑' : '↓'} ${Math.abs(rate).toFixed(1)}% 어제 대비`;
        }
    }

    // 오늘 결제 건수
    const paymentCountElement = document.querySelector('.stat-card:nth-child(3)');
    if (paymentCountElement && data.todayStats) {
        const countElement = paymentCountElement.querySelector('.stat-number');
        if (countElement) {
            countElement.textContent = formatNumber(data.todayStats.paymentCount);
        }

        // 결제 건수 증감률
        const trendElement = paymentCountElement.querySelector('.stat-trend');
        if (trendElement) {
            const todayCount = data.todayStats.paymentCount || 0;

            if (data.yesterdayStats) {
                // 어제 데이터가 있을 때
                const yesterdayCount = data.yesterdayStats.paymentCount || 0;
                const countDiff = todayCount - yesterdayCount;
                const isPositive = countDiff >= 0;

                trendElement.className = `stat-trend ${isPositive ? 'up' : 'down'}`;
                trendElement.textContent = `${isPositive ? '↑' : '↓'} ${Math.abs(countDiff)}건 어제 대비`;
            } else {
                // 어제 데이터가 없을 때
                trendElement.className = 'stat-trend';
                trendElement.textContent = '첫 데이터';
            }
        }
    }

    // 환불 건수
    const refundElement = document.querySelector('.stat-card:nth-child(4)');
    if (refundElement && data.refundStats) {
        const countElement = refundElement.querySelector('.stat-number');
        const trendElement = refundElement.querySelector('.stat-trend');

        if (countElement) {
            countElement.textContent = formatNumber(data.refundStats.refundCount);
        }
        if (trendElement && data.refundStats.difference !== null) {
            const diff = data.refundStats.difference;
            const isPositive = diff <= 0; // 환불은 감소가 긍정적
            trendElement.className = `stat-trend ${isPositive ? 'up' : 'down'}`;
            trendElement.textContent = `${diff >= 0 ? '↑' : '↓'} ${Math.abs(diff)}건 전일 대비`;
        }
    }
}

/**
 * 차트 렌더링
 */
function renderSalesChart(chartData) {
    const ctx = document.getElementById('salesChart').getContext('2d');

    // 데이터가 없으면 빈 차트 표시
    const labels = chartData.length > 0
        ? chartData.map(d => d.displayDate)
        : ['데이터 없음'];
    const salesData = chartData.length > 0
        ? chartData.map(d => d.sales)
        : [0];

    new Chart(ctx, {
        type: 'line',
        data: {
            labels: labels,
            datasets: [{
                label: '매출액(원)',
                data: salesData,
                borderColor: '#4B7BEC',
                backgroundColor: 'rgba(75,123,236,0.1)',
                borderWidth: 2,
                pointRadius: 4,
                pointBackgroundColor: '#4B7BEC',
                tension: 0.3,
                fill: true
            }]
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
                    ticks: { color: '#666' }
                },
                y: {
                    beginAtZero: true,
                    grid: { color: '#eef1f4' },
                    ticks: {
                        color: '#666',
                        callback: function(value) {
                            return formatCurrency(value);
                        }
                    }
                }
            },
            plugins: {
                legend: { display: false },
                tooltip: {
                    backgroundColor: '#333',
                    titleFont: { size: 13, weight: '600' },
                    bodyFont: { size: 12 },
                    callbacks: {
                        label: function(context) {
                            return '매출액: ' + formatCurrency(context.parsed.y);
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
function updateSalesTable(tableData) {
    const tbody = document.querySelector('.table-body tbody');
    if (!tbody) return;

    if (!tableData || tableData.length === 0) {
        tbody.innerHTML = '<tr><td colspan="4" style="text-align: center;">데이터가 없습니다.</td></tr>';
        return;
    }

    // 테이블 내림차순 정렬 (최신 날짜가 위로)
    const sortedData = [...tableData].reverse();

    tbody.innerHTML = sortedData.map(row => `
        <tr>
            <td>${row.date}</td>
            <td>${formatCurrency(row.sales)}</td>
            <td>${formatNumber(row.paymentCount)}건</td>
            <td>${formatNumber(row.refundCount)}건</td>
        </tr>
    `).join('');
}

/**
 * 통화 포맷 (₩1,234,567)
 */
function formatCurrency(value) {
    if (value === null || value === undefined) return '₩0';
    return '₩' + Number(value).toLocaleString('ko-KR');
}

/**
 * 숫자 포맷 (1,234)
 */
function formatNumber(value) {
    if (value === null || value === undefined) return '0';
    return Number(value).toLocaleString('ko-KR');
}