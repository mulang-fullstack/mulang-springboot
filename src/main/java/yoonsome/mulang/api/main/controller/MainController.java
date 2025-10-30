package yoonsome.mulang.api.main.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import yoonsome.mulang.api.course.dto.CourseListResponse;
import yoonsome.mulang.api.main.service.MainService;
import yoonsome.mulang.domain.course.dto.CourseListRequest;
import yoonsome.mulang.domain.language.entity.Language;
import yoonsome.mulang.domain.user.entity.User;
import yoonsome.mulang.infra.security.CustomUserDetails;

import java.util.List;
import java.util.Map;

@RequestMapping("/main")
@RequiredArgsConstructor
@Controller
public class MainController {
    private final MainService mainService;

    //실시간 랭킹
    @GetMapping("/ranking")
    public String getCourseRankingList(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestParam(defaultValue = "0") Long languageId, Model model){
        Long userId = getUserId(userDetails);
        List<Language> languages = mainService.getLanguageList();
        List<CourseListResponse> courseListResponses = mainService.getCourseRanking(userId, languageId);
        model.addAttribute("languages", languages);
        model.addAttribute("courses", courseListResponses);
        return "main/ranking";
    }
    //신규 클래스
    @GetMapping("/newCourse")
    public String getNewCourseList(@AuthenticationPrincipal CustomUserDetails userDetails, Model model, Pageable pageable){
        Long userId = getUserId(userDetails);
        List<CourseListResponse> courseListResponses = mainService.getNewCourseList(userId);
        model.addAttribute("courses", courseListResponses);
        return "main/newCourse";
    }
    //일상회화//카테고리 아이디: 영어(4), 중국어(8), 일본어(12)
    @GetMapping("/dailyConversation")
    public String getDailyConversationList(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @ModelAttribute CourseListRequest request,
            Model model){
        Long userId = getUserId(userDetails);
        List<Language> languages = mainService.getLanguageList();
        Page<CourseListResponse> courseListResponses = mainService.getCourseConversationPage(userId, request);
        model.addAttribute("languages", languages);
        model.addAttribute("courses", courseListResponses.getContent());
        model.addAttribute("totalPages", courseListResponses.getTotalPages());
        return "main/dailyConversation";
    }
    private Long getUserId(@AuthenticationPrincipal CustomUserDetails userDetails){
        //세션에서 userId 받아오기
        Long userId = null;
        if(userDetails != null){
            User sessionUser = userDetails.getUser(); // Security 세션에 저장된 사용자 엔티티
            userId = sessionUser.getId(); // 로그인한 사용자 ID 접근
        }
        return userId;
    }
}
