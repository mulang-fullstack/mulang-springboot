package yoonsome.mulang.domain.course.entity;

import jakarta.persistence.*;
import lombok.*;
import yoonsome.mulang.domain.category.entity.Category;
import yoonsome.mulang.domain.language.entity.Language;
import yoonsome.mulang.domain.teacher.entity.Teacher;
import yoonsome.mulang.infra.file.entity.File;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 100)
    private String subtitle;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "html_content", columnDefinition = "TEXT")
    private String htmlContent;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime  createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private StatusType status; // ENUM('PUBLIC', 'PRIVATE', 'PENDING' 'REVIEW', 'REJECTED')

    @Column
    private Integer price;

    @Column(name = "lecture_count", nullable = false)
    private Integer lectureCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    private File file;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = true)
    private Teacher teacher;

    @Column(name = "average_rating")
    private Double averageRating;

    @Column(name = "review_count")
    private Integer reviewCount;

    @Column(name = "rejection_reason")
    private String rejectionReason;
}

