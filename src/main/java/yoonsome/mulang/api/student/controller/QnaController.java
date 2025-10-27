package yoonsome.mulang.api.student.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import yoonsome.mulang.api.student.dto.MycourseDTO;
import yoonsome.mulang.domain.enrollment.repository.EnrollmentRepository;
import yoonsome.mulang.domain.lecture.repository.LectureRepository;
import yoonsome.mulang.domain.payment.entity.CourseEnrollment;
import yoonsome.mulang.domain.payment.repository.CourseEnrollmentRepository;
import yoonsome.mulang.infra.security.CustomUserDetails;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/student")
@Controller
@RequiredArgsConstructor
public class QnaController {

    private final CourseEnrollmentRepository courseEnrollmentRepository;
    private final LectureRepository lectureRepository;
    private final EnrollmentRepository enrollmentRepository;

    @GetMapping("qna")
    public String qna(Model model) {

        return "student/review/qna";
    }

    @GetMapping("qnawrite")
    public String qnawrite(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {

        Long userId = userDetails.getUser().getId();

        List<CourseEnrollment> enrollments = courseEnrollmentRepository
                .findMyCoursesWithDetails(userId);

        List<MycourseDTO> mycourseDTO = enrollments.stream()
                .map(e -> MycourseDTO.from(e, userId, lectureRepository, enrollmentRepository))
                .collect(Collectors.toList());
        model.addAttribute("mycourseDTO", mycourseDTO);

        return "student/review/qnawrite";
    }

}
