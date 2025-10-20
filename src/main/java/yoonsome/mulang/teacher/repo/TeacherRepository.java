package yoonsome.mulang.teacher.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import yoonsome.mulang.teacher.entity.Teacher;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Optional<Teacher> findByUserId(Long userId);

}
