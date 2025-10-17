package yoonsome.mulang.course.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CourseController {
    @GetMapping("course")
    public String getCourseList(){
        return "course/courseList";
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
