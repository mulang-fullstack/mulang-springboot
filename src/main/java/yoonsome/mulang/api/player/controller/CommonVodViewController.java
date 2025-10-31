package yoonsome.mulang.api.player.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import yoonsome.mulang.api.player.service.PlayerViewService;
import yoonsome.mulang.infra.security.CustomUserDetails;

@Controller
@RequiredArgsConstructor
public class CommonVodViewController {

    private final PlayerViewService playerViewService;

    @GetMapping("/player/{courseId}")
    public String showVodPage(@PathVariable Long courseId,
                              @RequestParam(required = false) Long lectureId,
                              @AuthenticationPrincipal CustomUserDetails userDetails,
                              @RequestHeader(value = "Referer", required = false) String referer,
                              Model model) {
        // requestHeader 활용해서 뒤로가기를 history back 말고 진짜 뒤로가기를 구현
        //  사용자 정보 확인
        var user = userDetails != null ? userDetails.getUser() : null;

        //  플레이어 화면 데이터 구성
        var data = playerViewService.getPlayerView(courseId, lectureId, user);
        model.addAllAttributes(data);

        // 뒤로가기 경로 설정
        if (referer != null && referer.contains("mypage/classes")) {
            model.addAttribute("backUrl", referer); // 올바른 내부 referer
        } else {
            model.addAttribute("backUrl", "/teacher/mypage/classes/edit"); // fallback
        }
        return "common/player";
    }

}
