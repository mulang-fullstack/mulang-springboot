package yoonsome.mulang.mypage.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import yoonsome.mulang.mypage.repository.ModifyRepository;
import yoonsome.mulang.mypage.service.ModifyService;
import yoonsome.mulang.user.entity.User;

@RequestMapping("mypage")
@Controller
public class ModifyController {
    private final ModifyRepository modifyRepository;
    private final ModifyService modifyService;
    @PersistenceContext
    private EntityManager em;

    public ModifyController(ModifyRepository modifyRepository, ModifyService modifyService) {
        this.modifyRepository = modifyRepository;
        this.modifyService = modifyService;
    }

    @GetMapping("edit")
    public String edit(Model model) {

        User user = em.find(User.class, 2L);
        model.addAttribute("user", user);
        return "/mypage/profile/edit";
    }
    @PostMapping("edit")
    public  String edit(Model model,
                        @RequestParam String email,
                        @RequestParam String phone,
                        @RequestParam String password,
                        @RequestParam String nickname) {
        if(!email.contains("@")) {
            User user = em.find(User.class, 2L);
            model.addAttribute("user", user);
            model.addAttribute("emailerror", "이메일 형식이 올바르지 않습니다.");
            return "/mypage/profile/edit";
        }
        if(password.length()<7 || password.length()>16){
            User user = em.find(User.class, 2L);
            model.addAttribute("user", user);
            model.addAttribute("passworderror", "비밀번호는 8자에서 15자 사이를 입력하세요");
            return "/mypage/profile/edit";
        }

        modifyService.updateUser(nickname, email,  phone, password);

        return "redirect:/mypage/personal";
    }
}
