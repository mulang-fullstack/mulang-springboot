package yoonsome.mulang.api.student.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import yoonsome.mulang.api.student.dto.MypageResponse;
import yoonsome.mulang.api.student.dto.SaveResponse;
import yoonsome.mulang.api.student.service.MypageService;
import yoonsome.mulang.api.student.service.SaveService;
import yoonsome.mulang.infra.security.CustomUserDetails;


@Controller
@RequestMapping("/student")
@RequiredArgsConstructor
public class SaveController {

    private final SaveService saveService;
    private final MypageService mypageService;

    @GetMapping("/save")
    public String getFavorites(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(required = false) Long languageId, @RequestParam(required = false) String keyword) {

        // CustomUserDetails에서 바로 User ID 가져오기
        Long userId = userDetails.getUser().getId();

        String searchKeyword = (keyword != null && !keyword.trim().isEmpty())
                ? keyword.trim()
                : null;


        Pageable pageable = PageRequest.of(page, 5); //한페이지에 5개씩

        Page<SaveResponse> favorites = null;

        if (searchKeyword != null) {
            if (languageId != null) {
                favorites = saveService.findByStudentIdAndLanguageAndKeyword(userId, languageId, searchKeyword, pageable);
            } else {
                favorites = saveService.findByStudentIdAndKeyword(userId, searchKeyword, pageable);
            }
        }else{
            if (languageId != null) {
                favorites = saveService.findByStudentIdAndLanguage(userId, languageId, pageable);
            } else {
                favorites = saveService.findByStudentIdWithCoursePage(userId, pageable);
            }
        }


        MypageResponse user = mypageService.getUserInfo(userId);
        model.addAttribute("user", user);

        model.addAttribute("favorites", favorites.getContent());  // .getContent() 추가!
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", favorites.getTotalPages());
        model.addAttribute("languageId", languageId);
        model.addAttribute("searchKeyword", searchKeyword);

        return "student/like/save";
    }

}
