package yoonsome.mulang.mypage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yoonsome.mulang.mypage.DTO.MypageResponse;
import yoonsome.mulang.user.entity.User;
import yoonsome.mulang.user.service.UserService;

@Service
@RequiredArgsConstructor //
public class MypageServiceImpl implements MypageService {

    private final UserService userService;

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

    public boolean verifypassword(Long userid, String password) {
        User user = userService.findById(userid);

    }

}
