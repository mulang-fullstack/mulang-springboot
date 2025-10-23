package yoonsome.mulang.domain.lecture.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import yoonsome.mulang.domain.course.entity.Course;
import yoonsome.mulang.domain.lecture.entity.Lecture;
import yoonsome.mulang.domain.lecture.repository.LectureRepository;
import yoonsome.mulang.infra.file.entity.File;
import yoonsome.mulang.infra.file.service.FileService;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureServiceImpl implements LectureService {

    private final LectureRepository lectureRepository;
    private final FileService fileService;

    @Override
    public void createLectureWithFile(String title,String content, Course course, MultipartFile video)
            throws IOException {
        if (video == null || video.isEmpty()) {
            throw new IllegalArgumentException("강의 영상은 필수입니다.");
        }
        File savedFile = fileService.createFile(video, null);

        Lecture lecture = new Lecture();
        lecture.setCourse(course);
        lecture.setTitle(title);
        lecture.setContent(content);
        lecture.setFile(savedFile);
        lectureRepository.save(lecture);
    }
    @Override
    public Lecture getLectureById(Long lectureId){
        return lectureRepository.findById(lectureId)
                .orElseThrow(() -> new IllegalArgumentException("강의를 찾을 수 없습니다."));
    }
    @Override
    public List<Lecture> getLecturesByCourseId(Long courseId) {
        return lectureRepository.findByCourse_Id(courseId);
    }
}
