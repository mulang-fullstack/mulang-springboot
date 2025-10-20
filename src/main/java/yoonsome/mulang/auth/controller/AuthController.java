package yoonsome.mulang.auth.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import yoonsome.mulang.auth.dto.LoginRequest;
import yoonsome.mulang.auth.dto.SignupRequest;
import yoonsome.mulang.auth.service.AuthService;
import yoonsome.mulang.user.entity.User;
import yoonsome.mulang.user.service.UserService;

@RequestMapping("/auth")
@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/login")
    public String login(){
        return "auth/login";
    }

    @PostMapping("/login")
    public String login(LoginRequest request,
                        HttpSession session,
                        Model model) {
        try {
            User user = authService.login(request);
            session.setAttribute("loginUser", user);
            return "redirect:/";

        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "auth/login";
        }
    }

    @GetMapping("/signup")
    public String signup(){
        return "auth/signup";
    }

    @PostMapping("/signup")
    public String signup(SignupRequest request) {
        authService.signup(request);
        return "redirect:/auth/login";
    }


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
