package yoonsome.mulang.teacher.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import yoonsome.mulang.course.entity.Course;
import yoonsome.mulang.course.service.CourseService;

@Controller
@RequiredArgsConstructor
public class CourseUploadController {

    private final CourseService courseService;

    @GetMapping("/teacherClass/courseUpload")
    public String courseUpload(){
        return "courseUpload";
    }

    @PostMapping("/teacherClass/courseUpload")
    public String insertCourse(@ModelAttribute Course course) {
        courseService.registerCourse(course);
        return "redirect:/teacherMypage/classEdit";
    }

    @GetMapping("/teacherMypage/classEdit")
    public String classEdit() {
        return "teacherMypage/classEdit";
    }

}
