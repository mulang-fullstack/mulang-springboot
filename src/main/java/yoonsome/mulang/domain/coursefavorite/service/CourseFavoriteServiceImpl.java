package yoonsome.mulang.domain.coursefavorite.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yoonsome.mulang.domain.course.entity.Course;
import yoonsome.mulang.domain.course.repository.CourseRepository;
import yoonsome.mulang.domain.coursefavorite.entity.CourseFavorite;
import yoonsome.mulang.domain.coursefavorite.repository.CourseFavoriteRepository;
import yoonsome.mulang.domain.enrollment.entity.Enrollment;
import yoonsome.mulang.domain.enrollment.repository.EnrollmentRepository;
import yoonsome.mulang.domain.user.entity.User;
import yoonsome.mulang.domain.user.repository.UserRepository;

import java.util.List;
import java.util.Set;

@Transactional
@RequiredArgsConstructor
@Service
public class CourseFavoriteServiceImpl implements CourseFavoriteService {
    @Autowired
    private CourseFavoriteRepository courseFavoriteRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Override
    public boolean existsCourseFavorite(Long studentId, Long courseId) {
        return courseFavoriteRepository.existsByStudentIdAndCourseId(studentId, courseId);
    }
    @Override
    public void addOrRemoveCourseFavorite(Long studentId, Long courseId) {

        /*나의 학습방에서 수강중인지 아닌지 여부 확인*/
        List<Enrollment> enrollmentInfo = enrollmentRepository.findByUserIdWithCourse(studentId);

        boolean isEnrolled = false;
        for (Enrollment enrollment : enrollmentInfo) {
            if (enrollment.getCourse().getId().equals(courseId)) {
                isEnrolled = true;
                break;
            }
        }
        if (isEnrolled) {
            //수강중일 경우
            throw new IllegalStateException("이미 수강 중인 강좌는 찜할 수 없습니다.");
        } else {
            /*찜 존재 여부 확인*/
            boolean exists = existsCourseFavorite(studentId, courseId);



            /*찜 이미 존재 시 제거, 아니면 추가*/
            if (exists) {
                courseFavoriteRepository.deleteByStudentIdAndCourseId(studentId, courseId);
            } else {
                User student = userRepository.findById(studentId)
                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 학생입니다. id=" + studentId));
                Course course = courseRepository.findById(courseId)
                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 강좌입니다. id=" + courseId));

                CourseFavorite courseFavorite = CourseFavorite.builder()
                        .student(student)
                        .course(course)
                        .build();
                courseFavoriteRepository.save(courseFavorite);
            }
        }
    }
    @Override
    public Set<Long> getCourseIdsByUserId(Long studentId) {
        return courseFavoriteRepository.findCourseIdsByStudentId(studentId);
    }
}
