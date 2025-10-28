package yoonsome.mulang.api.student.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import yoonsome.mulang.api.student.dto.MycourseResponse;
import yoonsome.mulang.api.student.dto.ReviewRequest;
import yoonsome.mulang.api.student.dto.ReviewResponse;
import yoonsome.mulang.api.student.service.MyCourseInfoService;
import yoonsome.mulang.api.student.service.MypageReviewService;
import yoonsome.mulang.domain.review.entity.CourseReview;
import yoonsome.mulang.domain.review.service.ReviewService;
import yoonsome.mulang.infra.security.CustomUserDetails;

import java.util.List;


@RequestMapping("/student")
@Controller
@RequiredArgsConstructor
public class ReviewController {

    private final MypageReviewService mypageReviewService;
    private final ReviewService reviewService;
    private final MyCourseInfoService myCourseInfoService;

    @GetMapping("review")
    public String review(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam(defaultValue = "최신순") String sort,
            Model model) {
        Long userId = userDetails.getUser().getId();

        List<ReviewResponse> reviews;
        if ("최신순".equals(sort)) {
            reviews = mypageReviewService.findByStudentIdOrderByCreatedAtDesc(userId);
        } else {
            reviews = mypageReviewService.findByStudentIdOrderByCreatedAtAsc(userId);
        }
        model.addAttribute("myReviews", reviews);
        model.addAttribute("currentSort", sort);


        return "student/review/review";
    }

    @GetMapping("reviewwrite")
    public String reviewWrite(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {

        Long userid = userDetails.getUser().getId();
        List<MycourseResponse> mycourseDTO = myCourseInfoService.findByUserId(userid); //

        model.addAttribute("mycourseDTO", mycourseDTO);

        return "student/review/reviewwrite";
    }

    @PostMapping("reviewwrite")
    public String reviewWriteSubmit(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @ModelAttribute ReviewRequest request,
            RedirectAttributes redirectAttributes,
            Model model) {

        Long userId = userDetails.getUser().getId();

        if (reviewService.existReview(userId, request.getCourseId())) {
            //  redirectAttributes 대신 model 사용 (같은 페이지에 머물기)
            model.addAttribute("error", "이미 리뷰를 작성하셨습니다.");

            // 강좌 목록 다시 조회
            List<MycourseResponse> mycourseDTO = myCourseInfoService.findByUserId(userId);
            model.addAttribute("mycourseDTO", mycourseDTO);

            return "student/review/reviewwrite";
        }

        try {
            CourseReview review = request.toEntity(userId);
            reviewService.saveReview(review);

            // ⭐ 성공 시에만 redirectAttributes 사용
            redirectAttributes.addFlashAttribute("message", "리뷰가 등록되었습니다.");
            return "redirect:/student/review";

        } catch (Exception e) {
            e.printStackTrace();

            // ⭐ 오류 시 model 사용
            model.addAttribute("error", "리뷰 등록에 실패했습니다: " + e.getMessage());

            // 강좌 목록 다시 조회
            List<MycourseResponse> mycourseDTO = myCourseInfoService.findByUserId(userId);
            model.addAttribute("mycourseDTO", mycourseDTO);

            return "student/review/reviewwrite";  // ⭐ 리다이렉트 아님
        }
    }

    @GetMapping("review/{reviewId}")
    public String detailReview(
            @PathVariable Long reviewId,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            Model model) {

        Long userId = userDetails.getUser().getId();

        // 리뷰 조회
        ReviewResponse review = mypageReviewService.findById(reviewId);

        // 본인 리뷰인지 확인
        if (!review.getStudent().getId().equals(userId)) {
            return "redirect:/student/review";
        }

        model.addAttribute("review", review);
        return "student/review/detailreview";

    }

    //  리뷰 수정 폼
    @GetMapping("/review/edit/{reviewId}")
    public String editReview(
            @PathVariable Long reviewId,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            Model model) {

        Long userId = userDetails.getUser().getId();
        ReviewResponse review = mypageReviewService.findById(reviewId);

        // 본인 리뷰인지 확인
        if (!review.getStudent().getId().equals(userId)) {
            return "redirect:/student/review";
        }

        model.addAttribute("review", review);
        return "student/review/reviewmodify";  // 수정 폼 JSP
    }

    @PostMapping("/review/edit/{reviewid}")
    public String editReview(@AuthenticationPrincipal CustomUserDetails userDetails, @ModelAttribute ReviewRequest request,
                             RedirectAttributes redirectAttributes, Model model) {

        Long userId = userDetails.getUser().getId();
        try {
            CourseReview review = request.toEntity(userId);
            reviewService.saveReview(review);

            // ⭐ 성공 시에만 redirectAttributes 사용
            redirectAttributes.addFlashAttribute("message", "리뷰가 등록되었습니다.");
            return "redirect:/student/review";

        } catch (Exception e) {
            e.printStackTrace();


            model.addAttribute("error", "리뷰 등록에 실패했습니다: " + e.getMessage());

            // 강좌 목록 다시 조회
            List<MycourseResponse> mycourseDTO = myCourseInfoService.findByUserId(userId);
            model.addAttribute("mycourseDTO", mycourseDTO);

            return "student/review/reviewwrite";
        }
    }
}