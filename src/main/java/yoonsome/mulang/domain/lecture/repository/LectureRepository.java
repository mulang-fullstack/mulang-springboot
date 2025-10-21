package yoonsome.mulang.domain.lecture.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yoonsome.mulang.domain.lecture.entity.Lecture;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
}
