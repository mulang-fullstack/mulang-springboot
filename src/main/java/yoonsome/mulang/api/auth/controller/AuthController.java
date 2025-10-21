package yoonsome.mulang.api.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import yoonsome.mulang.api.auth.dto.SignupRequest;
import yoonsome.mulang.api.auth.service.AuthService;
import yoonsome.mulang.domain.email.service.EmailCodeService;

@RequestMapping("/auth")
@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final EmailCodeService emailCodeService;

    // 로그인 폼
    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    // 회원가입 폼
    @GetMapping("/signup")
    public String signup() {
        return "auth/signup";
    }

    // 회원가입 요청
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

    /**
     * 이메일 중복 확인 + 인증 메일 발송
     * @param email 이메일 주소
     * @return 중복 시 "duplicate", 발송 성공 시 "sent"
     */
    @PostMapping("/email/send")
    public ResponseEntity<String> sendVerificationEmail(@RequestParam String email) {
        // 1. 이메일 중복 확인
        if (authService.isEmailExists(email)) {
            return ResponseEntity.ok("duplicate");
        }
        // 2. 이메일 인증 코드 발송
        authService.sendSignupVerification(email);

        return ResponseEntity.ok("sent");
    }

    /**
     * 이메일 인증 코드 검증
     * @param email 이메일 주소
     * @param code 사용자가 입력한 인증 코드
     * @return 검증 성공 여부 ("valid" / "invalid" / "expired")
     */
    @PostMapping("/email/verify")
    public ResponseEntity<String> verifyEmailCode(@RequestParam String email, @RequestParam String code) {
        String result = emailCodeService.verifyCode(email, code);
        return ResponseEntity.ok(result);
    }
}
