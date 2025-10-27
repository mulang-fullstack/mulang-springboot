package yoonsome.mulang.domain.qna.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import yoonsome.mulang.domain.qna.entity.CourseAnswer;

import java.util.List;

public interface CourseAnswerRepository extends JpaRepository<CourseAnswer, Long> {

    //특정 질문에 달린 모든 답변 조회
    Page<CourseAnswer> findByCourseQuestion_IdOrderByCreatedAtAsc(Long questionId, Pageable pageable);

    //특정 교사가 작성한 답변 조회
    Page<CourseAnswer> findByTeacher_IdOrderByCreatedAtDesc(Long teacherId, Pageable pageable);
}
