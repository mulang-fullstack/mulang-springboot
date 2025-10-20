package yoonsome.mulang.course.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import yoonsome.mulang.course.dto.CourseListRequest;
import yoonsome.mulang.course.dto.CourseListResponse;
import yoonsome.mulang.course.service.CourseService;

@RequiredArgsConstructor
@Controller
public class CourseController {
    private final CourseService courseService;
    /*
    @GetMapping("course")
    public String getCourseList(){
        return "course/courseList";
    }*/
    @GetMapping("course")
    public Page<CourseListResponse> getCourseList(@ModelAttribute CourseListRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        return courseService.getCoursesByLanguageAndCategory(request.getLanguageId(), request.getCategoryId(), pageable);
    }
    @GetMapping("courseDetail")
    public String getCourseDetail(){
        return "course/courseDetail";
    }
    @GetMapping("courseCurriculum")
    public String getCourseCurriculum(){
        return "course/courseCurriculum";
    }
    @GetMapping("courseReview")
    public String getCourseReview(){
        return "course/courseReview";
    }
}
