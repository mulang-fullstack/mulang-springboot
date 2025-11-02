package yoonsome.mulang.domain.teacher.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import yoonsome.mulang.domain.teacher.entity.Teacher;
import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    // 유저 ID로 교사 조회
    @Query("""
        SELECT t FROM Teacher t
        JOIN FETCH t.user u
        WHERE u.id = :userId
    """)
    Optional<Teacher> findByUserId(@Param("userId") Long userId);

    // 교사 ID로 단건 조회
    @Query("""
        SELECT t FROM Teacher t
        JOIN FETCH t.user u
        WHERE t.id = :teacherId
    """)
    Optional<Teacher> findByTeacherId(@Param("teacherId") Long teacherId);

}
