package yoonsome.mulang.api.qna.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import yoonsome.mulang.api.qna.dto.CourseAnswerRequest;
import yoonsome.mulang.api.qna.dto.CourseAnswerResponse;
import yoonsome.mulang.api.qna.dto.CourseQuestionRequest;
import yoonsome.mulang.api.qna.dto.CourseQuestionResponse;
import yoonsome.mulang.domain.course.entity.Course;
import yoonsome.mulang.domain.course.service.CourseService;
import yoonsome.mulang.domain.qna.entity.CourseAnswer;
import yoonsome.mulang.domain.qna.entity.CourseQuestion;
import yoonsome.mulang.domain.qna.service.CourseQnaService;
import yoonsome.mulang.domain.teacher.entity.Teacher;
import yoonsome.mulang.domain.teacher.service.TeacherService;
import yoonsome.mulang.domain.user.entity.User;
import yoonsome.mulang.domain.user.service.UserService;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CourseQnaApiServiceImpl implements CourseQnaApiService {

    private final CourseQnaService courseQnaService;
    private final CourseService courseService;
    private final UserService userService;
    private final TeacherService teacherService;

    /**
     * 질문 등록
     */
    @Override
    public void createQuestion(CourseQuestionRequest request, Long userId) {
        User user = userService.findById(userId);

        Course course = courseService.getCourse(request.getCourseId());

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
    @Override
    public void createAnswer(CourseAnswerRequest request, Long teacherId) {
        CourseQuestion question = courseQnaService.getQuestionById(request.getQuestionId());

        // teacherId 없을 경우 teacher=null로 처리 (학생도 등록 가능)
        Teacher teacher = null;
        if (teacherId != null) {
            teacher = teacherService.getTeacherById(teacherId);
        }

        CourseAnswer answer = CourseAnswer.builder()
                .content(request.getContent())
                .courseQuestion(question)
                .teacher(teacher) // null 허용 → 일반 사용자 답변도 등록됨
                .createdAt(Timestamp.from(Instant.now()))
                .build();

        courseQnaService.createAnswer(answer);
    }

    /**
     * 강좌별 Q&A 조회
     */
    @Override
    public Page<CourseQuestionResponse> getQnaByCourse(Long courseId, Pageable pageable) {
        return buildQnaPage(courseQnaService.getQuestionPageByCourse(courseId, pageable), pageable);
    }

    private Page<CourseQuestionResponse> buildQnaPage(Page<CourseQuestion> questionPage, Pageable pageable) {
        List<CourseQuestionResponse> questionResponseList = new ArrayList<>();

        for (CourseQuestion question : questionPage.getContent()) {
            Page<CourseAnswer> answerPage =
                    courseQnaService.getAnswerPageByQuestion(question.getId(), pageable);

            List<CourseAnswerResponse> answers = answerPage.getContent().stream().map(answer -> {
                String teacherName = (answer.getTeacher() != null && answer.getTeacher().getUser() != null)
                        ? answer.getTeacher().getUser().getUsername() : "알 수 없음";

                return CourseAnswerResponse.builder()
                        .id(answer.getId())
                        .content(answer.getContent())
                        .teacherName(teacherName)
                        .createdAt(answer.getCreatedAt())
                        .build();
            }).toList();

            String writerName = (question.getUser() != null) ? question.getUser().getNickname() : "알 수 없음";

            questionResponseList.add(CourseQuestionResponse.builder()
                    .id(question.getId())
                    .title(question.getTitle())
                    .content(question.getContent())
                    .writerName(writerName)
                    .createdAt(question.getCreatedAt())
                    .answers(answers)
                    .build());
        }

        return new PageImpl<>(questionResponseList, pageable, questionPage.getTotalElements());
    }
    @Override
    public void updateQuestion(Long questionId, CourseQuestionRequest request, Long userId) {
        CourseQuestion question = courseQnaService.getQuestionById(questionId);
        if (!question.getUser().getId().equals(userId)) {
            throw new IllegalStateException("본인만 수정할 수 있습니다.");
        }
        question.setTitle(request.getTitle());
        question.setContent(request.getContent());
    }

    @Override
    public void updateAnswer(Long answerId, CourseAnswerRequest request, Long teacherId) {
        CourseAnswer answer = courseQnaService.getAnswerById(answerId);
        if (!answer.getTeacher().getId().equals(teacherId)) {
            throw new IllegalStateException("본인만 수정할 수 있습니다.");
        }
        answer.setContent(request.getContent());
    }

    @Override
    public void deleteQuestion(Long questionId, Long userId) {
        CourseQuestion question = courseQnaService.getQuestionById(questionId);
        if (!question.getUser().getId().equals(userId)) {
            throw new IllegalStateException("본인만 삭제할 수 있습니다.");
        }
        courseQnaService.deleteQuestion(questionId);
    }

    @Override
    public void deleteAnswer(Long answerId, Long teacherId) {
        CourseAnswer answer = courseQnaService.getAnswerById(answerId);
        if (!answer.getTeacher().getId().equals(teacherId)) {
            throw new IllegalStateException("본인만 삭제할 수 있습니다.");
        }
        courseQnaService.deleteAnswer(answerId);
    }
}




