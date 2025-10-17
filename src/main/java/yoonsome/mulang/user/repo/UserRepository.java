package yoonsome.mulang.user.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import yoonsome.mulang.user.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}