package yoonsome.mulang.global.file.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import yoonsome.mulang.global.file.entity.File;

public interface FileRepository extends JpaRepository<File, Long> {
}
