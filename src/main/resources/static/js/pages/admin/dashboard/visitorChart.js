document.addEventListener('DOMContentLoaded', async () => {
    try {
        const res = await fetch('/admin/dashboard/visitor/api');
        if (!res.ok) throw new Error('데이터 요청 실패');
        const data = await res.json();

        const labels = data.weeklyLogins.map(item => item.date);
        const loginCounts = data.weeklyLogins.map(item => item.count);
        const signupCounts = data.weeklyNewUsers.map(item => item.count);

        const ctx = document.getElementById('visitorChart').getContext('2d');
        new Chart(ctx, {
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
                        tension: 0.3
                    },
                    {
                        label: '신규 가입자 수',
                        data: signupCounts,
                        borderColor: '#2ECC71',
                        backgroundColor: 'rgba(46,204,113,0.1)',
                        borderWidth: 2,
                        pointRadius: 4,
                        pointBackgroundColor: '#2ECC71',
                        tension: 0.3
                    }
                ]
            },
            options: {
                maintainAspectRatio: false,
                responsive: true,
                scales: {
                    x: {grid: {display: false}, ticks: {color: '#666', font: {size: 12}}},
                    y: {beginAtZero: true, grid: {color: '#eef1f4'}, ticks: {color: '#666'}}
                },
                plugins: {
                    legend: {display: true, position: 'bottom'},
                    tooltip: {
                        backgroundColor: '#333',
                        titleFont: {size: 13, weight: '600'},
                        bodyFont: {size: 12}
                    }
                }
            }
        });
    } catch (err) {
        console.error('대시보드 차트 로드 실패:', err);
    }
});
