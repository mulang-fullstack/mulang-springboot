package yoonsome.mulang.api.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import yoonsome.mulang.api.auth.dto.SignupRequest;
import yoonsome.mulang.api.auth.service.AuthService;
import yoonsome.mulang.domain.email.service.EmailCodeService;
import yoonsome.mulang.domain.user.service.UserService;
import yoonsome.mulang.global.util.UserValidator;
import yoonsome.mulang.global.util.UserValidator.ValidationResult;

@RequestMapping("/auth")
@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final EmailCodeService emailCodeService;
    private final UserService userService;

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
    @ResponseBody
    public String signup(@ModelAttribute SignupRequest request) {

        // 1. 서버측 유효성 검증
        ValidationResult validationResult = UserValidator.validateSignupData(
                request.getUsername(),
                request.getNickname(),
                request.getEmail(),
                request.getPassword(),
                request.getPasswordConfirm(),
                request.getAccountType()
        );

        if (!validationResult.isValid()) {
            return validationResult.getMessage();
        }

        // 2. 닉네임 중복 확인
        if (userService.existsByNickname(request.getNickname())) {
            return "error";
        }

        // 3. 이메일 중복 확인
        if (userService.existsByEmail(request.getEmail())) {
            return "error";
        }

        // 4. 이메일 인증 완료 여부 확인
        if (!emailCodeService.isVerified(request.getEmail())) {
            return "error";
        }

        // 5. 회원가입 처리
        boolean success = authService.signup(request);
        if (success) {
            // 인증 완료 상태 제거 (선택사항)
            emailCodeService.removeVerified(request.getEmail());
            return "success";
        } else {
            return "error";
        }
    }

    /**
     * 닉네임 중복 확인
     * @param nickname 닉네임
     * @return 중복 여부 ("duplicate" / "available" / "invalid")
     */
    @PostMapping("/nickname/check")
    @ResponseBody
    public String checkNickname(@RequestParam String nickname) {

        // 1. 유효성 검증
        ValidationResult result = UserValidator.validateNickname(nickname);
        if (!result.isValid()) {
            return "invalid";
        }

        // 2. 중복 확인
        if (userService.existsByNickname(nickname)) {
            return "duplicate";
        }

        return "available";
    }

    /**
     * 이메일 중복 확인 + 인증 메일 발송
     * @param email 이메일 주소
     * @return 중복 시 "duplicate", 발송 성공 시 "sent", 유효하지 않으면 "invalid"
     */
    @PostMapping("/email/send")
    @ResponseBody
    public String sendVerificationEmail(@RequestParam String email) {

        // 1. 이메일 유효성 검증
        ValidationResult result = UserValidator.validateEmail(email);
        if (!result.isValid()) {
            return "invalid";
        }

        // 2. 이메일 중복 확인
        if (userService.existsByEmail(email)) {
            return "duplicate";
        }

        // 3. 이메일 인증 코드 발송
        authService.sendSignupVerification(email);

        return "sent";
    }

    /**
     * 이메일 인증 코드 검증
     * @param email 이메일 주소
     * @param code 사용자가 입력한 인증 코드
     * @return 검증 성공 여부 ("valid" / "invalid" / "expired")
     */
    @PostMapping("/email/verify")
    @ResponseBody
    public String verifyEmailCode(@RequestParam String email, @RequestParam String code) {
        String result = emailCodeService.verifyCode(email, code);
        return result;
    }
}