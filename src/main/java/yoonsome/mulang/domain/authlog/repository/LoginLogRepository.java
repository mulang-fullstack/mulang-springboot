package yoonsome.mulang.domain.authlog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yoonsome.mulang.domain.authlog.entity.LoginLog;

import java.util.List;

public interface LoginLogRepository extends JpaRepository<LoginLog, Long> {
    List<LoginLog> findByEmailOrderByCreatedAtDesc(String email);
}