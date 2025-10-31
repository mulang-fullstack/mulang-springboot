package yoonsome.mulang.domain.enrollment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yoonsome.mulang.domain.enrollment.entity.Enrollment;
import yoonsome.mulang.domain.enrollment.entity.EnrollmentStatus;
import yoonsome.mulang.domain.enrollment.repository.EnrollmentRepository;
import yoonsome.mulang.domain.lecture.entity.Lecture;
import yoonsome.mulang.domain.lecture.repository.LectureRepository;
import yoonsome.mulang.domain.payment.entity.Payment;
import yoonsome.mulang.domain.progress.entity.Progress;
import yoonsome.mulang.domain.progress.repository.ProgressRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 수강신청 서비스 구현체
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final LectureRepository lectureRepository;
    private final ProgressRepository progressRepository;

    /**
     * 수강신청 생성
     */
    @Override
    @Transactional
    public Long createEnrollment(Payment payment) {
        // 1. Enrollment 생성
        Enrollment enrollment = Enrollment.builder()
                .user(payment.getUser())
                .course(payment.getCourse())
                .payment(payment)
                .progress(0)
                .isCompleted(false)
                .build();

        Enrollment saved = enrollmentRepository.save(enrollment);
        log.info("✅ Enrollment 생성 완료 - UserId: {}, CourseId: {}",
                payment.getUser().getId(), payment.getCourse().getId());

        // 2. 해당 강좌의 모든 강의 조회
        List<Lecture> lectures = lectureRepository.findByCourseId(
                payment.getCourse().getId()
        );

        // 3. 각 강의에 대한 Progress 생성
        int createdCount = 0;
        for (Lecture lecture : lectures) {
            // 중복 체크
            if (!progressRepository.existsByStudentIdAndLectureId(
                    payment.getUser().getId(), lecture.getId())) {

                Progress progress = Progress.builder()
                        .lecture(lecture)
                        .student(payment.getUser())
                        .enrollmentStatus(false) // 초기값: 미시청
                        .build();

                progressRepository.save(progress);
                createdCount++;
            }
        }

        log.info("✅ Progress 생성 완료 - CourseId: {}, 생성된 Progress 수: {}/{}",
                payment.getCourse().getId(), createdCount, lectures.size());

        return saved.getId();
    }

    /**
     * 중복 구매 체크
     */
    @Override
    public boolean hasEnrollment(Long userId, Long courseId) {
        return enrollmentRepository.existsByUserIdAndCourseId(userId, courseId);
    }

    /**
     * 강의 취소
     * @param userId 사용자
     * @param courseId 강좌
     */
    @Override
    @Transactional
    public void cancelEnrollment(Long userId, Long courseId) {
        Enrollment enrollment = enrollmentRepository
                .findByUserIdAndCourseId(userId, courseId)
                .orElseThrow(() -> new IllegalArgumentException("수강신청 정보를 찾을 수 없습니다."));

        // 상태만 변경 (삭제 X)
        enrollment.setStatus(EnrollmentStatus.REFUNDED);
        enrollment.setCancelledAt(LocalDateTime.now());

        enrollmentRepository.save(enrollment);

        // Progress는 그대로 남아있음!
    }
}
