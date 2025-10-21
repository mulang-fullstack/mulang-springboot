package yoonsome.mulang.api.student.service;

import yoonsome.mulang.api.student.dto.MypageResponse;

public interface MypageService {

    MypageResponse getUserInfo(Long userid);
    public boolean verifyPassword(String password, String realpassword);


}
