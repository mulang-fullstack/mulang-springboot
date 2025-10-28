//package yoonsome.mulang.domain.progress.repository;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//import yoonsome.mulang.api.student.dto.MycourseDTO;
//
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public interface EnrollmentRepository extends JpaRepository<yoonsome.mulang.domain.progress.entity.Progress, Long> {
//
//    Optional<yoonsome.mulang.domain.progress.entity.Progress> findByStudentIdAndLectureId(Long studentId, Long lectureId);
//
//    // 특정 학생이 수강 중인 강좌별 진척도 조회
//    @Query("SELECT new yoonsome.mulang.api.student.dto.MycourseDTO(" + // enrollment기준 조회
//            "l.course.id, " +
//            "l.course.title, " +
//            "COALESCE(l.course.thumbnail, ''), " +
//            "COALESCE(l.course.teacher.user.username, ''), " +
//            "(SELECT COUNT(lec) FROM Lecture lec WHERE lec.course.id = l.course.id), " +
//            "COALESCE(SUM(CASE WHEN e.progressStatus = true THEN 1 ELSE 0 END), 0)) " +
//            "FROM Progress e " +
//            "JOIN e.lecture l " +
//            "LEFT JOIN l.course.teacher t " +
//            "LEFT JOIN t.user " +
//            "WHERE e.student.id = :studentId " +
//            "GROUP BY l.course.id, l.course.title, l.course.thumbnail, l.course.teacher.user.username")
//
//
//    List<MycourseDTO> findCourseProgressByStudentId(@Param("studentId") Long studentId);
//
//    void deleteByLectureId(Long lectureId);
//
//}
