//package yoonsome.mulang.api.student.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//import yoonsome.mulang.api.student.dto.MycourseDTO;
//import yoonsome.mulang.domain.course.entity.Course;
//import yoonsome.mulang.domain.course.repository.CourseRepository;
//import yoonsome.mulang.domain.lecture.repository.LectureRepository;
//import yoonsome.mulang.domain.qna.entity.CourseQuestion;
//import yoonsome.mulang.domain.qna.repository.CourseQuestionRepository;
//import yoonsome.mulang.domain.qna.service.CourseQnaService;
//import yoonsome.mulang.infra.security.CustomUserDetails;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RequestMapping("/student")
//@Controller
//@RequiredArgsConstructor
//public class QnaController {
//
//    private final CourseEnrollmentRepository courseEnrollmentRepository;
//    private final LectureRepository lectureRepository;
//    private final EnrollmentRepository enrollmentRepository;
//    private final CourseQuestionRepository courseQuestionRepository;
//    private final CourseRepository courseRepository;
//    private final CourseQnaService courseQnaService;
//
//    @GetMapping("qna")
//    public String qna(Model model, @AuthenticationPrincipal CustomUserDetails userDetails, @RequestParam(defaultValue = "최신순") String sort) {
//        Long userId = userDetails.getUser().getId();
//
//        List<CourseQuestion> myqna;
//        if ("오래된순".equals(sort)) {
//            myqna = courseQnaService.getQuestionPageByUserAsc(userId);  // 오래된순
//        } else {
//            myqna = courseQnaService.getQuestionPageByUserDesc(userId);  // 최신순 (기본)
//        }
//        model.addAttribute("myqna", myqna);
//        model.addAttribute("currentSort", sort);
//
//        return "student/review/qna";
//    }
//
//    @GetMapping("qnawrite")
//    public String qnawrite(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
//
//        Long userId = userDetails.getUser().getId();
//
//        List<CourseEnrollment> enrollments = courseEnrollmentRepository
//                .findMyCoursesWithDetails(userId);
//
//        List<MycourseDTO> mycourseDTO = enrollments.stream()
//                .map(e -> MycourseDTO.from(e, userId, lectureRepository, enrollmentRepository))
//                .collect(Collectors.toList());
//        model.addAttribute("mycourseDTO", mycourseDTO);
//
//        return "student/review/qnawrite";
//    }
//
//    @PostMapping("qnawrite")
//    public String qnawrite(@RequestParam Long courseId,
//                           @RequestParam String content,
//                           @RequestParam String title,
//                           RedirectAttributes redirectAttributes,
//                           @AuthenticationPrincipal CustomUserDetails userDetails){
//       Course course = courseRepository.findById(courseId)
//               .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 강좌입니다."));
//
//       CourseQuestion courseQuestion = new CourseQuestion();
//       courseQuestion.setCourse(course);
//       courseQuestion.setTitle(title);
//       courseQuestion.setUser(userDetails.getUser());
//       courseQuestion.setContent(content);
//
//       courseQuestionRepository.save(courseQuestion);
//
//        redirectAttributes.addFlashAttribute("message", "질문이 등록되었습니다.");
//        return "redirect:/student/qna";
//    }
//
//}
