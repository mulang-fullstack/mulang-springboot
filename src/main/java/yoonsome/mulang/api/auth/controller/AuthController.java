package yoonsome.mulang.api.auth.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import yoonsome.mulang.api.auth.dto.SignupRequest;
import yoonsome.mulang.api.auth.service.AuthService;

@RequestMapping("/auth")
@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * 로그인 요청
     */
    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    /**
     * 회원가입 요청
     */
    @GetMapping("/signup")
    public String signup() {
        return "auth/signup";
    }

    @PostMapping("/signup")
    public String signup(SignupRequest request, Model model) {
        boolean success = authService.signup(request);
        if (success) {
            model.addAttribute("signupStatus", "success");
            return "redirect:/auth/login";
        } else {
            model.addAttribute("signupStatus", "error");
            return "redirect:/auth/signup";
        }
    }

    //이메일 중복 확인
    @GetMapping("/checkEmail")
    public ResponseEntity<String> checkEmail(@RequestParam String email) {
        boolean exists = authService.isEmailExists(email);
        return ResponseEntity.ok(exists ? "error" : "success");
    }

    /**
     * 로그아웃
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
