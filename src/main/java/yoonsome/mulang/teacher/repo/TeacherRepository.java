package yoonsome.mulang.teacher.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import yoonsome.mulang.teacher.entity.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
