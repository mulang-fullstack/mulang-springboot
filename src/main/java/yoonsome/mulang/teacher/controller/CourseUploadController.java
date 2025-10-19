package yoonsome.mulang.teacher.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import yoonsome.mulang.course.entity.Course;
import yoonsome.mulang.course.service.CourseService;
import java.io.IOException;
import java.util.List;

/**
 * <p>강좌 업로드 및 편집을 처리하는 교사 마이페이지 컨트롤러</p>
 * <p>강좌 등록, 챕터(VOD) 업로드, 강좌 수정 등 기능을 담당</p>
 * @author 양진석
 */
@Controller
@RequiredArgsConstructor
public class CourseUploadController {

    private final CourseService courseService;

    /**
     * 클래스 업로드 폼 페이지로 이동한다.
     * @return teacherMypage/classUpload.jsp 뷰
     */
    @GetMapping("classUpload")
    public String getClassUploadForm() {
        return "teacherMypage/classUpload";
    }

    @PostMapping("classEdit")
    public String createCourse(
            @ModelAttribute Course course,
            @RequestParam(value = "lectureTitle", required = false) List<String> lectureTitles,
            @RequestParam(value = "lectureVideo", required = false) List<MultipartFile> lectureVideos
    ) throws IOException {
        courseService.createCourseWithLectures(course, lectureTitles, lectureVideos);
        return "redirect:classEdit";
    }
    /**
     * 클래스 수정 페이지로 이동한다.
     * @return teacherMypage/classEdit.jsp 뷰
     */
    @GetMapping("classEdit")
    public String getClassEditForm() {
        return "teacherMypage/classEdit";
    }
}
