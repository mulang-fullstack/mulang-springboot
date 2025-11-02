package yoonsome.mulang.domain.log.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import yoonsome.mulang.domain.log.entity.UserLog;

import java.time.LocalDateTime;
import java.util.List;

public interface UserLogRepository extends JpaRepository<UserLog, Long> {
    List<UserLog> findByEmailOrderByCreatedAtDesc(String email);

    /**
     * 로그인/로그아웃 로그 검색 및 정렬용 쿼리
     */
    @Query("""
            SELECT l FROM UserLog l
            WHERE (:action IS NULL OR l.action = :action)
              AND (:startDate IS NULL OR l.createdAt >= :startDate)
              AND (:endDate IS NULL OR l.createdAt <= :endDate)
              AND (
                   :keyword IS NULL
                OR :keyword = ''
                OR LOWER(l.username) LIKE LOWER(CONCAT('%', :keyword, '%'))
                OR LOWER(l.email) LIKE LOWER(CONCAT('%', :keyword, '%'))
                OR LOWER(l.ip) LIKE LOWER(CONCAT('%', :keyword, '%'))
              )
            """)
    Page<UserLog> searchUserLogs(
            @Param("action") UserLog.ActionType action,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("keyword") String keyword,
            Pageable pageable
    );

    /** 오늘 로그인한 사용자 수 (중복제외) */
    @Query("""
        SELECT COUNT(DISTINCT l.email)
        FROM UserLog l
        WHERE l.action = 'LOGIN' AND DATE(l.createdAt) = CURRENT_DATE
    """)
    long countTodayLogins();

    /** 최근 7일 일자별 로그인 사용자 수 (중복제외) */
    @Query("""
        SELECT DATE(l.createdAt), COUNT(DISTINCT l.email)
        FROM UserLog l
        WHERE l.action = 'LOGIN' AND l.createdAt >= :startDate
        GROUP BY DATE(l.createdAt)
        ORDER BY DATE(l.createdAt)
    """)
    List<Object[]> countDailyLogins(@Param("startDate") LocalDateTime startDate);
}