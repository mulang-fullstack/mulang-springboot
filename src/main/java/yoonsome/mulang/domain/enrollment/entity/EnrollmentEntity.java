package yoonsome.mulang.domain.enrollment.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import yoonsome.mulang.domain.lecture.entity.Lecture;
import yoonsome.mulang.domain.user.entity.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "enrollment",
        uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "lecture_id"}))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class EnrollmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enrollment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id", nullable = false)
    private Lecture lecture;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @Column(name = "enrollment_status", nullable = false)
    private Boolean enrollmentStatus = false;

    @CreatedDate
    @Column(name = "applied_at", nullable = false, updatable = false)
    private LocalDateTime appliedAt;

    @Builder
    public EnrollmentEntity(Lecture lecture, User student, Boolean enrollmentStatus) {
        this.lecture = lecture;
        this.student = student;
        this.enrollmentStatus = enrollmentStatus != null ? enrollmentStatus : false;
    }

    // 강의 입장 시 true로 변경
    public void markAsViewed() {
        this.enrollmentStatus = true;
    }

    // 상태 변경 메서드
    public void updateStatus(Boolean status) {
        this.enrollmentStatus = status;
    }
}
