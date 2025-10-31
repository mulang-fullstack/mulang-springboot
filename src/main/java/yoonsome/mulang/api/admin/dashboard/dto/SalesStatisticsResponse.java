package yoonsome.mulang.api.admin.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesStatisticsResponse {

    // 통계 카드 데이터
    private MonthlyStats monthlyStats;
    private DailyStats todayStats;
    private DailyStats yesterdayStats;
    private RefundStats refundStats;

    // 차트 데이터 (최근 7일)
    private List<DailySalesData> chartData;

    // 테이블 데이터 (최근 7일)
    private List<DailySalesData> tableData;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MonthlyStats {
        private Long totalSales;          // 이번 달 총 매출
        private Long lastMonthSales;      // 지난 달 총 매출
        private Double growthRate;        // 성장률 (%)
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DailyStats {
        private Long totalSales;          // 일 매출
        private Long paymentCount;        // 결제 건수
        private Double growthRate;        // 전일 대비 성장률
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RefundStats {
        private Long refundCount;         // 환불 건수
        private Long yesterdayRefundCount; // 전일 환불 건수
        private Long difference;          // 차이
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DailySalesData {
        private String date;              // 날짜 (yyyy-MM-dd)
        private String displayDate;       // 표시용 날짜 (MM/dd)
        private Long sales;               // 매출액
        private Long paymentCount;        // 결제 건수
        private Long refundCount;         // 환불 건수
    }
}
