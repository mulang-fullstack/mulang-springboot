package yoonsome.mulang.teacher.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import yoonsome.mulang.course.entity.Course;


@Controller
@RequiredArgsConstructor
public class CourseUploadController {

    private final CourseService courseService;

    @GetMapping("courseUpload")
    public String courseUpload(){
        return "teacher/courseUpload";
    }
    @PostMapping("courseUpload")
    public String insertCourse(@ModelAttribute Course course) {
        courseService.insertS(course);
        return "redirect:/teacher/classEdit";
    }
}
