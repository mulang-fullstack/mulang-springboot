package yoonsome.mulang.domain.progress.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yoonsome.mulang.domain.lecture.entity.Lecture;
import yoonsome.mulang.domain.lecture.repository.LectureRepository;
import yoonsome.mulang.domain.progress.entity.Progress;
import yoonsome.mulang.domain.progress.repository.ProgressRepository;
import yoonsome.mulang.domain.user.entity.User;
import yoonsome.mulang.domain.user.repository.UserRepository;


@Service
@RequiredArgsConstructor
public class ProgressShowServiceImpl implements ProgressShowService {

    private final ProgressRepository progressRepository;

    private final UserRepository userRepository;
    private final LectureRepository lectureRepository;

    @Override
    public void LectureCompleted(Long userId, Long lectureId) {

        Progress progress = progressRepository.findByStudent_IdAndLecture_Id(userId, lectureId)
                .orElse(null);

        if (progress != null) {
            if(!progress.getProgressStatus()){
                progress.setProgressStatus(true);
                progressRepository.save(progress);
            }
        }
    }
}
