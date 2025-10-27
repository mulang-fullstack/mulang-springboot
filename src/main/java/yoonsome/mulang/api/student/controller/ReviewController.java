package yoonsome.mulang.api.student.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import yoonsome.mulang.api.student.dto.MycourseDTO;
import yoonsome.mulang.api.student.service.CourseReviewService;  // ← 이거 추가!
import yoonsome.mulang.domain.enrollment.repository.EnrollmentRepository;
import yoonsome.mulang.infra.security.CustomUserDetails;

import java.util.List;

@RequestMapping("/student")
@Controller
@RequiredArgsConstructor
public class ReviewController {

    private final EnrollmentRepository enrollmentRepository;
    private final CourseReviewService reviewService;  // ← ReviewService → CourseReviewService

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
        return "student/review/reviewwrite";
    }

    @PostMapping("reviewwrite")
    public String reviewWriteSubmit(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam Long courseId,
            @RequestParam Integer rating,
            @RequestParam String content) {

        try {
            reviewService.createReview(
                    userDetails.getUser(),
                    courseId,
                    rating,
                    content
            );
            return "redirect:/student/review?success=true";
        } catch (Exception e) {
            return "redirect:/student/reviewwrite?error=" + e.getMessage();
        }
    }
}
