package yoonsome.mulang.api.student.service;

import org.springframework.web.multipart.MultipartFile;
import yoonsome.mulang.api.student.dto.MypageResponse;

public interface MypageService {

    MypageResponse getUserInfo(Long userid);
    boolean verifyPassword(String password, String realpassword);
    void updateUserInfo(Long userid, String email, String nickname, MultipartFile photo);
    void updatepassword(Long userid,String password);


}
