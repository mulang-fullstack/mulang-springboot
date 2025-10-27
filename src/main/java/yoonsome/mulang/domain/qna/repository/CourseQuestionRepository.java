package yoonsome.mulang.domain.qna.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import yoonsome.mulang.domain.qna.entity.CourseQuestion;

import java.util.List;


public interface CourseQuestionRepository extends JpaRepository<CourseQuestion, Long> {


    //특정 강좌의 모든 질문 조회
    Page<CourseQuestion> findByCourse_IdOrderByCreatedAtDesc(Long courseId, Pageable pageable);

    //특정 사용자가 작성한 질문 조회 (최순실)
    List<CourseQuestion> findByUser_IdOrderByCreatedAtDesc(Long userId);

    //특정 사용자가 작성한 질문 조회 (오래된순)
    List<CourseQuestion> findByUser_IdOrderByCreatedAtAsc(Long userId);
}
