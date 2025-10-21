package yoonsome.mulang.mypage.service;

import yoonsome.mulang.mypage.DTO.MypageResponse;

public interface MypageService {

    MypageResponse getUserInfo(Long userid);
    boolean verifypassword(Long userid, String password);

}
