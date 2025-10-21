package yoonsome.mulang.domain.category.service;

import yoonsome.mulang.api.course.dto.CourseListRequest;
import yoonsome.mulang.domain.category.entity.Category;
import java.util.List;

public interface CategoryService {
    List<Category> getCategoryListByLanguageId(CourseListRequest request);
}
