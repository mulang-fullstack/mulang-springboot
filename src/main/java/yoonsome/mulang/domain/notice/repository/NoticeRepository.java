package yoonsome.mulang.domain.notice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import yoonsome.mulang.domain.notice.entity.Notice;
import yoonsome.mulang.domain.notice.entity.Notice.NoticeStatus;
import yoonsome.mulang.domain.notice.entity.Notice.NoticeType;

import java.time.LocalDateTime;

/**
 * 공지사항 레포지토리
 */
@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {

    /**
     * 동적 검색 조건으로 공지사항 목록 조회
     * - 공지 유형, 공개 여부, 키워드(제목/내용), 날짜 범위
     */
    @Query("SELECT n FROM Notice n " +
            "WHERE (:type IS NULL OR n.type = :type) " +
            "AND (:status IS NULL OR n.status = :status) " +
            "AND (:keyword IS NULL OR :keyword = '' OR n.title LIKE %:keyword% OR n.content LIKE %:keyword%) " +
            "AND (:startDate IS NULL OR n.createdAt >= :startDate) " +  // 변경
            "AND (:endDate IS NULL OR n.createdAt <= :endDate)")        // 변경
    Page<Notice> findBySearchConditions(
            @Param("type") NoticeType type,
            @Param("status") NoticeStatus status,
            @Param("keyword") String keyword,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable
    );

    /**
     * 공개 상태 공지사항만 조회 (사용자용)
     */
    @Query("SELECT n FROM Notice n WHERE n.status = 'PUBLIC' ORDER BY n.createdAt DESC")
    Page<Notice> findPublicNotices(Pageable pageable);

    /**
     * 특정 유형의 공개 공지사항 조회
     */
    Page<Notice> findByTypeAndStatus(NoticeType type, NoticeStatus status, Pageable pageable);
}