package yoonsome.mulang.api.student.dto;


import lombok.Data;
import yoonsome.mulang.domain.course.entity.Course;
import yoonsome.mulang.domain.qna.entity.CourseQuestion;

import java.sql.Timestamp;

@Data
public class QnaResponse {
    private Long id;
    private String title;
    private String content;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Course course;

    // Entity -> DTO 변환 메서드
    public static QnaResponse from(CourseQuestion question) {
        QnaResponse response = new QnaResponse();
        response.setId(question.getId());
        response.setContent(question.getContent());
        response.setCreatedAt(question.getCreatedAt());
        response.setUpdatedAt(question.getUpdatedAt());
        response.setTitle(question.getTitle());
        response.setCourse(question.getCourse());
        return response;


    }
}
