package yoonsome.mulang.api.student.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import yoonsome.mulang.infra.security.CustomUserDetails;
import yoonsome.mulang.api.student.dto.MypageResponse;
import yoonsome.mulang.api.student.service.MypageService;

@RequestMapping("/student")
@Controller
public class ModifyController {

    private final MypageService mypageService;

    public ModifyController(MypageService mypageService) {
        this.mypageService = mypageService;
    }

    @GetMapping("edit")
    public String edit(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long id = userDetails.getUser().getId();
        MypageResponse user = mypageService.getUserInfo(id);
        model.addAttribute("user", user);

        return "student/profile/edit";
    }
    /*@PostMapping("edit")
    public  String edit(Model model,
                        @RequestParam String email,
                        @RequestParam String phone,
                        @RequestParam String password,
                        @RequestParam String nickname) {
        if(!email.contains("@") || !email.contains(".")) {
            User user = em.find(User.class, 2L);
            model.addAttribute("user", user);
            model.addAttribute("emailerror", "이메일 형식이 올바르지 않습니다.");
            return "/mypage/profile/edit";
        }
        if(password.length()<7 && password.length() == 0 || password.length()>16){
            if(password.length() == 0){

            }
            User user = em.find(User.class, 2L);
            model.addAttribute("user", user);
            model.addAttribute("passworderror", "비밀번호는 8자에서 15자 사이를 입력하세요");
            return "/mypage/profile/edit";
        }
        if(phone.length()!=11 && phone.contains("010")){
            User user = em.find(User.class, 2L);
            model.addAttribute("user", user);
            model.addAttribute("phoneerror", "알맞은 정화번호를 입력하세요(전화번호의 형식은 010-****-**** 입니다)");
            return "/mypage/profile/edit";
        }

        modifyService.updateUser(nickname, email,  phone, password);

        return "redirect:/mypage/personal";
    }*/
}
