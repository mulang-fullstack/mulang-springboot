package yoonsome.mulang.course.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

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

    @Column(name = "max_student")
    private Integer maxStudent;

    @Column(name = "current_student", nullable = false)
    private Integer currentStudent;

    @Column
    private Integer price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private CourseType type; // ENUM('VOD', 'ONLINE', 'OFFLINE')

    @Column(name = "lecture_count", nullable = false)
    private Integer lectureCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    // Tutor는 엔티티 만들어지고 나중에 추가 예정
}

