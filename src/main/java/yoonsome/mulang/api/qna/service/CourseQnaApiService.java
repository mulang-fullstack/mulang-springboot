package yoonsome.mulang.api.qna.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import yoonsome.mulang.api.qna.dto.CourseAnswerRequest;
import yoonsome.mulang.api.qna.dto.CourseQuestionRequest;
import yoonsome.mulang.api.qna.dto.CourseQuestionResponse;
import yoonsome.mulang.domain.course.entity.Course;
import yoonsome.mulang.domain.qna.entity.CourseQuestion;
import yoonsome.mulang.domain.teacher.entity.Teacher;
import yoonsome.mulang.domain.user.entity.User;

public interface CourseQnaApiService {

    //질문 등록
    void createQuestion(CourseQuestionRequest request, Long userId);
    //답변 등록
    void createAnswer(CourseAnswerRequest request, Long teacherId);
    //강좌별 Q&A 전체조회
    Page<CourseQuestionResponse> getQnaByCourse(Long courseId, Pageable pageable, User currentUser);
    //질문 업데이트
    void updateQuestion(Long questionId, CourseQuestionRequest request, Long userId);
    //답변 업데이트
    void updateAnswer(Long answerId, CourseAnswerRequest request, Long teacherId);
    //질문 삭제
    void deleteQuestion(Long questionId, Long userId);
    //답변 삭제
    void deleteAnswer(Long answerId, Long teacherId);
}
