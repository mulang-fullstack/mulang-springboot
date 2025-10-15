package yoonsome.mulang.lecture.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LectureController {
    @GetMapping("lecture.do")
    public String getLectureList(){
        return "lecture/lectureList";
    }
    @GetMapping("lectureDetail.do")
    public String getLectureDetail(){
        return "lecture/lectureDetail";
    }
    @GetMapping("lectureCurriculum.do")
    public String getLectureCurriculum(){
        return "lecture/lectureCurriculum";
    }
    @GetMapping("lectureReview.do")
    public String getLectureReview(){
        return "lecture/lectureReview";
    }
}
