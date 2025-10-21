package yoonsome.mulang.api.auth.service;

import yoonsome.mulang.api.auth.dto.LoginRequest;
import yoonsome.mulang.api.auth.dto.SignupRequest;
import yoonsome.mulang.domain.user.entity.User;

public interface AuthService {
    /**
     * 회원가입 처리
     * @param request 가입 요청 DTO
     */
    boolean signup(SignupRequest request);
    /**
     * 이메일이 이미 존재하는지 확인
     * @param email 이메일 주소
     * @return 존재하면 true, 없으면 false
     */
    boolean isEmailExists(String email);
    /**
     * 회원가입 시 인증 메일을 발송
     * @param email 수신자 이메일 주소
     */
    void sendSignupVerification(String email);
}
