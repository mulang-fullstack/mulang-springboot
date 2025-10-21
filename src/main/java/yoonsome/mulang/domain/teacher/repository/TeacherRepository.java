package yoonsome.mulang.domain.teacher.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import yoonsome.mulang.domain.teacher.entity.Teacher;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    @Query("SELECT t FROM Teacher t " +
            "JOIN FETCH t.language " +
            "JOIN FETCH t.user " +
            "WHERE t.user.id = :userId")
    Optional<Teacher> findByUserId(@Param("userId") Long userId);
}
