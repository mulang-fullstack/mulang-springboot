package yoonsome.mulang.student.service;

import yoonsome.mulang.student.DTO.MypageResponse;

public interface MypageService {

    MypageResponse getUserInfo(Long userid);
    public boolean verifyPassword(String password, String realpassword);


}
