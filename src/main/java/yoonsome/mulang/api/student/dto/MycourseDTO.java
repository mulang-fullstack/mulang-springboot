package yoonsome.mulang.api.student.dto;

import lombok.Getter;
import lombok.ToString;
import yoonsome.mulang.domain.enrollment.entity.EnrollmentEntity;
import yoonsome.mulang.domain.enrollment.repository.EnrollmentRepository;
import yoonsome.mulang.domain.lecture.entity.Lecture;
import yoonsome.mulang.domain.lecture.repository.LectureRepository;
import yoonsome.mulang.domain.payment.entity.CourseEnrollment;

import java.util.List;

@Getter
@ToString
public class MycourseDTO {
    private Long courseId;
    private String courseTitle;
    private String courseThumbnail;
    private String teacherName;
    private Long totalLectures;
    private Long viewedLectures;
    private Double progressPercentage;

    // JPQL new 키워드용 생성자 (6개 파라미터)
    public MycourseDTO(Long courseId, String courseTitle, String courseThumbnail,
                       String teacherName, Long totalLectures, Long viewedLectures) {
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.courseThumbnail = courseThumbnail;
        this.teacherName = teacherName;
        this.totalLectures = totalLectures;
        this.viewedLectures = viewedLectures;

        if(totalLectures != null && totalLectures > 0 && viewedLectures != null){
            this.progressPercentage = Math.round((viewedLectures*100.0)/totalLectures * 10.0)/10.0;
        } else{
            this.progressPercentage = 0.0;
        }
    }

    // 7개 파라미터 생성자
    public MycourseDTO(Long courseId, String courseTitle, String courseThumbnail,
                       String teacherName, Long totalLectures, Long viewedLectures,
                       Double progressPercentage) {
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.courseThumbnail = courseThumbnail;
        this.teacherName = teacherName;
        this.totalLectures = totalLectures;
        this.viewedLectures = viewedLectures;
        this.progressPercentage = progressPercentage;
    }

    // 정적 팩토리 메서드 (진행도 계산 포함!)
    public static MycourseDTO from(CourseEnrollment enrollment,
                                   Long studentId,
                                   LectureRepository lectureRepository,
                                   EnrollmentRepository enrollmentRepository) {
        Long courseId = enrollment.getCourse().getId();

        // 전체 강의 수 조회
        List<Lecture> lectures = lectureRepository.findByCourse_Id(courseId);
        Long totalLectures = (long) lectures.size();

        // 수강 완료한 강의 수 조회
        Long viewedLectures = lectures.stream()
                .filter(lecture -> enrollmentRepository
                        .findByStudentIdAndLectureId(studentId, lecture.getId())
                        .map(EnrollmentEntity::getEnrollmentStatus)
                        .orElse(false))
                .count();

        // 6개 파라미터 생성자 사용 (진행도 자동 계산됨!)
        return new MycourseDTO(
                courseId,
                enrollment.getCourse().getTitle(),
                enrollment.getCourse().getThumbnail(),
                enrollment.getCourse().getTeacher() != null
                        ? enrollment.getCourse().getTeacher().getUser().getUsername()
                        : "강사 정보 없음",
                totalLectures,
                viewedLectures
        );
    }
}
