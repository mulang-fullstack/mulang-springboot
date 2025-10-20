package yoonsome.mulang.mypage.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import yoonsome.mulang.user.entity.User;

@RequestMapping("mypage")
@Controller
public class PersonalController {
    @PersistenceContext
    private EntityManager em;

    @GetMapping("personal")
    public String personal(Model model) {

        User user = em.find(User.class, 2L);
        model.addAttribute("user", user);
        return "mypage/profile/personal";
    }
    @PostMapping("check-password")
    public String passwordCheck(Model model,
                                @RequestParam String password, RedirectAttributes redirectAttributes){
       User user = em.find(User.class, 2L);
       String realpassword = user.getPassword();
       if(realpassword.equals(password)){
           model.addAttribute("user", user);
           return "mypage/profile/edit";
       }
       else{
           model.addAttribute("user", user);
           redirectAttributes.addFlashAttribute("passwordError", "비밀번호가 올바르지 않습니다.");
           return "redirect:/mypage/personal"; // 다시 프로필 페이지로
       }
    }
}
