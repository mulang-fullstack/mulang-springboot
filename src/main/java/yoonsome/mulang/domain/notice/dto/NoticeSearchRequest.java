package yoonsome.mulang.domain.notice.dto;

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
    private LocalDateTime startDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endDate;

    private String sortBy = "createdAt";
    private String sortDirection = "DESC";
    private int page = 0;
    private int size = 10;
}
