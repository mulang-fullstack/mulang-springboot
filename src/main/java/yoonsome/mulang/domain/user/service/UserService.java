package yoonsome.mulang.domain.user.service;

import org.springframework.data.domain.Page;
import yoonsome.mulang.api.admin.user.dto.UserSearchRequest;
import yoonsome.mulang.domain.user.entity.User;

public interface UserService {
    //이메일로 회원 조회
    User findByEmail(String email);
    //회원 저장
    User saveUser(User user);
    //이메일 중복 여부 확인
    boolean existsByEmail(String email);
    //닉네임 중복 여부 확인
    boolean existsByNickname(String nickname);
    //회원 삭제
    void deleteUser(Long id);
    //회원 ID로 조회
    User findById(Long id);
    /**
     * 사용자 목록 검색 + 필터 + 정렬 + 페이징
     */
    Page<User> getUserList(UserSearchRequest request);
}
