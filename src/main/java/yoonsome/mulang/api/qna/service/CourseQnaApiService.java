package yoonsome.mulang.api.qna.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import yoonsome.mulang.api.qna.dto.CourseAnswerRequest;
import yoonsome.mulang.api.qna.dto.CourseAnswerResponse;
import yoonsome.mulang.api.qna.dto.CourseQuestionRequest;
import yoonsome.mulang.api.qna.dto.CourseQuestionResponse;
import yoonsome.mulang.domain.course.entity.Course;
import yoonsome.mulang.domain.qna.entity.CourseAnswer;
import yoonsome.mulang.domain.qna.entity.CourseQuestion;
import yoonsome.mulang.domain.teacher.entity.Teacher;
import yoonsome.mulang.domain.user.entity.User;

public interface CourseQnaApiService {

    void createQuestion(CourseQuestionRequest request, User user, Course course);

    void createAnswer(CourseAnswerRequest request, Teacher teacher, CourseQuestion question);

}
