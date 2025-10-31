//package yoonsome.mulang.infra.security;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.stereotype.Service;
//import yoonsome.mulang.domain.course.repository.CourseRepository;
//import yoonsome.mulang.domain.enrollment.entity.EnrollmentStatus;
//import yoonsome.mulang.domain.enrollment.repository.EnrollmentRepository;
//import yoonsome.mulang.domain.payment.repository.PaymentRepository;
//
//import java.util.Collection;
//
//@Service
//@RequiredArgsConstructor
//public class CourseAccessService {
//    private final CourseRepository courseRepository;
//    private final EnrollmentRepository enrollmentRepository;
//
//    public boolean canAccessCourse(Long courseId, Authentication authentication) {
//        if (authentication == null) {
//            return false;
//        }
//
//        String email = authentication.getName();
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//
//        // 1. 관리자 체크
//        if (authorities.stream()
//                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
//            return true;
//        }
//
//        // 2. 강사이면서 본인 강의인지 체크
//        if (authorities.stream()
//                .anyMatch(auth -> auth.getAuthority().equals("ROLE_TEACHER"))) {
//            return courseRepository.existsByIdAndTeacherId(courseId, 1L);
//        }
//
//        // 3. 학생이면서 활성 수강신청이 있는지 체크
//        if (authorities.stream()
//                .anyMatch(auth -> auth.getAuthority().equals("ROLE_STUDENT"))) {
//            // Payment만 체크하는게 아니라 Enrollment도 체크!
//            return enrollmentRepository.existsByUserEmailAndCourseIdAndStatus(email, courseId, EnrollmentStatus.ACTIVE);
//        }
//
//        return false;
//    }
//}