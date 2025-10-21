package yoonsome.mulang.api.course.controller;

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
import yoonsome.mulang.domain.course.entity.Course;
import yoonsome.mulang.domain.course.repository.CourseRepository;
import yoonsome.mulang.domain.course.service.CourseService;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class CourseController {
    private final CourseService courseService;
    private final CategoryService categoryService;
    private final CourseRepository courseRepository;//
    /*
    @GetMapping("course")
    public String getCourseList(){
        return "course/courseList";
    }*/

    @GetMapping("course")
    public String getCourseList(@ModelAttribute CourseListRequest request, Model model) {
        /*카테고리*/
        List<Category> categoryList = categoryService.getCategoryListByLanguageId(request);
        System.out.println("categoryList: " + categoryList);
        model.addAttribute("categories", categoryList);

        /*강좌*/
        System.out.println("@request:"+request);
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<CourseListResponse> courses = courseService.getCourseList(request, pageable);
        model.addAttribute("courses", courses.getContent());
        System.out.println("@course:"+courses.getContent());
        /*
        Page<Course> rawCourses = courseRepository.findByLanguage_IdAndCategory_Id(
                request.getLanguageId(), request.getCategoryId(), pageable);

        System.out.println("Total Elements: " + rawCourses.getTotalElements());
        rawCourses.getContent().forEach(c -> System.out.println(c.getId() + " / " + c.getTitle()));*/
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
    @GetMapping("courseCurriculum")
    public String getCourseCurriculum(){
        return "course/courseCurriculum";
    }
    @GetMapping("courseReview")
    public String getCourseReview(){
        return "course/courseReview";
    }
}
