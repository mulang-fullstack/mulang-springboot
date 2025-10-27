package yoonsome.mulang.domain.lecture.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yoonsome.mulang.domain.course.entity.Course;
import yoonsome.mulang.domain.lecture.entity.Lecture;

import java.util.List;
import java.util.Optional;

public interface LectureRepository extends JpaRepository<Lecture, Long> {

    // 특정 코스에 속한 강의 목록 조회
    List<Lecture> findByCourse_Id(Long courseId);

    // 특정 코스 내 특정 강의 조회
    Optional<Lecture> findByCourse_IdAndId(Long courseId, Long lectureId);

    // lecture 개수 세기
    int countByCourse(Course course);

    //lecture 순서
    List<Lecture> findByCourseOrderByOrderIndexAsc(Course course);
}
