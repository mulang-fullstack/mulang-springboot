package yoonsome.mulang.infra.file.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import yoonsome.mulang.infra.file.entity.File;

public interface FileRepository extends JpaRepository<File, Long> {
}
