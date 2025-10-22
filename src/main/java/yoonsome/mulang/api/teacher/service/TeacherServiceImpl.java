package yoonsome.mulang.api.teacher.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import yoonsome.mulang.api.teacher.dto.CourseUploadRequest;
import yoonsome.mulang.api.teacher.dto.LectureUploadRequest;
import yoonsome.mulang.api.teacher.dto.TeacherProfileUpdateRequest;
import yoonsome.mulang.domain.course.entity.Course;
import yoonsome.mulang.domain.course.repository.CourseRepository;
import yoonsome.mulang.domain.lecture.service.LectureService;
import yoonsome.mulang.domain.teacher.entity.Teacher;
import yoonsome.mulang.domain.user.entity.User;
import yoonsome.mulang.domain.user.repository.UserRepository;
import yoonsome.mulang.infra.file.entity.File;
import yoonsome.mulang.infra.file.service.FileService;
import yoonsome.mulang.domain.teacher.repository.TeacherRepository;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final CourseRepository courseRepository;
    private final FileService fileService;
    private final UserRepository userRepository;
    private final LectureService lectureService;

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

    @Transactional
    @Override
    public void updateTeacherProfile(Long userId, TeacherProfileUpdateRequest request)
            throws IOException {

        Teacher teacher = teacherRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("교사 정보가 존재하지 않습니다."));
        teacher.setIntroduction(request.getIntroduction());
        teacher.setCarreer(request.getCarreer());

        if (request.getPhoto() != null && !request.getPhoto().isEmpty()) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("유저 정보가 존재하지 않습니다."));
            File savedPhoto = fileService.createFile(request.getPhoto());
            user.setFile(savedPhoto);
        }
    }

    @Override
    public Teacher createTeacher(User user) {
        Teacher teacher = new Teacher();
        teacher.setUser(user);
        return teacherRepository.save(teacher);
    }

    @Transactional
    public void createCourse(Long userId, CourseUploadRequest request) throws IOException {
        Teacher teacher = teacherRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("교사 정보가 존재하지 않습니다."));

        Course course = new Course();
        course.setTeacher(teacher);
        course.setTitle(request.getTitle());
        course.setSubtitle(request.getSubtitle());
        course.setContent(request.getContent());
        course.setPrice(request.getPrice());

        if (request.getThumbnail() != null && !request.getThumbnail().isEmpty()) {
            File savedThumb = fileService.createFile(request.getThumbnail());
            course.setThumbnail(savedThumb.getUrl());
        }

        Course savedCourse = courseRepository.save(course);

        if (request.getLectures() != null) {
            for (LectureUploadRequest lectureReq : request.getLectures()) {
                lectureService.createLectureWithFile(
                        lectureReq.getTitle(), savedCourse, lectureReq.getVideo()
                );
            }
        }
    }
}


