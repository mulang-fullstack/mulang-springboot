package yoonsome.mulang.mypage.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yoonsome.mulang.mypage.repository.ModifyRepository;
import yoonsome.mulang.user.entity.User;

@Service
@RequiredArgsConstructor
public class ModifyService {

    private final ModifyRepository modify;

    @Transactional
    public void updateUser(String nickname, String email, String phone, String password){
        User user = modify.findById(2l);

        user.setEmail(email);
        user.setNickname(nickname);
        user.setPhone(phone);
        user.setPassword(password);

        modify.update(user);
    }

}
