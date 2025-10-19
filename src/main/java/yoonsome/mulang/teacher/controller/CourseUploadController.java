package yoonsome.mulang.teacher.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import yoonsome.mulang.course.entity.Course;
import yoonsome.mulang.course.service.CourseService;
import java.io.IOException;
import java.util.List;

/**
 * <p>교사 마이페이지 - 강좌 업로드 및 수정 컨트롤러</p>
 * <p>강좌 등록, 챕터(VOD) 업로드, 강좌 수정 등 담당</p>
 * @author 양진석
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/teacher/mypage/classes")
public class CourseUploadController {

    private final CourseService courseService;

    /**
     * [GET] 새 강좌 업로드 폼 페이지
     * @return teacherMypage/classUpload.jsp
     */
    @GetMapping("/new")
    public String showCourseUploadForm() {
        return "teacherMypage/classUpload";
    }

    /**
     * [POST] 새 강좌 등록 처리
     */
    @PostMapping
    public String createCourse(
            @ModelAttribute Course course,
            @RequestParam(value = "lectureTitle", required = false) List<String> lectureTitles,
            @RequestParam(value = "lectureVideo", required = false) List<MultipartFile> lectureVideos
    ) throws IOException {
        courseService.createCourseWithLectures(course, lectureTitles, lectureVideos);
        return "redirect:/teacher/mypage/classEdit";
    }

    @DeleteMapping("/{courseId}")
    @ResponseBody
    public ResponseEntity<?> deleteCourse(@PathVariable Long courseId) {
        courseService.deleteCourse(courseId);
        return ResponseEntity.ok().build();
    }
}
