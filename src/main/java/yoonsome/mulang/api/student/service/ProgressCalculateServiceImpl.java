package yoonsome.mulang.api.student.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yoonsome.mulang.domain.progress.repository.ProgressRepository;

@Service
@RequiredArgsConstructor
public class ProgressCalculateServiceImpl implements ProgressCalculateService {

    private final ProgressRepository progressRepository;

    @Override
    public Integer progressCalculate(Long userId, Long courseId) {
        Long totalcount = progressRepository.countTotalLecturesByCourseId(userId, courseId);
        Long watchcount = progressRepository.countCompletedLecturesByCourseId(userId, courseId);

        int progress = (int) Math.round(((double) watchcount / totalcount) * 100);

        return progress;
    }
}
