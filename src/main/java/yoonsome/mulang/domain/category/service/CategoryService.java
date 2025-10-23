package yoonsome.mulang.domain.category.service;

import yoonsome.mulang.api.course.dto.CourseListRequest;
import yoonsome.mulang.domain.category.entity.Category;
import java.util.List;

public interface CategoryService {
    //언어 ID를 기준으로 카테고리 목록 조회
    List<Category> getCategoryListByLanguageId(CourseListRequest request);

    //카테고리 ID로 단일 카테고리 조회
    Category getById(Long id);
}
