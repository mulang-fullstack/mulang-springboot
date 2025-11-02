package yoonsome.mulang.api.qna.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import yoonsome.mulang.api.qna.dto.CourseAnswerRequest;
import yoonsome.mulang.api.qna.dto.CourseQuestionRequest;
import yoonsome.mulang.api.qna.dto.CourseQuestionResponse;
import yoonsome.mulang.api.qna.service.CourseQnaApiService;
import yoonsome.mulang.domain.user.entity.User;
import yoonsome.mulang.infra.security.CustomUserDetails;

import java.util.Map;


@RequestMapping("/player/{courseId}/api/qna")
@RequiredArgsConstructor
@RestController
public class CourseQnaApiController {

    private final CourseQnaApiService courseQnaApiService;

    /**
     * 강좌별 Q&A 조회
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getQnaByCourse(
            @PathVariable Long courseId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        Pageable pageable = PageRequest.of(page, size);

        User currentUser = (userDetails != null) ? userDetails.getUser() : null;

        Page<CourseQuestionResponse> result =
                courseQnaApiService.getQnaByCourse(courseId, pageable, currentUser);

        Map<String, Object> response = Map.of(
                "content", result.getContent(),
                "page", result.getNumber(),
                "size", result.getSize(),
                "totalElements", result.getTotalElements(),
                "totalPages", result.getTotalPages()
        );

        return ResponseEntity.ok(response);
    }

    /**
     * 질문 등록
     */
    @PostMapping("/question")
    public ResponseEntity<Void> createQuestion(
            @RequestBody CourseQuestionRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        if (userDetails == null || userDetails.getUser() == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        Long userId = userDetails.getUser().getId();
        courseQnaApiService.createQuestion(request, userId);
        return ResponseEntity.ok().build();
    }

    /**
     * 답변 등록
     */
    @PostMapping("/answer")
    public ResponseEntity<Void> createAnswer(
            @RequestBody CourseAnswerRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        if (userDetails == null || userDetails.getUser() == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        Long userId = userDetails.getUser().getId();
        courseQnaApiService.createAnswer(request, userId);
        return ResponseEntity.ok().build();
    }

    /**
     * 질문 수정
     */
    @PutMapping("/question/{questionId}")
    public ResponseEntity<Void> updateQuestion(
            @PathVariable Long questionId,
            @RequestBody CourseQuestionRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        if (userDetails == null || userDetails.getUser() == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        Long userId = userDetails.getUser().getId();
        courseQnaApiService.updateQuestion(questionId, request, userId);
        return ResponseEntity.ok().build();
    }

    /**
     * 답변 수정
     */
    @PutMapping("/answer/{answerId}")
    public ResponseEntity<Void> updateAnswer(
            @PathVariable Long answerId,
            @RequestBody CourseAnswerRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        if (userDetails == null || userDetails.getUser() == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        Long userId = userDetails.getUser().getId();
        courseQnaApiService.updateAnswer(answerId, request, userId);
        return ResponseEntity.ok().build();
    }

    /**
     * 질문 삭제
     */
    @DeleteMapping("/question/{questionId}")
    public ResponseEntity<Void> deleteQuestion(
            @PathVariable Long questionId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        if (userDetails == null || userDetails.getUser() == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        Long userId = userDetails.getUser().getId();
        courseQnaApiService.deleteQuestion(questionId, userId);
        return ResponseEntity.ok().build();
    }

    /**
     * 답변 삭제
     */
    @DeleteMapping("/answer/{answerId}")
    public ResponseEntity<Void> deleteAnswer(
            @PathVariable Long answerId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        if (userDetails == null || userDetails.getUser() == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        Long userId = userDetails.getUser().getId();
        courseQnaApiService.deleteAnswer(answerId, userId);
        return ResponseEntity.ok().build();
    }
}
