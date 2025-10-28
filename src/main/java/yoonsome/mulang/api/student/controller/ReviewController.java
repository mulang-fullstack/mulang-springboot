package yoonsome.mulang.api.student.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import yoonsome.mulang.api.student.dto.ReviewResponse;
import yoonsome.mulang.api.student.service.ReviewForShowService;
import yoonsome.mulang.infra.security.CustomUserDetails;

import java.util.List;


@RequestMapping("/student")
@Controller
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewForShowService reviewForShowService;

    @GetMapping("review")
    public String review(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam(defaultValue = "최신순") String sort,
            Model model) {
        Long userId = userDetails.getUser().getId();

        List<ReviewResponse> reviews;
        if("최신순".equals(sort)){
            reviews = reviewForShowService.findByStudentIdOrderByCreatedAtDesc(userId);
        }else{
            reviews = reviewForShowService.findByStudentIdOrderByCreatedAtAsc(userId);
        }
        model.addAttribute("myReviews", reviews);
        model.addAttribute("currentSort", sort);



        return "student/review/review";
    }

    /*@GetMapping("reviewwrite")
    public String reviewWrite(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        Long userid = userDetails.getUser().getId();

        List<MycourseDTO> mycourseDTO = enrollmentRepository
                .findCourseProgressByStudentId(userid);

        List<CourseEnrollment> enrollments = courseEnrollmentRepository
                .findMyCoursesWithDetails(userid);


        List<MycourseDTO> mycourseDTO = enrollments.stream()
                .map(e -> MycourseDTO.from(e, userid, lectureRepository, enrollmentRepository))
                .collect(Collectors.toList());
        model.addAttribute("mycourseDTO", mycourseDTO);
        return "student/review/reviewwrite";
    }

    @PostMapping("reviewwrite")
    public String reviewWriteSubmit(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam Long courseId,
            @RequestParam Integer rating,
            @RequestParam String content) {
        boolean existReview = reviewService.existReview(userDetails.getUser(), courseId);
        if (existReview) {
            reviewService.updateReview(
                    userDetails.getUser(),
                    courseId,
                    rating,
                    content
            );

        } else {
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


        return "redirect:/student/review?success=true";

    }*/
}
