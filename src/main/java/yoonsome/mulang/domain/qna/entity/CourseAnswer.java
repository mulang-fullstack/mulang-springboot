package yoonsome.mulang.domain.qna.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import yoonsome.mulang.domain.teacher.entity.Teacher;
import yoonsome.mulang.domain.user.entity.User;

import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "course_answer")
public class CourseAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "create_at")
    private Timestamp createdAt;

    @Column(name = "update_at")
    private Timestamp updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private CourseQuestion courseQuestion;
}
