package yoonsome.mulang.domain.lecture.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yoonsome.mulang.domain.course.entity.Course;
import yoonsome.mulang.domain.enrollment.repository.EnrollmentRepository;
import yoonsome.mulang.domain.lecture.entity.Lecture;
import yoonsome.mulang.domain.lecture.repository.LectureRepository;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureServiceImpl implements LectureService {

    private final LectureRepository lectureRepository;
    private final EnrollmentRepository enrollmentRepository;


    @Override
    public Lecture getLectureById(Long lectureId){
        return lectureRepository.findById(lectureId)
                .orElseThrow(() -> new IllegalArgumentException("강의를 찾을 수 없습니다."));
    }
    @Override
    public List<Lecture> getLecturesByCourseId(Long courseId) {
        return lectureRepository.findByCourseId(courseId);
    }

    @Override
    public int countByCourse(Course course) {
        return lectureRepository.countByCourse(course);
    }

    @Override
    public List<Lecture> findByCourseOrdered(Course course) {
        return lectureRepository.findByCourseOrderByOrderIndexAsc(course);
    }
    @Override
    public Lecture save(Lecture lecture) {
        return lectureRepository.save(lecture);
    }

    @Override
    @Transactional
    public void delete(Long lectureId) {
        lectureRepository.deleteById(lectureId);
    }
}
