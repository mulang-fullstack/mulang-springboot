package yoonsome.mulang.api.student.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import yoonsome.mulang.api.student.dto.MypageResponse;
import yoonsome.mulang.domain.user.entity.User;
import yoonsome.mulang.domain.user.service.UserService;

@Service
@RequiredArgsConstructor //
public class MypageServiceImpl implements MypageService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public MypageResponse getUserInfo(Long userid) {
            
        User user = userService.findById(userid); // userservice로 db조회

        MypageResponse userSearchInfoDTO = new MypageResponse();
        userSearchInfoDTO.setId(user.getId());
        userSearchInfoDTO.setNickname(user.getNickname());
        userSearchInfoDTO.setUsername(user.getUsername());
        userSearchInfoDTO.setEmail(user.getEmail());

        return userSearchInfoDTO;
    }

    public boolean verifyPassword(String password, String realpassword) {
        return passwordEncoder.matches(password, realpassword);
    }
    @Transactional
    public void updateUserInfo(Long userid,  String email, String nickname){
        User user = userService.findById(userid);

        user.setNickname(nickname);
        user.setEmail(email);


    }
    @Transactional
    public void updatepassword(Long userid,String password){
        User user = userService.findById(userid);
        user.setPassword(passwordEncoder.encode(password));
    }



}
