package yoonsome.mulang.domain.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yoonsome.mulang.api.course.dto.CourseListRequest;
import yoonsome.mulang.domain.category.entity.Category;
import yoonsome.mulang.domain.category.repository.CategoryRepository;
import yoonsome.mulang.domain.language.entity.Language;
import yoonsome.mulang.domain.language.repository.LanguageRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private final CategoryRepository categoryRepository;
    @Autowired
    private LanguageRepository languageRepository;

    @Override
    public List<Category> getCategoryListByLanguageId(CourseListRequest request) {
        Long languageId = request.getLanguageId();
        return categoryRepository.findByLanguage_Id(languageId);
    }
    @Override
    public Category getById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 존재하지 않습니다. id=" + id));
    }
    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }
}
