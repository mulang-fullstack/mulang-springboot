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
public class SaveController {
    @GetMapping("save")
    public String save(Model model) {
        return "mypage/like/save";
    }

}
