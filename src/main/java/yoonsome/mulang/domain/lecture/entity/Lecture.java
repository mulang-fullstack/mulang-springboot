package yoonsome.mulang.domain.lecture.entity;

import jakarta.persistence.*;
import lombok.*;
import yoonsome.mulang.domain.course.entity.Course;
import yoonsome.mulang.infra.file.entity.File;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "lecture")
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_id")
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = true, length = 10)
    private String length;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

}
