package yoonsome.mulang.api.student.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yoonsome.mulang.domain.lecture.entity.Lecture;
import yoonsome.mulang.domain.lecture.service.LectureService;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MycourseServiceImpl implements MycourseService{

    private final LectureService lectureService;

    @Override
    public Lecture getLecture(Long lectureId) {
        return lectureService.getLectureById(lectureId);
    }
    @Override
    public List<Lecture> getLectureListByCourseId(Long courseId) {
        return lectureService.getLecturesByCourseId(courseId);
    }
}