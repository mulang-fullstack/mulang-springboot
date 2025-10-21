package yoonsome.mulang.domain.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yoonsome.mulang.domain.category.entity.Category;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    List<Category> findByLanguage_Id(Long languageId);
}
