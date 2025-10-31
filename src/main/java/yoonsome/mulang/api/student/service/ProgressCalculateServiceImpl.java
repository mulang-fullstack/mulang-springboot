package yoonsome.mulang.api.student.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yoonsome.mulang.domain.course.entity.Course;
import yoonsome.mulang.domain.course.repository.CourseRepository;
import yoonsome.mulang.domain.progress.repository.ProgressRepository;

@Service
@RequiredArgsConstructor
public class ProgressCalculateServiceImpl implements ProgressCalculateService {

    private final ProgressRepository progressRepository;
    private final CourseRepository courseRepository;

    @Override
    public Integer progressCalculate(Long userId, Long courseId) {
        //Long totalcount = progressRepository.countTotalLecturesByCourseId(userId, courseId);
        Course course = courseRepository.findById(courseId).orElseThrow();
        int totalcount = course.getLectureCount();
        Long watchcount = progressRepository.countCompletedLecturesByCourseId(userId, courseId);

        int progress = (int) Math.round(((double) watchcount / totalcount) * 100);

        return progress;
    }
}
