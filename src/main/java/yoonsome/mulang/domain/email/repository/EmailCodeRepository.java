package yoonsome.mulang.domain.email.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yoonsome.mulang.domain.email.entity.EmailCode;

import java.util.Optional;

public interface EmailCodeRepository extends JpaRepository<EmailCode, Long> {
    Optional<EmailCode> findByEmail(String email);
    Optional<EmailCode> findTopByEmailOrderByCreatedAtDesc(String email);
}
