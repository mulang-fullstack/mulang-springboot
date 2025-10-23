package yoonsome.mulang.api.teacher.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import yoonsome.mulang.api.teacher.dto.CourseUploadRequest;
import yoonsome.mulang.api.teacher.dto.LectureUploadRequest;
import yoonsome.mulang.api.teacher.dto.TeacherProfileUpdateRequest;
import yoonsome.mulang.domain.category.entity.Category;
import yoonsome.mulang.domain.category.repository.CategoryRepository;
import yoonsome.mulang.domain.category.service.CategoryService;
import yoonsome.mulang.domain.course.entity.Course;
import yoonsome.mulang.domain.course.entity.StatusType;
import yoonsome.mulang.domain.course.repository.CourseRepository;
import yoonsome.mulang.domain.course.service.CourseService;
import yoonsome.mulang.domain.language.entity.Language;
import yoonsome.mulang.domain.language.repository.LanguageRepository;
import yoonsome.mulang.domain.language.service.LanguageService;
import yoonsome.mulang.domain.lecture.service.LectureService;
import yoonsome.mulang.domain.teacher.entity.Teacher;
import yoonsome.mulang.domain.teacher.service.TeacherService;
import yoonsome.mulang.domain.user.entity.User;
import yoonsome.mulang.domain.user.service.UserService;
import yoonsome.mulang.infra.file.entity.File;
import yoonsome.mulang.infra.file.service.FileService;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TeacherMypageServiceImpl implements TeacherMypageService {

    private final TeacherService teacherService;
    private final FileService fileService;
    private final UserService userService;
    private final LectureService lectureService;
    private final CourseService courseService;
    private final CategoryService categoryService;
    private final LanguageService  languageService;

    // 교사 본인의 강좌 목록 조회
    @Override
    public List<Course> getTeacherCourses(Long userId) {
        Teacher teacher = teacherService.getTeacherByUserId(userId);
        return courseService.getCoursesByTeacher(teacher.getId());
    }

    // 교사 프로필 조회
    @Override
    public Teacher getTeacherProfile(Long userId) {
        return teacherService.getTeacherByUserId(userId);
    }

    // 교사 프로필 수정
    @Override
    @Transactional
    public void updateTeacherProfile(Long userId, TeacherProfileUpdateRequest request) throws IOException {
        Teacher teacher = teacherService.getTeacherByUserId(userId);
        teacher.setIntroduction(request.getIntroduction());
        teacher.setCareer(request.getCareer());

        User user = userService.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("유저 정보가 존재하지 않습니다.");
        }
        if (request.getNickname() != null && !request.getNickname().isBlank()) {
            user.setNickname(request.getNickname());
        }
        if (request.getEmail() != null && !request.getEmail().isBlank()) {
            user.setEmail(request.getEmail());
        }

        MultipartFile photo = request.getPhoto();
        if (photo != null && !photo.isEmpty()) {
            File savedPhoto = fileService.createFile(photo);
            user.setFile(savedPhoto);
        }
    }

    // 강좌 생성
    @Override
    public void createCourse(Long userId, CourseUploadRequest request) throws IOException {
        Teacher teacher = teacherService.getTeacherByUserId(userId);
        Language language= languageService.getById(request.getLanguageId());
        Category category = categoryService.getById(request.getCategoryId());


        Course course = new Course();
        course.setTeacher(teacher);
        course.setTitle(request.getTitle());
        course.setSubtitle(request.getSubtitle());
        course.setContent(request.getContent());

        course.setHtmlContent(request.getContent());
        //course.setContent(Jsoup.parse(request.getContent()).text());

        course.setPrice(request.getPrice());
        course.setCategory(category);
        course.setLanguage(language);
        course.setStatus(request.getStatus() != null ? request.getStatus() : StatusType.PENDING);

        course.setLectureCount(request.getLectureCount() != null ? request.getLectureCount() : 1);

        if (request.getThumbnailFile() != null && !request.getThumbnailFile().isEmpty()) {
            File savedThumb = fileService.createFile(request.getThumbnailFile());
            course.setThumbnail(savedThumb.getUrl());
        }

        Course savedCourse = courseService.registerCourse(course);

        if (request.getLectures() != null) {
            for (LectureUploadRequest lectureReq : request.getLectures()) {
                lectureService.createLectureWithFile(
                        lectureReq.getTitle(),
                        lectureReq.getContent(),
                        savedCourse,
                        lectureReq.getVideo()
                );
            }
        }
    }
}