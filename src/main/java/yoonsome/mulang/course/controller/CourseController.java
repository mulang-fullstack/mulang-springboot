package yoonsome.mulang.course.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CourseController {
    @GetMapping("course")
    public String getLectureList(){
        return "course/courseList";
    }
    @GetMapping("courseDetail")
    public String getLectureDetail(){
        return "course/courseDetail";
    }
    @GetMapping("courseCurriculum")
    public String getLectureCurriculum(){
        return "course/courseCurriculum";
    }
    @GetMapping("courseReview")
    public String getLectureReview(){
        return "course/courseReview";
    }
}
