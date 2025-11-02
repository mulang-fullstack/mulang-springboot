package yoonsome.mulang.api.student.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import yoonsome.mulang.api.auth.service.AuthService;
import yoonsome.mulang.domain.email.service.EmailCodeService;
import yoonsome.mulang.domain.user.entity.User;
import yoonsome.mulang.domain.user.service.UserService;
import yoonsome.mulang.infra.security.CustomUserDetails;
import yoonsome.mulang.api.student.dto.MypageResponse;
import yoonsome.mulang.api.student.service.MypageService;

@RequestMapping("/student")
@Controller
@RequiredArgsConstructor
public class StuInfoModifyController {

    private final MypageService mypageService;
    private final EmailCodeService emailCodeService;
    private final AuthService authService;
    private final UserService userService;

    @GetMapping("passwordchange")
    public String passwordchange(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        Long userid = userDetails.getUser().getId();
        MypageResponse user = mypageService.getUserInfo(userid);
        model.addAttribute("user", user);
        return "student/profile/passwordchange";
    }

    @PostMapping("passwordchange")
    public String passwordchage(@AuthenticationPrincipal CustomUserDetails userDetails
            , Model model,
                                @RequestParam String newpassword,
                                @RequestParam String confirmpassword,
                                RedirectAttributes redirectAttributes) {


        Long userId = userDetails.getUser().getId();

        if (newpassword.length() < 5 && newpassword.length() != 0) {
            model.addAttribute("passworderror", "비밀번호는 6자 이상입력하세요");
            return "student/profile/passwordchange";

        }
        if (!newpassword.equals(confirmpassword)) {
            model.addAttribute("passworderror", "서로 비밀번호가 달라요");
            return "student/profile/passwordchange";
        }

        mypageService.updatepassword(userId, newpassword);

        redirectAttributes.addFlashAttribute("message", "비밀번호가 수정되었습니다.");
        return "redirect:/student/personal";
    }


    @GetMapping("edit")  // 편집창에 정보 가져와서 보여주기
    public String edit(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long userid = userDetails.getUser().getId();
        MypageResponse user = mypageService.getUserInfo(userid);
        model.addAttribute("user", user);

        return "student/profile/edit";
    }

    @PostMapping("editemail")
    public String emailedit(@RequestParam String email,
                            Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long userid = userDetails.getUser().getId();
        MypageResponse user = mypageService.getUserInfo(userid);

        // 1. 이메일 형식 검증
        if (!email.contains("@") || !email.contains(".")) {
            model.addAttribute("user", user);
            model.addAttribute("emailerror", "이메일 형식이 올바르지 않습니다.");
            return "student/profile/edit";
        }


      boolean emailexist = userService.existsByEmail(email);

        if(!email.equals(user.getEmail())) {
            if (emailexist) {
                model.addAttribute("emailerror", "가입된 이메일이 있습니다");
                model.addAttribute("user", user);
                return "student/profile/edit";
            }
        }

        try {
            authService.sendSignupVerification(email);
            model.addAttribute("message", "인증코드가 전송되었습니다.");
            model.addAttribute("showCodeInput", true);
            user.setEmail(email);
            model.addAttribute("user", user);
        } catch (Exception e) {
            // 메일 전송 실패해도 에러 안내고 계속 진행
            System.out.println("메일 전송 실패: " + e.getMessage());
            model.addAttribute("message", "인증코드가 전송되었습니다. (개발 모드)");
            model.addAttribute("showCodeInput", true);
            user.setEmail(email);
            model.addAttribute("user", user);

            // 개발용: 콘솔에 인증코드 출력
            // 실제 코드는 emailCodeService에서 확인 가능
        }

        return "student/profile/edit";
    }

    @PostMapping("verifycode")
    public String verifycode(@RequestParam String email,
                             @RequestParam String emailCode,
                             Model model,
                             @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long userid = userDetails.getUser().getId();
        MypageResponse user = mypageService.getUserInfo(userid);
        user.setEmail(email);  // 입력받은 이메일로 덮어쓰기
        model.addAttribute("user", user);
        model.addAttribute("showCodeInput", true);
        model.addAttribute("changedEmail", email);  // 추가!

        try {
            String result = emailCodeService.verifyCode(email, emailCode);

            if ("valid".equals(result)) {
                model.addAttribute("message", "이메일 인증이 완료되었습니다.");
                model.addAttribute("emailVerified", true);
                model.addAttribute("verifiedEmail", email);
            } else if ("expired".equals(result)) {
                model.addAttribute("emailerror", "인증코드가 만료되었습니다.");
            } else if ("invalid".equals(result)) {
                model.addAttribute("emailerror", "인증코드가 일치하지 않습니다.");
            }
        } catch (Exception e) {
            model.addAttribute("emailerror", "인증 확인 중 오류가 발생했습니다.");
        }

        return "student/profile/edit";
    }


    @PostMapping("edit")
    public String edit(Model model,
                       @AuthenticationPrincipal CustomUserDetails userDetails,
                       @RequestParam String email,
                       @RequestParam(required = false) String nickname,
                       @RequestParam(required = false) Boolean emailVerified,
                       @RequestParam(required = false) String verifiedEmail,
                       @RequestPart(required = false) MultipartFile photo,
                       RedirectAttributes redirectAttributes) {

        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+photo.getOriginalFilename());

        Long userId = userDetails.getUser().getId();
        MypageResponse user = mypageService.getUserInfo(userId);


        if (nickname != null && !nickname.trim().isEmpty()) {
            // 현재 닉네임과 다를 때만 중복 확인
            if (!nickname.equals(user.getNickname())) {
                if (userService.existsByNickname(nickname)) {
                    model.addAttribute("nicknameError", "이미 사용 중인 닉네임입니다.");
                    model.addAttribute("user", user);
                    return "student/profile/edit";
                }
                if(nickname.length()<2) {
                    model.addAttribute("nicknameError", "닉네임은 2자 이상이어야합니다");
                    model.addAttribute("user", user);
                    return "student/profile/edit";
                }
                if(nickname.length()>8){
                    model.addAttribute("nicknameError", "닉네임은 8자 이하이어야합니다");
                    model.addAttribute("user", user);
                    return "student/profile/edit";
                }
            }
        }

        // 2. 이메일이 변경되었는지 확인
        if (!email.equals(user.getEmail())) {
            // 이메일 변경됨 → 인증 필수
            if (emailVerified == null || !emailVerified || !email.equals(verifiedEmail)) {
                model.addAttribute("user", user);
                model.addAttribute("emailerror", "이메일 인증을 완료해주세요.");
                return "student/profile/edit";
            }

        }

        // 3. 프로필 수정 진행
        try {
            mypageService.updateUserInfo(userId, email, nickname, photo);
            redirectAttributes.addFlashAttribute("message", "프로필이 수정되었습니다.");
            return "redirect:/student/personal";

        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", "파일 업로드 중 오류가 발생했습니다.");
            model.addAttribute("user", user);
            return "student/profile/edit";
        }
    }
}