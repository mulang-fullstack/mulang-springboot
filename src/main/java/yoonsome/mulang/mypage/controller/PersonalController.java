package yoonsome.mulang.mypage.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

}
