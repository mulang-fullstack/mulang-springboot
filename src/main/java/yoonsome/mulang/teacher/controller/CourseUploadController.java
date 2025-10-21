package yoonsome.mulang.teacher.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import yoonsome.mulang.course.entity.Course;
import yoonsome.mulang.course.service.CourseService;
import yoonsome.mulang.global.file.service.FileService;
import java.io.IOException;
import java.util.List;

/**
 * ============================================================
 * [CourseUploadController]
 * ------------------------------------------------------------
 * ■ 담당 기능
 *  - 교사 마이페이지 내 강좌 관련 기능 관리
 *  - 강좌 등록, 강좌 수정, VOD 업로드, 강좌 삭제 등
 *
 * ■ 접근 제한
 *  - /teacher/mypage/** 경로 전체는 TeacherAuthInterceptor에 의해
 *    로그인 및 교사 권한(TEACHER) 검증이 이미 수행됨.
 *
 * ■ 주요 매핑
 *  - GET  /teacher/mypage/classes/new    → 강좌 등록 페이지 이동
 *  - POST /teacher/mypage/classes        → 새 강좌 등록
 *  - DELETE /teacher/mypage/classes/{id} → 강좌 삭제
 * ============================================================
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/teacher/mypage/classes")
public class CourseUploadController {

    private final CourseService courseService;
    private final FileService fileService;

    /** [GET] 새 강좌 업로드 폼 페이지 */
    @GetMapping("/new")
    public String showCourseUploadForm() {
        return "teacherMypage/classUpload";
    }

    /** [POST] 새 강좌 등록 처리 */
    @PostMapping
    public String createCourse(
            @ModelAttribute Course course,
            @RequestParam(value = "lectureTitle", required = false) List<String> lectureTitles,
            @RequestParam(value = "lectureVideo", required = false) List<MultipartFile> lectureVideos,
            @RequestParam(value = "thumbnailFile", required = false) MultipartFile thumbnailFile
    ) throws IOException {
        // 서비스에서 파일 업로드 및 DB 저장 로직 수행
        courseService.createCourseWithLectures(course, lectureTitles, lectureVideos, thumbnailFile);
        // 등록 후 강좌 관리 페이지로 리다이렉트
        return "redirect:/teacher/mypage/classes/edit";
    }

    /** [DELETE] 강좌 삭제 처리 */
    @DeleteMapping("/{courseId}")
    @ResponseBody
    public ResponseEntity<Void> deleteCourse(@PathVariable Long courseId) {
        courseService.deleteCourse(courseId);
        return ResponseEntity.ok().build();
    }
}
