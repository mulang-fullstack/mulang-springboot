package yoonsome.mulang.api.teacher.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import yoonsome.mulang.api.teacher.dto.*;
import yoonsome.mulang.domain.category.entity.Category;
import yoonsome.mulang.domain.category.service.CategoryService;
import yoonsome.mulang.domain.course.entity.Course;
import yoonsome.mulang.domain.course.entity.StatusType;
import yoonsome.mulang.domain.course.service.CourseService;
import yoonsome.mulang.domain.language.entity.Language;
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
    public List<TeacherCourseResponse> getTeacherCourse(Long userId) {
        Teacher teacher = teacherService.getTeacherByUserId(userId);
        List<Course> courses = courseService.getCoursesByTeacher(teacher.getId());

        return courses.stream()
                .map(course -> TeacherCourseResponse.builder()
                        .id(course.getId())
                        .title(course.getTitle())
                        .subtitle(course.getSubtitle())
                        .status(course.getStatus().name())
                        .price(course.getPrice())
                        .thumbnail(course.getThumbnail())
                        .language(course.getLanguage().getName())
                        .category(course.getCategory().getName())
                        .build())
                .toList();
    }

    // 교사 프로필 조회
    @Override
    public TeacherProfileResponse getTeacherProfileResponse(Long userId) {
        Teacher teacher = teacherService.getTeacherByUserId(userId);
        User user = userService.findById(userId);

        return new TeacherProfileResponse(
                user.getUsername(),
                user.getNickname(),
                user.getEmail(),
                teacher.getIntroduction(),
                teacher.getCareer(),
                user.getFile() != null? user.getFile().getUrl() : null
        );
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

    @Override
    public TeacherCourseResponse getCourseDetail(Long userId, Long courseId) {
        Teacher teacher = teacherService.getTeacherByUserId(userId);
        Course course = courseService.getCourse(courseId);

        if (!course.getTeacher().getId().equals(teacher.getId())) {
            throw new IllegalArgumentException("본인의 강좌만 조회할 수 있습니다.");
        }

        return TeacherCourseResponse.builder()
                .id(course.getId())
                .title(course.getTitle())
                .subtitle(course.getSubtitle())
                .status(course.getStatus().name())
                .price(course.getPrice())
                .language(course.getLanguage().getName())
                .category(course.getCategory().getName())
                .thumbnail(course.getThumbnail())
                .content(course.getContent())
                .htmlContent(course.getHtmlContent())
                .createdDate(course.getCreatedDate())
                .lectureCount(course.getLectureCount())
                .build();
    }

    @Override
    @Transactional
    public void updateCourse(Long userId, Long courseId, CourseUpdateRequest request) throws IOException {
        Teacher teacher = teacherService.getTeacherByUserId(userId);
        Course course = courseService.getCourse(courseId);

        if (!course.getTeacher().getId().equals(teacher.getId())) {
            throw new IllegalArgumentException("본인의 강좌만 수정할 수 있습니다.");
        }
        if (request.getTitle() != null) course.setTitle(request.getTitle());
        if (request.getSubtitle() != null) course.setSubtitle(request.getSubtitle());
        if (request.getContent() != null) {
            course.setContent(request.getContent());
            course.setHtmlContent(request.getContent());
        }
        if (request.getPrice() != null) course.setPrice(request.getPrice());
        if (request.getStatus() != null) course.setStatus(request.getStatus());

        MultipartFile thumbnail = request.getThumbnailFile();
        if (thumbnail != null && !thumbnail.isEmpty()) {
            File savedThumb = fileService.createFile(thumbnail);
            course.setThumbnail(savedThumb.getUrl());
        }

        if (request.getCategoryId() != null) {
            Category category = categoryService.getById(request.getCategoryId());
            course.setCategory(category);
        }
        if (request.getLanguageId() != null) {
            Language language = languageService.getById(request.getLanguageId());
            course.setLanguage(language);
        }

        int lectureCount = lectureService.countByCourse(course);
        course.setLectureCount(lectureCount);
        courseService.registerCourse(course);
    }

    @Override
    @Transactional
    public void deleteCourse(Long userId, Long courseId) {
        Teacher teacher = teacherService.getTeacherByUserId(userId);
        Course course = courseService.getCourse(courseId);

        if (!course.getTeacher().getId().equals(teacher.getId())) {
            throw new IllegalArgumentException("본인의 강좌만 수정할 수 있습니다.");
        }
        course.setStatus(StatusType.PRIVATE);
    }
}