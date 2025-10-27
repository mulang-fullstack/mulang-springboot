package yoonsome.mulang.domain.notice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yoonsome.mulang.domain.notice.entity.Notice;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
    // 기본 CRUD는 JpaRepository가 제공
}
