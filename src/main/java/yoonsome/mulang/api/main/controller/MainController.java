package yoonsome.mulang.api.main.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import yoonsome.mulang.api.course.dto.CourseListResponse;
import yoonsome.mulang.api.main.service.MainService;
import yoonsome.mulang.domain.language.entity.Language;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class MainController {
    private final MainService mainService;

    //실시간 랭킹
    @GetMapping("ranking")
    public String getCourseRankingList(Long languageId, Model model){
        List<Language> languages = mainService.getLanguageList();
        List<CourseListResponse> courseListResponses = mainService.getCourseRankingPage(languageId);
        model.addAttribute("languages", languages);
        model.addAttribute("courses", courseListResponses);
        return "main/ranking";
    }
    //신규 클래스
    @GetMapping("newCourse")
    public String getNewCourseList(Model model){

        return "main/newCourse";
    }
    //일상회화
    @GetMapping("dailyConversation")
    public String getDailyConversationList(Model model){

        return "main/dailyConversation";
    }
}
