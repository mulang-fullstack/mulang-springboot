package yoonsome.mulang.file.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import yoonsome.mulang.file.entity.File;

public interface FileRepository extends JpaRepository<File, Long> {
}
