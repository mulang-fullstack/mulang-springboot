package yoonsome.mulang.infra.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import yoonsome.mulang.domain.course.repository.CourseRepository;
import yoonsome.mulang.domain.enrollment.entity.EnrollmentStatus;
import yoonsome.mulang.domain.enrollment.repository.EnrollmentRepository;
import yoonsome.mulang.domain.user.entity.User;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class CourseAccessService {
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    public boolean canAccessCourse(Long courseId, Authentication authentication) {
        if (authentication == null) {
            return false;
        }

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        // 1. 관리자 체크
        if (hasRole(authorities, "ROLE_ADMIN")) {
            return true;
        }

        // 2. 강사이면서 본인 강의인지 체크 (User ID로 직접 확인)
        if (hasRole(authorities, "ROLE_TEACHER")) {
            return courseRepository.existsByIdAndTeacherUserId(courseId, user.getId());
        }

        // 3. 학생이면서 활성 수강신청이 있는지 체크
        if (hasRole(authorities, "ROLE_STUDENT")) {
            return enrollmentRepository.existsByUserEmailAndCourseIdAndStatus(
                    user.getEmail(), courseId, EnrollmentStatus.ACTIVE
            );
        }

        return false;
    }

    private boolean hasRole(Collection<? extends GrantedAuthority> authorities, String role) {
        return authorities.stream()
                .anyMatch(auth -> auth.getAuthority().equals(role));
    }
}