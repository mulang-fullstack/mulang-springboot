document.addEventListener('DOMContentLoaded', () => {
    const ctx = document.getElementById('salesChart').getContext('2d');
    new Chart(ctx, {
        type: 'line',
        data: {
            labels: ['10/10','10/11','10/12','10/13','10/14','10/15','10/16'],
            datasets: [{
                label: '매출액(원)',
                data: [50000, 80000, 65000, 90000, 110000, 120000, 60000],
                borderColor: '#4B7BEC',
                backgroundColor: 'rgba(75,123,236,0.1)',
                borderWidth: 2,
                pointRadius: 4,
                pointBackgroundColor: '#4B7BEC',
                tension: 0.3
            }]
        },
        options: {
            maintainAspectRatio: false,
            responsive: true,
            scales: {
                x: { grid: { display: false }, ticks: { color: '#666' } },
                y: { beginAtZero: true, grid: { color: '#eef1f4' }, ticks: { color: '#666', stepSize: 20000 } }
            },
            plugins: {
                legend: { display: false },
                tooltip: { backgroundColor: '#333', titleFont: { size: 13, weight: '600' }, bodyFont: { size: 12 } }
            }
        }
    });
});
