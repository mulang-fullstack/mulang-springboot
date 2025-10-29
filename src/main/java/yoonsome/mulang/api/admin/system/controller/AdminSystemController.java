package yoonsome.mulang.api.admin.system.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import yoonsome.mulang.domain.notice.dto.NoticeCreateRequest;
import yoonsome.mulang.domain.notice.dto.NoticeListResponse;
import yoonsome.mulang.domain.notice.dto.NoticeSearchRequest;
import yoonsome.mulang.domain.notice.entity.Notice;
import yoonsome.mulang.domain.notice.service.NoticeService;
import yoonsome.mulang.infra.security.CustomUserDetails;

import java.util.HashMap;
import java.util.Map;

/**
 * 관리자 시스템 관리 컨트롤러
 * - 공지사항 관리
 * - 문의사항 관리
 */
@Controller
@RequestMapping("/admin/system")
@RequiredArgsConstructor
public class AdminSystemController {

    private final NoticeService noticeService;

    /**
     * 공지사항 관리 페이지
     */
    @GetMapping("/notice")
    public String notice(Model model){
        model.addAttribute("activeMenu","system");
        model.addAttribute("activeSubmenu","notice");
        return "admin/system/noticeManage";
    }

    /**
     * 공지사항 목록 조회 (비동기 API)
     */
    @GetMapping("/notice/api")
    @ResponseBody
    public ResponseEntity<?> getNoticeList(NoticeSearchRequest request) {
        Page<NoticeListResponse> page = noticeService.getNoticeList(request);

        Map<String, Object> result = new HashMap<>();
        result.put("notices", page.getContent());
        result.put("currentPage", page.getNumber());
        result.put("totalPages", page.getTotalPages());
        result.put("totalElements", page.getTotalElements());
        result.put("size", page.getSize());

        return ResponseEntity.ok(result);
    }

    /**
     * 공지 생성 (JSON 요청)
     */
    @PostMapping("/notice")
    @ResponseBody
    public ResponseEntity<?> createNotice(
            @Valid @RequestBody NoticeCreateRequest request,
            BindingResult bindingResult,
            @AuthenticationPrincipal CustomUserDetails principal
    ) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("입력값 오류");
        }
        Notice saved = noticeService.createNotice(request, principal.getUser());
        return ResponseEntity.ok("공지사항 등록 완료 (ID: " + saved.getId() + ")");
    }

    /**
     * 문의사항 관리 페이지
     */
    @GetMapping("/inquiry")
    public String inquiry(Model model) {
        model.addAttribute("activeMenu", "system");
        model.addAttribute("activeSubmenu", "inquiry");
        return "admin/system/inquiryManage";
    }
}