package yoonsome.mulang.api.teacher.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
import yoonsome.mulang.domain.lecture.entity.Lecture;
import yoonsome.mulang.domain.lecture.service.LectureService;
import yoonsome.mulang.domain.teacher.entity.Teacher;
import yoonsome.mulang.domain.teacher.service.TeacherService;
import yoonsome.mulang.infra.file.entity.File;
import yoonsome.mulang.infra.file.service.S3FileService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class TeacherCourseServiceImpl implements TeacherCourseService {

    private final TeacherService teacherService;
    private final S3FileService s3FileService;
    private final LectureService lectureService;
    private final CourseService courseService;
    private final CategoryService categoryService;
    private final LanguageService languageService;


    // 교사 본인의 강좌 목록 조회
    @Override
    public Page<TeacherCourseResponse> getTeacherCoursePage(Long userId, Pageable pageable) {
        Teacher teacher = teacherService.getTeacherByUserId(userId);
        List<StatusType> statuses = List.of(StatusType.values());

        Page<Course> coursePage = courseService.getTeacherCoursePage(teacher, statuses, pageable);

        List<TeacherCourseResponse> responseList = coursePage.getContent().stream()
                .map(course -> {
                    // 썸네일 URL 생성
                    String thumbnailUrl = null;
                    if (course.getFile() != null) {
                        thumbnailUrl = s3FileService.getPublicUrl(course.getFile().getId());
                    }

                    return TeacherCourseResponse.builder()
                            .id(course.getId())
                            .title(course.getTitle())
                            .subtitle(course.getSubtitle())
                            .status(course.getStatus().name())
                            .statusText(course.getStatus().getBadgeText())
                            .price(course.getPrice())
                            .language(course.getLanguage().getName())
                            .category(course.getCategory().getName())
                            .thumbnail(thumbnailUrl)
                            .rejectionReason(course.getRejectionReason())
                            .lectureCount(course.getLectureCount())
                            .createdAt(course.getCreatedAt())
                            .build();
                })
                .collect(Collectors.toList());

        return new PageImpl<>(responseList, pageable, coursePage.getTotalElements());
    }
    // 강좌 생성
    @Override
    public void createCourse(Long userId, CourseUploadRequest request) throws IOException {
        final int MAX_LECTURE_COUNT = 10;

        Teacher teacher = teacherService.getTeacherByUserId(userId);
        Language language = languageService.getById(request.getLanguageId());
        Category category = categoryService.getById(request.getCategoryId());

        // [0] 강의 개수 제한
        if (request.getLectures() != null && request.getLectures().size() > MAX_LECTURE_COUNT) {
            throw new IllegalArgumentException("강의는 최대 " + MAX_LECTURE_COUNT + "개까지 업로드할 수 있습니다.");
        }
        // [1] 강좌 생성
        Course course = new Course();
        course.setTeacher(teacher);
        course.setTitle(request.getTitle());
        course.setSubtitle(request.getSubtitle());
        course.setHtmlContent(request.getContent());
        course.setContent(Jsoup.parse(request.getContent()).text());
        course.setPrice(request.getPrice());
        course.setCategory(category);
        course.setLanguage(language);
        course.setStatus(StatusType.PENDING);
        course.setLectureCount(request.getLectureCount() != null ? request.getLectureCount() : 1);

        if (request.getThumbnailFile() != null && !request.getThumbnailFile().isEmpty()) {
            File savedThumb = s3FileService.uploadThumbnail(request.getThumbnailFile());
            course.setFile(savedThumb);
        }

        Course savedCourse = courseService.registerCourse(course);

        // [2] 강의 업로드
        if (request.getLectures() != null && !request.getLectures().isEmpty()) {
            for (LectureUploadRequest lectureReq : request.getLectures()) {
                MultipartFile video = lectureReq.getVideo();
                if (video == null || video.isEmpty()) continue;
                // 파일 업로드 (FileService)
                File videoFile = s3FileService.uploadVideo(video);
                // Lecture 엔티티 조립
                Lecture lecture = new Lecture();
                lecture.setCourse(savedCourse);
                lecture.setTitle(lectureReq.getTitle());
                lecture.setContent(lectureReq.getContent());
                lecture.setFile(videoFile);
                // 도메인 서비스는 단순 저장만 담당
                lectureService.save(lecture);
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

        List<TeacherLectureEditResponse> lectureList =
                lectureService.getLecturesByCourseId(courseId).stream()
                        .map(lecture -> TeacherLectureEditResponse.builder()
                                .id(lecture.getId())
                                .title(lecture.getTitle())
                                .content(lecture.getContent())
                                .fileUrl(lecture.getFile() != null ? lecture.getFile().getUrl() : null)
                                .originalName(lecture.getFile() != null ? lecture.getFile().getOriginalName() : null)
                                .build())
                        .toList();

        return TeacherCourseResponse.builder()
                .id(course.getId())
                .title(course.getTitle())
                .subtitle(course.getSubtitle())
                .status(course.getStatus().name())
                .price(course.getPrice())
                .language(course.getLanguage().getName())
                .category(course.getCategory().getName())
                .thumbnail(s3FileService.getPublicUrl(course.getFile().getId()))
                .content(course.getContent())
                .htmlContent(course.getHtmlContent())
                .createdAt(course.getCreatedAt())
                .lectureCount(course.getLectureCount())
                .lectures(lectureList)
                .build();
    }

    @Override
    @Transactional
    public void updateCourse(Long userId, Long courseId, CourseUpdateRequest request) throws IOException {
        final int MAX_LECTURE_COUNT = 10;

        Teacher teacher = teacherService.getTeacherByUserId(userId);
        Course course = courseService.getCourse(courseId);

        if (!course.getTeacher().getId().equals(teacher.getId())) {
            throw new IllegalArgumentException("본인의 강좌만 수정할 수 있습니다.");
        }

        // [0] 강의 개수 제한
        if (request.getLectures() != null && request.getLectures().size() > MAX_LECTURE_COUNT) {
            throw new IllegalArgumentException("강의는 최대 " + MAX_LECTURE_COUNT + "개까지 업로드할 수 있습니다.");
        }

        // ---------- 기본 필드 수정 ----------
        if (request.getTitle() != null) course.setTitle(request.getTitle());
        if (request.getSubtitle() != null) course.setSubtitle(request.getSubtitle());
        if (request.getContent() != null) {
            course.setHtmlContent(request.getContent());
            course.setContent(Jsoup.parse(request.getContent()).text());
        }
        if (request.getPrice() != null) course.setPrice(request.getPrice());

        MultipartFile thumbnail = request.getThumbnailFile();
        if (thumbnail != null && !thumbnail.isEmpty()) {
            File savedThumb = s3FileService.uploadThumbnail(thumbnail);
            course.setFile(savedThumb);
        }

        if (request.getCategoryId() != null) {
            Category category = categoryService.getById(request.getCategoryId());
            course.setCategory(category);
        }
        if (request.getLanguageId() != null) {
            Language language = languageService.getById(request.getLanguageId());
            course.setLanguage(language);
        }

        // ---------- Lecture 처리 ----------
        List<Lecture> existingLectures = lectureService.getLecturesByCourseId(courseId);

        // [1] 명시적 삭제 요청만 처리
        if (request.getDeletedLectureIds() != null && !request.getDeletedLectureIds().isEmpty()) {
            for (Long lectureId : request.getDeletedLectureIds()) {
                deleteLecture(lectureId);
                System.out.println("[DELETE] 강의 삭제됨: ID=" + lectureId);
            }
        }
        // [2] 신규 및 수정 처리
        if (request.getLectures() != null && !request.getLectures().isEmpty()) {
            for (int i = 0; i < request.getLectures().size(); i++) {
                LectureUploadRequest lectureReq = request.getLectures().get(i);

                if (lectureReq.getId() == null) {
                    MultipartFile video = lectureReq.getVideo();
                    if (video == null || video.isEmpty()) {
                        System.out.println("[SKIP] 영상 파일이 없는 신규 강의 건너뜀: " + lectureReq.getTitle());
                        continue;
                    }
                    Lecture newLecture = new Lecture();
                    newLecture.setCourse(course);
                    newLecture.setTitle(lectureReq.getTitle());
                    newLecture.setContent(lectureReq.getContent());
                    newLecture.setOrderIndex(i);

                    File videoFile = s3FileService.uploadVideo(video);
                    newLecture.setFile(videoFile);

                    lectureService.save(newLecture);
                } else {
                    Lecture existing = existingLectures.stream()
                            .filter(l -> l.getId().equals(lectureReq.getId()))
                            .findFirst()
                            .orElse(null);

                    if (existing != null) {
                        existing.setTitle(lectureReq.getTitle());
                        existing.setContent(lectureReq.getContent());
                        existing.setOrderIndex(i);

                        MultipartFile video = lectureReq.getVideo();
                        if (video != null && !video.isEmpty()) {
                            File savedFile = s3FileService.uploadVideo(video);
                            existing.setFile(savedFile);
                        }
                    }
                }
            }
        }

        // ---------- Lecture 개수 및 순서 재정렬 ----------
        List<Lecture> updatedLectures = lectureService.getLecturesByCourseId(courseId);
        for (int i = 0; i < updatedLectures.size(); i++) {
            updatedLectures.get(i).setOrderIndex(i);
        }
        course.setLectureCount(updatedLectures.size());

        // ---------- 모든 상태에서 수정 시 PENDING 전환 ----------
        course.setStatus(StatusType.PENDING);

        courseService.registerCourse(course);
    }


    //course 삭제 상태변경 private로
    //빈 구현 잘가라... 팀장의 시나리오 요청 ...잘가라....
    @Override
    @Transactional
    public void deleteCourse(Long userId, Long courseId) {
    }

    //lecture 삭제 업데이트 폼에서 -누르면
    @Transactional
    public void deleteLecture(Long lectureId) {
        Lecture lecture = lectureService.getLectureById(lectureId);
        if (lecture.getFile() != null) {
            File file = lecture.getFile();
            lecture.setFile(null);
            lectureService.save(lecture);
            s3FileService.deleteFile(file);
        }
        lectureService.delete(lectureId);
    }

    //기존 HTML 본문은 유지하고, 지정된 이미지 URL만 새 파일로 교체
    @Transactional
    public void updateEditorImage(Long userId, Long courseId, String oldImageUrl, MultipartFile newImage) throws IOException {
        Teacher teacher = teacherService.getTeacherByUserId(userId);
        Course course = courseService.getCourse(courseId);

        // 권한 검증
        if (!course.getTeacher().getId().equals(teacher.getId())) {
            throw new IllegalArgumentException("본인의 강좌만 수정할 수 있습니다.");
        }
        // 새 이미지 업로드
        File newFile = s3FileService.uploadImage(newImage);

        // HTML 문자열에서 기존 URL을 새 URL로 교체
        String currentHtml = course.getHtmlContent();
        if (currentHtml == null || !currentHtml.contains(oldImageUrl)) {
            throw new IllegalArgumentException("기존 이미지 URL을 찾을 수 없습니다.");
        }

        String updatedHtml = currentHtml.replace(oldImageUrl, newFile.getUrl());
        course.setHtmlContent(updatedHtml);

        // 변경사항 저장
        courseService.registerCourse(course);
    }
}
