package yoonsome.mulang.api.admin.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DailyCountResponse {
    private String date;
    private long count;
}
