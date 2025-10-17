package yoonsome.mulang.teacher.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import yoonsome.mulang.teacher.entity.Tutor;

public interface TeacherRepository extends JpaRepository<Tutor, Long> {
}
