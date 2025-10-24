package yoonsome.mulang.domain.category.service;

import yoonsome.mulang.domain.course.dto.CourseListRequest;
import yoonsome.mulang.domain.category.entity.Category;
import yoonsome.mulang.domain.language.entity.Language;

import java.util.List;

public interface CategoryService {
    //언어 ID를 기준으로 카테고리 목록 조회
    List<Category> getCategoryListByLanguageId(Long languageId);

    //카테고리 ID로 단일 카테고리 조회
    Category getById(Long id);
    
    //전체
    List<Category> getAllCategory();
}
