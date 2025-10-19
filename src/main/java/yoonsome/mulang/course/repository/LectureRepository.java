package yoonsome.mulang.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yoonsome.mulang.course.entity.Lecture;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
}
