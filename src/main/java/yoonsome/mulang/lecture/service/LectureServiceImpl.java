package yoonsome.mulang.lecture.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import yoonsome.mulang.course.entity.Course;
import yoonsome.mulang.lecture.entity.Lecture;
import yoonsome.mulang.lecture.repository.LectureRepository;
import yoonsome.mulang.file.service.FileService;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class LectureServiceImpl implements LectureService {

    private final LectureRepository lectureRepository;
    private final FileService fileService;

    @Override
    public void createLectureWithFile(String title, Course course, MultipartFile video) throws IOException {
        Lecture lecture = new Lecture();
        lecture.setCourse(course);
        lecture.setTitle(title);
        lectureRepository.save(lecture);

        if (video != null && !video.isEmpty()) {
            fileService.createFile(video, lecture);
        }
    }
}
