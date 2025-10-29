package yoonsome.mulang.domain.qna.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yoonsome.mulang.domain.qna.entity.CourseAnswer;
import yoonsome.mulang.domain.qna.entity.CourseQuestion;
import yoonsome.mulang.domain.qna.repository.CourseAnswerRepository;
import yoonsome.mulang.domain.qna.repository.CourseQuestionRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CourseQnaServiceImpl implements CourseQnaService {

    private final CourseQuestionRepository courseQuestionRepository;
    private final CourseAnswerRepository courseAnswerRepository;

    @Override
    public CourseQuestion createQuestion(CourseQuestion question) {
        return courseQuestionRepository.save(question);
    }

    @Override
    public CourseAnswer createAnswer(CourseAnswer answer) {
        return courseAnswerRepository.save(answer);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CourseQuestion> getQuestionPageByCourse(Long courseId, Pageable pageable) {
        return courseQuestionRepository.findByCourse_IdOrderByCreatedAtDesc(courseId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CourseAnswer> getAnswerPageByQuestion(Long questionId, Pageable pageable) {
        return courseAnswerRepository.findByCourseQuestion_IdOrderByCreatedAtAsc(questionId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseQuestion> getQuestionPageByUserDesc(Long userId) {
        return courseQuestionRepository.findByUser_IdOrderByCreatedAtDesc(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseQuestion> getQuestionPageByUserAsc(Long userId) {
        return courseQuestionRepository.findByUser_IdOrderByCreatedAtAsc(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CourseAnswer> getAnswerPageByUser(Long userId, Pageable pageable) {
        return courseAnswerRepository.findByUser_IdOrderByCreatedAtDesc(userId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public CourseQuestion getQuestionById(Long questionId) {
        return courseQuestionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("질문을 찾을 수 없습니다. ID=" + questionId));
    }

    @Override
    @Transactional(readOnly = true)
    public CourseAnswer getAnswerById(Long answerId) {
        return courseAnswerRepository.findById(answerId)
                .orElseThrow(() -> new IllegalArgumentException("답변을 찾을 수 없습니다. ID=" + answerId));
    }

    @Override
    public void deleteQuestion(Long questionId) {
        // [1] 연결된 모든 답변 먼저 삭제
        List<CourseAnswer> answers = courseAnswerRepository.findByCourseQuestion_Id(questionId);
        if (!answers.isEmpty()) {
            courseAnswerRepository.deleteAll(answers);
        }
        // [2] 질문 삭제
        courseQuestionRepository.deleteById(questionId);
    }

    @Override
    public void deleteAnswer(Long answerId) {
        courseAnswerRepository.deleteById(answerId);
    }
}
