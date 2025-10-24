package yoonsome.mulang.domain.log.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import yoonsome.mulang.api.admin.user.dto.UserLogSearchRequest;
import yoonsome.mulang.domain.log.entity.UserLog;
import yoonsome.mulang.domain.log.repository.UserLogRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserLogServiceImpl implements UserLogService {

    private final UserLogRepository  userLogRepository;

    @Override
    public Page<UserLog> getUserLogList(UserLogSearchRequest request) {
        // 정렬 기준 설정
        Sort sort;
        if ("username".equalsIgnoreCase(request.getSortBy())) {
            sort = request.getSortDirection().equalsIgnoreCase("DESC")
                    ? Sort.by(Sort.Order.desc("username"))
                    : Sort.by(Sort.Order.asc("username"));
        } else {
            sort = request.getSortDirection().equalsIgnoreCase("ASC")
                    ? Sort.by(Sort.Order.asc("createdAt"))
                    : Sort.by(Sort.Order.desc("createdAt"));
        }

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), sort);

        UserLog.ActionType actionType = null;
        if (request.getAction() != null && !"ALL".equalsIgnoreCase(request.getAction())) {
            try {
                actionType = UserLog.ActionType.valueOf(request.getAction().toUpperCase());
            } catch (IllegalArgumentException e) {
                actionType = null; // 잘못된 값이 오면 무시
            }
        }

        System.out.println("==============================================="+request.getKeyword());

        // 검색 실행
        return userLogRepository.searchUserLogs(
                actionType,
                request.getStartDate(),    // 시작일
                request.getEndDate(),      // 종료일
                request.getKeyword(),      // 검색어
                pageable
        );
    }
    @Override
    public long countTodayLogins() {
        return userLogRepository.countTodayLogins();
    }

    @Override
    public List<Object[]> countDailyLogins(LocalDateTime startDate) {
        return userLogRepository.countDailyLogins(startDate);
    }

}
