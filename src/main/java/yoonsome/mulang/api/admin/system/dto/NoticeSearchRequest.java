package yoonsome.mulang.api.admin.system.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import yoonsome.mulang.domain.notice.entity.Notice.NoticeStatus;
import yoonsome.mulang.domain.notice.entity.Notice.NoticeType;

import java.time.LocalDateTime;

@Data
public class NoticeSearchRequest {

    private NoticeType type;
    private NoticeStatus status;
    private String keyword;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startDate = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endDate = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);

    private String sortBy = "createdAt";
    private String sortDirection = "DESC";
    private int page = 0;
    private int size = 10;
}
