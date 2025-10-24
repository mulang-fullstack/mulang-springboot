package yoonsome.mulang.domain.course.entity;

import jakarta.persistence.*;
import lombok.*;
import yoonsome.mulang.domain.category.entity.Category;
import yoonsome.mulang.domain.language.entity.Language;
import yoonsome.mulang.domain.teacher.entity.Teacher;
import java.time.LocalDate;

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

    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDate createdDate;

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDate.now();
    }

    /*
    @Column(name = "apply_started_at", nullable = false)
    private LocalDate applyStartedAt;

    @Column(name = "apply_ended_at", nullable = false)
    private LocalDate applyEndedAt;

    @Column(name = "started_at", nullable = false)
    private LocalDate startedAt;

    @Column(name = "ended_at", nullable = false)
    private LocalDate endedAt;

    @Column(nullable = false)
    private boolean status;
    */

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private StatusType status; // ENUM('PUBLIC', 'PRIVATE', 'PENDING')

    //공개, 비공개, 심사중
    /*
    @Column(name = "max_student")
    private Integer maxStudent;

    @Column(name = "current_student", nullable = false)
    private Integer currentStudent;
    */

    @Column
    private Integer price;

    /*
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private CourseType type; // ENUM('VOD', 'ONLINE', 'OFFLINE')
    */

    @Column(name = "lecture_count", nullable = false)
    private Integer lectureCount;

    @Column(name = "thumbnail", nullable = false, length = 255)
    private String thumbnail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = true)
    private Teacher teacher;

}

