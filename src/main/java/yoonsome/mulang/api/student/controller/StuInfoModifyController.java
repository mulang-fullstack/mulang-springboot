package yoonsome.mulang.api.student.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import yoonsome.mulang.infra.security.CustomUserDetails;
import yoonsome.mulang.api.student.dto.MypageResponse;
import yoonsome.mulang.api.student.service.MypageService;

@RequestMapping("/student")
@Controller
public class StuInfoModifyController {

    private final MypageService mypageService;

    public StuInfoModifyController(MypageService mypageService) {
        this.mypageService = mypageService;
    }

    @GetMapping("passwordchange")
    public String passwordchange(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        Long userid =  userDetails.getUser().getId();
        MypageResponse user = mypageService.getUserInfo(userid);
        model.addAttribute("user", user);
        return "student/profile/passwordchange";
    }
    @PostMapping("passwordchange")
    public String passwordchage(@AuthenticationPrincipal CustomUserDetails userDetails
                                , Model model,
                                @RequestParam String newpassword,
                                @RequestParam String confirmpassword,
                                RedirectAttributes redirectAttributes){


        Long userId = userDetails.getUser().getId();

        if(newpassword.length()<5 && newpassword.length() != 0){
            model.addAttribute("passworderror", "비밀번호는 6자 이상입력하세요");
            return "student/profile/passwordchange";

        }if(!newpassword.equals(confirmpassword)){
            model.addAttribute("passworderror", "서로 비밀번호가 달라요");
            return "student/profile/passwordchange";
        }

        mypageService.updatepassword(userId,newpassword);

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
    @PostMapping("edit")
    public String edit(Model model,
                       @AuthenticationPrincipal CustomUserDetails userDetails,
                       @RequestParam String email,
                       @RequestParam String nickname,
                       @RequestPart(required = false) MultipartFile photo,
                       RedirectAttributes redirectAttributes) {

        Long userId = userDetails.getUser().getId();

        if(!email.contains("@") || !email.contains(".")) {
            MypageResponse user = mypageService.getUserInfo(userId);
            model.addAttribute("user", user);
            model.addAttribute("emailerror", "이메일 형식이 올바르지 않습니다.");
            return "student/profile/edit";
        }

        try {
            mypageService.updateUserInfo(userId, email, nickname, photo);
            redirectAttributes.addFlashAttribute("message", "프로필이 수정되었습니다.");
            return "redirect:/student/personal";
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", "파일 업로드 중 오류가 발생했습니다.");
            MypageResponse user = mypageService.getUserInfo(userId);
            model.addAttribute("user", user);
            return "student/profile/edit";
        }
    }


}
