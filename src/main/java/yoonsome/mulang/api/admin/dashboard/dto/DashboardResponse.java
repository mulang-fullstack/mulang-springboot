package yoonsome.mulang.api.admin.dashboard.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DashboardResponse {
    private long todayLogins;
    private long activeSessions;
    private long todayNewUsers;
    private long totalUsers;
    private List<DailyCountResponse> weeklyLogins;
    private List<DailyCountResponse> weeklyNewUsers;
    private double loginChangeRate;
    private double signupChangeRate;
}