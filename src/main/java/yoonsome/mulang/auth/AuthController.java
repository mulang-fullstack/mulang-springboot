package yoonsome.mulang.auth;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import yoonsome.mulang.user.entity.User;
import yoonsome.mulang.user.service.UserService;

@RequestMapping("/auth")
@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("login")
    public String login(){
        return "auth/login";
    }
    @GetMapping("signup")
    public String signup(){
        return "auth/signup";
    }

    @PostMapping("login.do")
    public String login(
            @RequestParam String email,
            @RequestParam String password,
            HttpSession session,
            RedirectAttributes redirectAttributes
    ) {
        try {
            User user = userService.loginUser(email, password);
            session.setAttribute("loginUser", user);
            return "redirect:/";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/auth/login";
        }
    }

    @GetMapping("logout.do")
    public String logout(HttpSession session){
        session.removeAttribute("loginUser");
        return "redirect:/";
    }
}
