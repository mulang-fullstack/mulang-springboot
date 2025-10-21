package yoonsome.mulang.api.student.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import yoonsome.mulang.infra.security.CustomUserDetails;
import yoonsome.mulang.api.student.dto.MypageResponse;
import yoonsome.mulang.api.student.service.MypageService;
import yoonsome.mulang.domain.user.entity.User;
import yoonsome.mulang.domain.user.service.UserService;


@RequestMapping("/student")
@Controller
@RequiredArgsConstructor
public class PersonalController {

    private final MypageService mypageService;
    private final UserService userService;

    @GetMapping("personal")
    public String personal(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {

        Long id = userDetails.getUser().getId();
        MypageResponse user = mypageService.getUserInfo(id);
        model.addAttribute("user", user);
        return "student/profile/personal";
    }

    @PostMapping("check-password") // serssion에는 비밀번호에 관한정보는 담겨있지 않기 때문에 findbyid로 다시 조회해야함
    public String passwordCheck(@AuthenticationPrincipal CustomUserDetails userDetails,
                                @RequestParam String password,
                                RedirectAttributes redirectAttributes) {

        Long id = userDetails.getUser().getId();
        MypageResponse user = mypageService.getUserInfo(id);

        User forPassword = userService.findById(user.getId());

        if (!mypageService.verifyPassword(password, forPassword.getPassword())) {
            redirectAttributes.addFlashAttribute("passwordError", "비밀번호가 일치하지 않습니다.");
            return "redirect:/student/personal";
        }

        return "redirect:/student/edit";
    }
}
