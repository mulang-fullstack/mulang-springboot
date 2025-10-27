package yoonsome.mulang.api.qna.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yoonsome.mulang.api.qna.dto.CourseAnswerRequest;
import yoonsome.mulang.api.qna.dto.CourseAnswerResponse;
import yoonsome.mulang.api.qna.dto.CourseQuestionRequest;
import yoonsome.mulang.api.qna.dto.CourseQuestionResponse;
import yoonsome.mulang.domain.course.entity.Course;
import yoonsome.mulang.domain.qna.entity.CourseAnswer;
import yoonsome.mulang.domain.qna.entity.CourseQuestion;
import yoonsome.mulang.domain.qna.service.CourseQnaService;
import yoonsome.mulang.domain.teacher.entity.Teacher;
import yoonsome.mulang.domain.user.entity.User;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Q&A API 서비스 구현체
 * - DTO는 @Builder 방식으로 생성
 * - 람다, 스트림, 메서드 레퍼런스 사용 금지
 */
@Service
@RequiredArgsConstructor
@Transactional
public class CourseQnaApiServiceImpl implements CourseQnaApiService {

    private final CourseQnaService courseQnaService;

    /**
     * 질문 등록
     */
    public void createQuestion(CourseQuestionRequest request, User user, Course course) {
        CourseQuestion question = CourseQuestion.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .course(course)
                .user(user)
                .createdAt(Timestamp.from(Instant.now()))
                .build();

        courseQnaService.createQuestion(question);
    }

    /**
     * 답변 등록
     */
    public void createAnswer(CourseAnswerRequest request, Teacher teacher, CourseQuestion question) {
        CourseAnswer answer = CourseAnswer.builder()
                .content(request.getContent())
                .courseQuestion(question)
                .teacher(teacher)
                .createdAt(Timestamp.from(Instant.now()))
                .build();

        courseQnaService.createAnswer(answer);
    }
}
