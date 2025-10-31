package yoonsome.mulang.domain.user.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import yoonsome.mulang.domain.user.entity.User;
import yoonsome.mulang.domain.user.entity.User.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    //이메일 회원 조회
    Optional<User> findByEmail(String email);
    //이메일 중복 여부 확인
    boolean existsByEmail(String email);
    //닉네임 중복 여부 확인
    boolean existsByNickname(String nickname);

    /**
     * 사용자 검색 + 필터 + 정렬용 쿼리
     */
    @Query("""
        SELECT u FROM User u
        WHERE (:role IS NULL OR u.role = :role)
          AND (:status IS NULL OR u.userStatus = :status)
          AND (:startDate IS NULL OR u.createdAt >= :startDate)
          AND (:endDate IS NULL OR u.createdAt <= :endDate)
          AND (
               :keyword IS NULL
            OR LOWER(u.username) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(u.nickname) LIKE LOWER(CONCAT('%', :keyword, '%'))
          )
        """)
    Page<User> searchUsers(
            @Param("role") Role role,
            @Param("status") UserStatus userStatus,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("keyword") String keyword,
            Pageable pageable
    );

    /**
     * 오늘 신규 가입자 수
     */
    @Query("""
        SELECT COUNT(u)
        FROM User u
        WHERE DATE(u.createdAt) = CURRENT_DATE
    """)
    long countTodayNewUsers();

    /**
     * 전체 사용자 수 (관리자 제외)
     */
    @Query("""
        SELECT COUNT(u)
        FROM User u
    """)
    long countTotalUsers();

    /**
     * 최근 7일 일자별 신규 가입자 수
     * (대시보드 그래프용)
     */
    @Query("""
        SELECT DATE(u.createdAt), COUNT(u)
        FROM User u
        WHERE u.createdAt >= :startDate
        GROUP BY DATE(u.createdAt)
        ORDER BY DATE(u.createdAt)
    """)
    List<Object[]> countDailyNewUsers(@Param("startDate") LocalDateTime startDate);

}