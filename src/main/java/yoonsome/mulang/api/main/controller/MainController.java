package yoonsome.mulang.api.main.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import yoonsome.mulang.api.course.dto.CourseListResponse;
import yoonsome.mulang.api.main.service.MainService;
import yoonsome.mulang.domain.language.entity.Language;
import yoonsome.mulang.domain.user.entity.User;
import yoonsome.mulang.infra.security.CustomUserDetails;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class MainController {
    private final MainService mainService;

    //메인페이지 주간 BEST 인기 클래스
    @GetMapping("/")
    public String getBestCourseList(Model model){
        List<CourseListResponse> courseListResponses = mainService.getBestCourseList();
        System.out.println("@main courseListResponses:"+courseListResponses);
        model.addAttribute("courses", courseListResponses);
        return "index";
    }
    //실시간 랭킹
    @GetMapping("ranking")
    public String getCourseRankingList(@AuthenticationPrincipal CustomUserDetails userDetails, Long languageId, Model model){
        //세션에서 userId 받아오기
        Long userId = null;
        if(userDetails != null){
            User sessionUser = userDetails.getUser(); // Security 세션에 저장된 사용자 엔티티
            userId = sessionUser.getId(); // 로그인한 사용자 ID 접근
        }
        List<Language> languages = mainService.getLanguageList();
        List<CourseListResponse> courseListResponses = mainService.getCourseRanking(userId, languageId);
        model.addAttribute("languages", languages);
        model.addAttribute("courses", courseListResponses);
        return "main/ranking";
    }
    //신규 클래스
    @GetMapping("newCourse")
    public String getNewCourseList(@AuthenticationPrincipal CustomUserDetails userDetails, Long languageId, Model model){
        //세션에서 userId 받아오기
        Long userId = null;
        if(userDetails != null){
            User sessionUser = userDetails.getUser(); // Security 세션에 저장된 사용자 엔티티
            userId = sessionUser.getId(); // 로그인한 사용자 ID 접근
        }
        List<CourseListResponse> courseListResponses = mainService.getNewCourseList(userId);
        model.addAttribute("courses", courseListResponses);
        return "main/newCourse";
    }
    //일상회화
    @GetMapping("dailyConversation")
    public String getDailyConversationList(Model model){

        return "main/dailyConversation";
    }
}
