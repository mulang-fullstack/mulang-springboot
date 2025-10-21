package yoonsome.mulang.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yoonsome.mulang.domain.user.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    //이메일 회원 조회
    Optional<User> findByEmail(String email);
    //이메일 중복 여부 확인
    boolean existsByEmail(String email);
}