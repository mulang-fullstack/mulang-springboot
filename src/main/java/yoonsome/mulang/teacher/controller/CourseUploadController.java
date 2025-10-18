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

@Controller
@RequiredArgsConstructor
public class CourseUploadController {

    private final CourseService courseService;

    @GetMapping("classUpload")
    public String courseUpload(){
        return "teacherMypage/classUpload";
    }

    @PostMapping("Edit")
    public String insertCourse(@ModelAttribute Course course) {
        courseService.registerCourse(course);
        return "redirect:classEdit";
    }

    @GetMapping("classEdit")
    public String classEdit() {
        return "teacherMypage/classEdit";
    }

}
