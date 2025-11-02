package yoonsome.mulang.api.ai.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import yoonsome.mulang.api.ai.dto.*;
import yoonsome.mulang.domain.ai.service.LevelTestService;
import yoonsome.mulang.infra.security.CustomUserDetails;

import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/ai/level")
@RequiredArgsConstructor
public class LevelTestController {

    private final LevelTestService levelTestService;

    /**
     * 메인 페이지
     */
    @GetMapping
    public String levelTestMain() {
        return "ai/aiLevelMain";
    }

    /**
     * 테스트 팝업 페이지 (초기 - 언어 선택)
     */
    @GetMapping("/test")
    public String levelTestPopup(Model model) {
        List<LanguageOption> languages = levelTestService.getAvailableLanguages();
        model.addAttribute("languages", languages);
        return "ai/aiLevelPopup";
    }

    /**
     * API: 테스트 가능 여부 확인 (새로 추가!)
     */
    @GetMapping("/api/check-availability")
    @ResponseBody
    public ResponseEntity<?> checkAvailability(
            @RequestParam String language,
            @AuthenticationPrincipal CustomUserDetails loginUser
    ) {
        try {
            if (loginUser == null) {
                return ResponseEntity.status(401)
                        .body(Map.of("success", false, "message", "로그인이 필요합니다."));
            }

            Long userId = loginUser.getUser().getId();
            TestAvailabilityResponse availability = levelTestService.checkTestAvailability(userId, language);

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "data", availability
            ));
        } catch (Exception e) {
            log.error("테스트 가능 여부 확인 오류", e);
            return ResponseEntity.status(500)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    /**
     * API: 테스트 시작 (문제 생성)
     */
    @PostMapping("/api/start")
    @ResponseBody
    public ResponseEntity<?> startTest(
            @RequestBody TestStartRequest request,
            @AuthenticationPrincipal CustomUserDetails loginUser
    ) {
        try {
            // 로그인 체크
            if (loginUser == null) {
                return ResponseEntity.status(401)
                        .body(Map.of("success", false, "message", "로그인이 필요합니다."));
            }

            Long userId = loginUser.getUser().getId();
            TestStartResponse response = levelTestService.startTest(userId, request);

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "data", response
            ));
        } catch (IllegalStateException e) {
            // 테스트 제한 오류
            log.warn("테스트 제한: {}", e.getMessage());
            return ResponseEntity.status(403)
                    .body(Map.of("success", false, "message", e.getMessage()));
        } catch (Exception e) {
            log.error("테스트 시작 오류", e);
            return ResponseEntity.status(500)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    /**
     * API: 답안 제출 및 채점
     */
    @PostMapping("/api/submit")
    @ResponseBody
    public ResponseEntity<?> submitTest(
            @RequestBody TestSubmitRequest request,
            @AuthenticationPrincipal CustomUserDetails loginUser
    ) {
        try {
            // 로그인 체크
            if (loginUser == null) {
                return ResponseEntity.status(401)
                        .body(Map.of("success", false, "message", "로그인이 필요합니다."));
            }

            TestResultResponse response = levelTestService.submitTest(request);

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "data", response
            ));
        } catch (Exception e) {
            log.error("답안 제출 오류", e);
            return ResponseEntity.status(500)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    /**
     * API: 지원 언어 목록
     */
    @GetMapping("/api/languages")
    @ResponseBody
    public ResponseEntity<?> getLanguages() {
        List<LanguageOption> languages = levelTestService.getAvailableLanguages();
        return ResponseEntity.ok(Map.of(
                "success", true,
                "data", languages
        ));
    }

    /**
     * API: 사용자 테스트 이력
     */
    @GetMapping("/api/history")
    @ResponseBody
    public ResponseEntity<?> getTestHistory(
            @AuthenticationPrincipal CustomUserDetails loginUser
    ) {
        try {
            // 로그인 체크
            if (loginUser == null) {
                return ResponseEntity.status(401)
                        .body(Map.of("success", false, "message", "로그인이 필요합니다."));
            }

            Long userId = loginUser.getUser().getId();
            var history = levelTestService.getUserTestHistory(userId);

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "data", history
            ));
        } catch (Exception e) {
            log.error("이력 조회 오류", e);
            return ResponseEntity.status(500)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }
}