package yoonsome.mulang.student.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import yoonsome.mulang.global.util.BCryptEncoder;
import yoonsome.mulang.student.DTO.MypageResponse;
import yoonsome.mulang.student.service.MypageService;
import yoonsome.mulang.user.entity.User;
import yoonsome.mulang.user.service.UserService;


@RequestMapping("/student")
@Controller
@RequiredArgsConstructor
public class PersonalController {

    private final MypageService mypageService;
    private final BCryptEncoder bCryptEncoder;
    private final UserService userService;

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

    @PostMapping("check-password") // serssion에는 비밀번호에 관한정보는 담겨있지 않기 때문에 findbyid로 다시 조회해야함
    public String passwordCheck(HttpSession session,
                                @RequestParam String password,
                                RedirectAttributes redirectAttributes) {

        User loginUser = (User) session.getAttribute("loginUser");

        User forPassword = userService.findById(loginUser.getId());

        if (loginUser == null) {
            return "redirect:/auth/login";
        }

        if (!mypageService.verifyPassword(password, forPassword.getPassword())) {
            redirectAttributes.addFlashAttribute("passwordError", "비밀번호가 일치하지 않습니다.");
            return "redirect:/mypage/personal";
        }

        return "redirect:/mypage/edit";
    }
}
