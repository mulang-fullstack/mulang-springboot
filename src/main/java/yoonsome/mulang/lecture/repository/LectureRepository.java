package yoonsome.mulang.lecture.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yoonsome.mulang.lecture.entity.Lecture;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
}
