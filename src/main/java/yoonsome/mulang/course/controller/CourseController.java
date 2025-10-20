package yoonsome.mulang.course.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import yoonsome.mulang.course.dto.CourseListRequest;
import yoonsome.mulang.course.dto.CourseListResponse;
import yoonsome.mulang.course.entity.Course;
import yoonsome.mulang.course.repository.CourseRepository;
import yoonsome.mulang.course.service.CourseService;

@RequiredArgsConstructor
@Controller
public class CourseController {
    private final CourseService courseService;
    private final CourseRepository courseRepository;//
    /*
    @GetMapping("course")
    public String getCourseList(){
        return "course/courseList";
    }*/

    @GetMapping("course")
    public String getCourseList(@ModelAttribute CourseListRequest request, Model model) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<CourseListResponse> courses = courseService.getCourseListByLanguageAndCategory(
                request.getLanguageId(), request.getCategoryId(), pageable);
        model.addAttribute("courses", courses);
        System.out.println(courses.getContent());
        courses.forEach(System.out::println);
        /*
        // Page 객체 내용 확인
        System.out.println("총 데이터 수: " + courses.getTotalElements());
        System.out.println("총 페이지 수: " + courses.getTotalPages());

        // 실제 데이터 하나하나 확인
        for (CourseListResponse dto : courses.getContent()) {
            System.out.println(dto);}*/
        for (CourseListResponse dto : courses.getContent()) {
            System.out.println(dto.getTitle() + " / " + dto.getTeacherName());
        }
        Page<Course> rawCourses = courseRepository.findByLanguage_IdAndCategory_Id(
                request.getLanguageId(), request.getCategoryId(), pageable);

        System.out.println("Total Elements: " + rawCourses.getTotalElements());
        rawCourses.getContent().forEach(c -> System.out.println(c.getId() + " / " + c.getTitle()));
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
