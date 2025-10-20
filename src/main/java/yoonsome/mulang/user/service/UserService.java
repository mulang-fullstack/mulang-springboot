package yoonsome.mulang.user.service;

import yoonsome.mulang.user.entity.User;

public interface UserService {
    //이메일로 회원 조회
    User findByEmail(String email);
    //회원 저장
    User saveUser(User user);
    //이메일 중복 여부 확인
    boolean existsByEmail(String email);
    //회원 삭제
    void deleteUser(Long id);
    //회원 ID로 조회
    User findById(Long id);
}
