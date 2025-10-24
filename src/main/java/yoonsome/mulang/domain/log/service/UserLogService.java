package yoonsome.mulang.domain.log.service;

import org.springframework.data.domain.Page;
import yoonsome.mulang.api.admin.user.dto.UserLogSearchRequest;
import yoonsome.mulang.domain.log.entity.UserLog;

import java.time.LocalDateTime;
import java.util.List;

public interface UserLogService {
    /** 유저 로그 조회 & 필터 & 검색 */
    Page<UserLog> getUserLogList(UserLogSearchRequest request);
    /** 오늘 로그인한 사용자 수 (중복제외) */
    long countTodayLogins();
    /** 최근 7일 일자별 로그인 수 */
    List<Object[]> countDailyLogins(LocalDateTime startDate);
}
