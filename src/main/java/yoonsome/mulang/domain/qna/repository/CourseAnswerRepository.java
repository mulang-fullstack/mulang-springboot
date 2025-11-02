package yoonsome.mulang.domain.qna.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import yoonsome.mulang.domain.qna.entity.CourseAnswer;

import java.util.List;

public interface CourseAnswerRepository extends JpaRepository<CourseAnswer, Long> {

    // 특정 질문의 모든 답변 조회
    Page<CourseAnswer> findByCourseQuestion_IdOrderByCreatedAtAsc(Long questionId, Pageable pageable);

    // 특정 유저(교사·학생 공통)가 작성한 답변 조회
    Page<CourseAnswer> findByUser_IdOrderByCreatedAtDesc(Long userId, Pageable pageable);

    // 삭제용: 특정 질문의 모든 답변 조회
    List<CourseAnswer> findByCourseQuestion_Id(Long questionId);
}
