package yoonsome.mulang.api.student.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yoonsome.mulang.global.util.BCryptEncoder;
import yoonsome.mulang.api.student.dto.MypageResponse;
import yoonsome.mulang.domain.user.entity.User;
import yoonsome.mulang.domain.user.service.UserService;

@Service
@RequiredArgsConstructor //
public class MypageServiceImpl implements MypageService {

    private final UserService userService;
    private final BCryptEncoder bCryptEncoder;

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
        return bCryptEncoder.matches(password, realpassword);
    }

}
