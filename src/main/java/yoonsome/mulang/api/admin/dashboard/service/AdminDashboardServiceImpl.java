package yoonsome.mulang.api.admin.dashboard.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;
import yoonsome.mulang.api.admin.dashboard.dto.DailyCountResponse;
import yoonsome.mulang.api.admin.dashboard.dto.DashboardResponse;
import yoonsome.mulang.domain.log.service.UserLogService;
import yoonsome.mulang.domain.user.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
@Service
@RequiredArgsConstructor
public class AdminDashboardServiceImpl implements AdminDashboardService {

    private final UserService userService;
    private final UserLogService userLogService;
    private final SessionRegistry sessionRegistry;

    public DashboardResponse getDashboardStats() {
        LocalDateTime weekAgo = LocalDateTime.now().minusDays(6).withHour(0).withMinute(0).withSecond(0);

        long todayLogins = userLogService.countTodayLogins();
        long activeSessions = sessionRegistry.getAllPrincipals().size();
        long todayNewUsers = userService.countTodayNewUsers();
        long totalUsers = userService.countTotalUsers();

        List<Object[]> loginStats = userLogService.countDailyLogins(weekAgo);
        List<Object[]> signupStats = userService.countDailyNewUsers(weekAgo);

        double loginChange = calcChangeRate(loginStats);
        double signupChange = calcChangeRate(signupStats);

        return DashboardResponse.builder()
                .todayLogins(todayLogins)
                .activeSessions(activeSessions)
                .todayNewUsers(todayNewUsers)
                .totalUsers(totalUsers)
                .weeklyLogins(mapToChart(loginStats))
                .weeklyNewUsers(mapToChart(signupStats))
                .loginChangeRate(loginChange)
                .signupChangeRate(signupChange)
                .build();
    }
    /**
     * 전일 대비 증감률(%) 계산 메서드
     * - 주간 통계의 마지막 두 항목을 비교하여 변화율을 계산
     * - 예: 어제 100건 → 오늘 120건이면 ((120 - 100) / 100) * 100 = +20%
     * - 데이터가 2개 미만일 경우(계산 불가)는 0 반환
     *
     * @param list 날짜별 [날짜, 수치] 배열 리스트 (ex: [['2025-10-20', 12], ['2025-10-21', 15]])
     * @return 전일 대비 증감률 (소수점 포함)
     */
    private double calcChangeRate(List<Object[]> list) {
        if (list.size() < 2) return 0;
        long yesterday = ((Number) list.get(list.size() - 2)[1]).longValue();
        long today = ((Number) list.get(list.size() - 1)[1]).longValue();
        return yesterday == 0 ? 0 : ((double) (today - yesterday) / yesterday) * 100;
    }
    /**
     * JPA 통계 쿼리 결과(Object[] 리스트)를 DailyCountResponse 리스트로 변환
     * - JPA에서 SELECT date(created_at), COUNT(*) 형태로 반환된 데이터를 변환
     * - 각 Object[]의 첫 번째 요소(날짜)는 문자열로, 두 번째 요소(카운트)는 long으로 매핑
     *
     * @param raw JPA 결과 리스트 (ex: [['2025-10-20', 12], ['2025-10-21', 15]])
     * @return DailyCountResponse 리스트 (date, count)
     */
    private List<DailyCountResponse> mapToChart(List<Object[]> raw) {
        return raw.stream()
                .map(r -> new DailyCountResponse(r[0].toString(), ((Number) r[1]).longValue()))
                .toList();
    }
}
