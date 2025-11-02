package yoonsome.mulang.domain.progress.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import yoonsome.mulang.domain.lecture.entity.Lecture;
import yoonsome.mulang.domain.user.entity.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "progress",
        uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "lecture_id"}))
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Progress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "progress_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id", nullable = false)
    private Lecture lecture;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @Column(name = "progress_status", nullable = false)
    private Boolean progressStatus = false;

    @CreatedDate
    @Column(name = "applied_at", nullable = false, updatable = false)
    private LocalDateTime appliedAt;

    @Builder
    public Progress(Lecture lecture, User student, Boolean enrollmentStatus) {
        this.lecture = lecture;
        this.student = student;
        this.progressStatus = enrollmentStatus != null ? enrollmentStatus : false;
    }

    @PrePersist
    protected void onCreate() {
        if (this.appliedAt == null) {
            this.appliedAt = LocalDateTime.now();
        }
    }

    // 강의 입장 시 true로 변경
    public void markAsViewed() {
        this.progressStatus = true;
    }

    // 상태 변경 메서드
    public void updateStatus(Boolean status) {
        this.progressStatus = status;
    }
}
