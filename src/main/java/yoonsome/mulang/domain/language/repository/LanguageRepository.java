package yoonsome.mulang.domain.language.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yoonsome.mulang.domain.language.entity.Language;

public interface LanguageRepository extends JpaRepository<Language, Long> {
}
