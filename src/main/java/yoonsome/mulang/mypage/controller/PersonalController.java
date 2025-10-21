package yoonsome.mulang.mypage.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import yoonsome.mulang.mypage.DTO.MypageResponse;
import yoonsome.mulang.mypage.service.MypageService;
import yoonsome.mulang.user.entity.User;
import yoonsome.mulang.util.BCryptEncoder;


@RequestMapping("mypage")
@Controller
@RequiredArgsConstructor
public class PersonalController {

    private final MypageService mypageService;
    private final BCryptEncoder bCryptEncoder;

    @GetMapping("personal")
    public String personal(HttpSession session, Model model) {
        User loginUser = (User) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/auth/login";
        }


        Long id = loginUser.getId();
        MypageResponse user = mypageService.getUserInfo(id);
        model.addAttribute("user", user);
        return "mypage/profile/personal";
    }

    @PostMapping("check-password")
    public String passwordCheck(HttpSession session,
                                @RequestParam String password, RedirectAttributes redirectAttributes) {

        User loginUser = (User) session.getAttribute("loginUser"); //세션 저장됨

        if (loginUser == null) {
            return "redirect:/auth/login";
        }

        String realpassword = loginUser.getPassword();

        if (!bCryptEncoder.matches(password, realpassword)) {
            redirectAttributes.addFlashAttribute("passwordError", "비밀번호가 일치하지 않습니다.");
            return "redirect:/mypage/personal";
        }
        return "redirect:/mypage/edit";
    }
}
