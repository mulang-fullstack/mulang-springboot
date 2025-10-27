package yoonsome.mulang.domain.qna.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import yoonsome.mulang.domain.qna.entity.CourseAnswer;
import yoonsome.mulang.domain.qna.entity.CourseQuestion;

public interface CourseQnaService {

    /** 질문 등록 */
    CourseQuestion createQuestion(CourseQuestion question);

    /** 답변 등록 */
    CourseAnswer createAnswer(CourseAnswer answer);

    /** 강좌별 질문 페이징 조회 */
    Page<CourseQuestion> getQuestionPageByCourse(Long courseId, Pageable pageable);

    /** 질문별 답변 페이징 조회 */
    Page<CourseAnswer> getAnswerPageByQuestion(Long questionId, Pageable pageable);

    /** 특정 사용자 질문 페이징 조회 */
    Page<CourseQuestion> getQuestionPageByUser(Long userId, Pageable pageable);

    /** 특정 교사 답변 페이징 조회 */
    Page<CourseAnswer> getAnswerPageByTeacher(Long teacherId, Pageable pageable);

    /** 단일 질문 조회 */
    CourseQuestion getQuestionById(Long questionId);

    /** 단일 답변 조회 */
    CourseAnswer getAnswerById(Long answerId);

    /** 질문 삭제 */
    void deleteQuestion(Long questionId);

    /** 답변 삭제 */
    void deleteAnswer(Long answerId);
}
