package yoonsome.mulang;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LectureController {
    @GetMapping("lecture.do")
    public String getLectureList(){
        return "board/lectureList";
    }
    @GetMapping("lectureDetail.do")
    public String getLectureDetail(){
        return "board/lectureDetail";
    }
}
