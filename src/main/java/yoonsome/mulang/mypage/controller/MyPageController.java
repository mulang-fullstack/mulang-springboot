package yoonsome.mulang.mypage.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import yoonsome.mulang.user.entity.User;

import java.sql.*;

@RequestMapping("mypage")
@Controller
public class MyPageController {
    @PersistenceContext
    private EntityManager em;

    @GetMapping("personal")
    public String personal(Model model) {

        User user = em.find(User.class, 2L);
        model.addAttribute("user", user);
        return "mypage/profile/personal";
    }



    @GetMapping("save")
    public String save(Model model) {

        return "mypage/like/save";
    }

    @GetMapping("pay")
    public String pay() {

        return "mypage/payhistory/pay";
    }

    @GetMapping("course")
    public String coruse() {

        return "mypage/mycourse/course";
    }

    @GetMapping("review")
    public String review() {

        return "mypage/review/review";
    }

    @GetMapping("qna")
    public String qna() {

        return "mypage/review/qna";
    }
    @GetMapping("realpay")
    public String realpay() {

        return "mypage/realpay";
    }
}
