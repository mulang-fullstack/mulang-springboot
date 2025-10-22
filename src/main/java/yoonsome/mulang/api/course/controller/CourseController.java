package yoonsome.mulang.api.course.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import yoonsome.mulang.api.course.dto.CourseListRequest;
import yoonsome.mulang.api.course.dto.CourseListResponse;
import yoonsome.mulang.domain.category.entity.Category;
import yoonsome.mulang.domain.category.service.CategoryService;
import yoonsome.mulang.domain.course.service.CourseService;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class CourseController {
    private final CourseService courseService;
    private final CategoryService categoryService;

    @GetMapping("course")
    public String getCourseList(@ModelAttribute CourseListRequest request, HttpSession session, Model model) {
        //languageId 세션 복원/저장
        if (request.getLanguageId() == null) {
            Long sessionLanguageId = (Long) session.getAttribute("languageId");
            request.setLanguageId(sessionLanguageId != null ? sessionLanguageId : 1L);
        } else {
            session.setAttribute("languageId", request.getLanguageId());
        }

        //sort 세션 복원/저장
        if (request.getSort() == null) {
            String sessionSort = (String) session.getAttribute("sort");
            request.setSort(sessionSort != null ? sessionSort : "rating");
        } else {
            session.setAttribute("sort", request.getSort());
        }

        /*카테고리*/
        List<Category> categories = categoryService.getCategoryListByLanguageId(request);
        model.addAttribute("categories", categories);
        //System.out.println("@categories: " + categories);

        /*강좌*/
        System.out.println("@request:"+request);
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<CourseListResponse> courses = courseService.getCourseList(request, pageable);
        model.addAttribute("courses", courses.getContent());
        System.out.println("@course:"+courses.getContent());

        return "course/courseList";
    }
    /*
    @GetMapping("course")
    public Page<CourseListResponse> getCourseList(@ModelAttribute CourseListRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        return courseService.getCourseListByLanguageAndCategory(request.getLanguageId(), request.getCategoryId(), pageable);
    }*/
    @GetMapping("courseDetail")
    public String getCourseDetail(){
        return "course/courseDetail";
    }
}
