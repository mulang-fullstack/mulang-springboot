package yoonsome.mulang;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import yoonsome.mulang.api.course.dto.CourseListResponse;
import yoonsome.mulang.api.main.service.MainService;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private final MainService mainService;


    @GetMapping("/")
    public String getBestCourseList(Model model){
        //BEST 인기 클래스
        List<CourseListResponse> bestResponse = mainService.getBestCourseList();
        //방금 결제됐어요!
        List<CourseListResponse> recentResponse = mainService.getCourseRecentPaidPage();
        //실시간 랭킹
        List<CourseListResponse> rankResponse = mainService.getCourseRanking(null, 0L);
        //따끈따끈한 신규 클래스:4개
        List<CourseListResponse> newResponse = mainService.getNewCourseList(null, PageRequest.of(0, 4));
        model.addAttribute("best", bestResponse);
        model.addAttribute("recent", recentResponse);
        model.addAttribute("rank", rankResponse);
        model.addAttribute("newCourse", newResponse);
        model.addAttribute("courses", bestResponse);
        return "index";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }
    @GetMapping("tests")
    public String test() {
        return "auth/login";
    }
}
