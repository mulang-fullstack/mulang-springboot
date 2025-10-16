package yoonsome.mulang.lecture.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LectureController {
    @GetMapping("lecture")
    public String getLectureList(){
        return "lecture/lectureList";
    }
    @GetMapping("lectureDetail")
    public String getLectureDetail(){
        return "lecture/lectureDetail";
    }
    @GetMapping("lectureCurriculum")
    public String getLectureCurriculum(){
        return "lecture/lectureCurriculum";
    }
    @GetMapping("lectureReview")
    public String getLectureReview(){
        return "lecture/lectureReview";
    }
}
