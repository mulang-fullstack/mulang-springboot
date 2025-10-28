package yoonsome.mulang.api.qna.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import yoonsome.mulang.domain.course.entity.Course;
import yoonsome.mulang.domain.course.service.CourseService;

@Controller
@RequiredArgsConstructor
public class CourseQnaViewController {

    private final CourseService courseService;

    /**
     * 강좌별 Q&A 게시판 보기
     * 예: /student/course/{courseId}
     */
    @GetMapping("/student/course/{courseId}")
    public String showCourseQnaPage(@PathVariable Long courseId, Model model) {

        // [1] 강좌 정보만 조회
        Course course = courseService.getCourse(courseId);
        model.addAttribute("course", course);

        // [2] Q&A 게시판만 렌더링 (lecture 없음)
        return "student/mycourse/player";  // 기존 JSP 재활용
    }
}
