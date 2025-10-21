package yoonsome.mulang.api.teacher.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import yoonsome.mulang.domain.course.entity.Course;
import yoonsome.mulang.domain.course.repository.CourseRepository;
import yoonsome.mulang.infra.file.entity.File;
import yoonsome.mulang.infra.file.service.FileService;
import yoonsome.mulang.api.teacher.entity.Teacher;
import yoonsome.mulang.domain.teacher.repository.TeacherRepository;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final CourseRepository courseRepository;
    private final FileService fileService;

    @Override
    public List<Course> getTeacherCourses(Long userId) {
        Teacher teacher = teacherRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("교사 정보가 존재하지 않습니다."));
        return courseRepository.findByTeacherId(teacher.getId());
    }

    @Override
    public Teacher getTeacherProfile(Long userId) {
        return teacherRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("교사 정보가 존재하지 않습니다."));
    }

    @Override
    public void updateTeacherProfile(Long userId, Teacher updateData, MultipartFile photo) throws IOException {
        Teacher teacher = teacherRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("교사 정보가 존재하지 않습니다."));

        teacher.setIntroduction(updateData.getIntroduction());
        teacher.setCarreer(updateData.getCarreer());
        teacher.setLanguage(updateData.getLanguage());

        if (photo != null && !photo.isEmpty()) {
            File savedPhoto = fileService.createFile(photo);
            teacher.setPhoto(savedPhoto.getUrl());
        }

        teacherRepository.save(teacher);
    }
}
