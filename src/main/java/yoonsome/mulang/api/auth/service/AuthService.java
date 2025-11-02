package yoonsome.mulang.api.auth.service;

import yoonsome.mulang.api.auth.dto.SignupRequest;

public interface AuthService {
    /**
     * 회원가입 처리
     * @param request 가입 요청 DTO
     */
    boolean signup(SignupRequest request);

    /**
     * 회원가입 시 인증 메일을 발송
     * @param email 수신자 이메일 주소
     */
    void sendSignupVerification(String email);
}
