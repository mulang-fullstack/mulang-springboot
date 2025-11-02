package yoonsome.mulang.domain.notice.entity;

import jakarta.persistence.*;
import lombok.*;
import yoonsome.mulang.domain.user.entity.User;

import java.time.LocalDateTime;

/**
 * 공지사항 엔티티
 * 관리자 작성, 모든 사용자 열람 가능
 */
@Entity
@Table(name = "notice")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long id;

    @Column(nullable = false, length = 200)
    private String title; // 제목

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content; // 본문 내용

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private NoticeStatus status = NoticeStatus.PUBLIC; // 기본값: 공개

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private NoticeType type = NoticeType.GENERAL; // 기본값: 일반 공지

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 작성자 (User 엔티티 연관)

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /** 엔티티 생성 시각 자동 설정 */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    /** 엔티티 수정 시각 자동 업데이트 */
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * 공지사항 수정 메서드
     */
    public void update(NoticeType type, String title, String content, NoticeStatus status) {
        this.type = type;
        this.title = title;
        this.content = content;
        this.status = status;
    }

    /**
     * 공지 유형 Enum
     */
    @Getter
    @AllArgsConstructor
    public enum NoticeType {
        GENERAL("일반"),
        UPDATE("업데이트"),
        SYSTEM("시스템");

        private final String description;
    }

    /**
     * 공개 상태 Enum
     */
    @Getter
    @AllArgsConstructor
    public enum NoticeStatus {
        PUBLIC("공개"),
        PRIVATE("비공개");

        private final String description;
    }
}
