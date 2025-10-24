package yoonsome.mulang.api.student.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import yoonsome.mulang.api.student.dto.MycourseDTO;
import yoonsome.mulang.domain.enrollment.repository.EnrollmentRepository;
import yoonsome.mulang.infra.security.CustomUserDetails;

import java.util.List;

@RequestMapping("/student")
@Controller
@RequiredArgsConstructor
public class ReviewController {


    private final EnrollmentRepository enrollmentRepository;

    @GetMapping("review")
    public String review() {
        return "student/review/review";
    }
    @GetMapping("reviewwrite")
    public String reviewWrite(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {


        Long userid = userDetails.getUser().getId();

        List<MycourseDTO> mycourseDTO = enrollmentRepository
                .findCourseProgressByStudentId(userid);

        model.addAttribute("mycourseDTO", mycourseDTO);

        return "student/review/reviewwrite"; // JSP 경로
    }

}
