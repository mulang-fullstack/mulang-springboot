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
    public void createAnswer(CourseAnswerRequest request, Long userId) {
        CourseQuestion question = courseQnaService.getQuestionById(request.getQuestionId());
        User user = userService.findById(userId);

        CourseAnswer answer = CourseAnswer.builder()
                .content(request.getContent())
                .courseQuestion(question)
                .user(user)
                .createdAt(Timestamp.from(Instant.now()))
                .build();

        courseQnaService.createAnswer(answer);
    }

    /**
     * 강좌별 Q&A 조회
     */
    @Override
    public Page<CourseQuestionResponse> getQnaByCourse(Long courseId, Pageable pageable, User currentUser) {
        Page<CourseQuestion> questionPage = courseQnaService.getQuestionPageByCourse(courseId, pageable);
        return buildQnaPage(questionPage, pageable, currentUser);
    }

    /**
     * Q&A 질문·답변 페이지 변환
     * 관리자 / 작성자 여부에 따라 deletable 필드 부여
     * 관리자 / 작성자 여부에 따라 editable 필드 부여
     */
    private Page<CourseQuestionResponse> buildQnaPage(Page<CourseQuestion> questionPage, Pageable pageable, User currentUser) {
        List<CourseQuestionResponse> questionResponseList = new ArrayList<>();
        boolean isAdmin = currentUser != null && currentUser.getRole() == User.Role.ADMIN;

        for (CourseQuestion question : questionPage.getContent()) {
            Page<CourseAnswer> answerPage = courseQnaService.getAnswerPageByQuestion(question.getId(), pageable);

            List<CourseAnswerResponse> answers = answerPage.getContent().stream().map(answer -> {
                String writerName = (answer.getUser() != null && answer.getUser().getNickname() != null)
                        ? answer.getUser().getNickname()
                        : "익명";

                boolean isMyAnswer = currentUser != null
                        && answer.getUser() != null
                        && currentUser.getId().equals(answer.getUser().getId());

                return CourseAnswerResponse.builder()
                        .id(answer.getId())
                        .content(answer.getContent())
                        .teacherName(writerName)
                        .createdAt(answer.getCreatedAt())
                        .deletable(isAdmin || isMyAnswer)
                        .editable(isAdmin || isMyAnswer)
                        .build();
            }).toList();

            String questionWriter = (question.getUser() != null && question.getUser().getNickname() != null)
                    ? question.getUser().getNickname()
                    : "익명";

            boolean isMyQuestion = currentUser != null
                    && question.getUser() != null
                    && currentUser.getId().equals(question.getUser().getId());

            questionResponseList.add(CourseQuestionResponse.builder()
                    .id(question.getId())
                    .title(question.getTitle())
                    .content(question.getContent())
                    .writerName(questionWriter)
                    .createdAt(question.getCreatedAt())
                    .answers(answers)
                    .deletable(isAdmin || isMyQuestion)
                    .editable(isAdmin || isMyQuestion)
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
    public void updateAnswer(Long answerId, CourseAnswerRequest request, Long userId) {
        CourseAnswer answer = courseQnaService.getAnswerById(answerId);
        if (!answer.getUser().getId().equals(userId)) {
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
    public void deleteAnswer(Long answerId, Long userId) {
        CourseAnswer answer = courseQnaService.getAnswerById(answerId);
        if (!answer.getUser().getId().equals(userId)) {
            throw new IllegalStateException("본인만 삭제할 수 있습니다.");
        }
        courseQnaService.deleteAnswer(answerId);
    }
}
