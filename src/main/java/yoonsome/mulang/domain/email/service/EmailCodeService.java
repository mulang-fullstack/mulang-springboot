package yoonsome.mulang.domain.email.service;

public interface EmailCodeService {

    /**
     * 인증 코드 저장 (기존 이메일 있으면 갱신)
     */
    void saveCode(String email, String code);
    /**
     * 인증 코드 검증
     * @return true = 유효한 코드, false = 만료 또는 불일치
     */
    String verifyCode(String email, String code);
}
