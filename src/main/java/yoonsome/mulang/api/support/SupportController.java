package yoonsome.mulang.api.support;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import yoonsome.mulang.domain.notice.dto.NoticeListResponse;
import yoonsome.mulang.domain.notice.dto.NoticeSearchRequest;
import yoonsome.mulang.api.support.dto.NoticeDetailResponse;
import yoonsome.mulang.domain.notice.service.NoticeService;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/support/notice")
public class SupportController {

    private final NoticeService noticeService;

    /**
     * 공지사항 목록 페이지
     * @return notice.jsp
     */
    @GetMapping
    public String notice() {
        return "notice/notice";
    }

    /**
     * 공지사항 목록 데이터 (비동기)
     * @param request 검색 조건
     * @return 공지사항 LIST, PAGINATION
     */
    @GetMapping("/api")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getNoticeList(NoticeSearchRequest request) {
        Page<NoticeListResponse> page = noticeService.getNoticeList(request);

        Map<String, Object> result = new HashMap<>();
        result.put("notices", page.getContent());
        result.put("currentPage", page.getNumber());
        result.put("totalPages", page.getTotalPages());
        result.put("size", page.getSize());

        return ResponseEntity.ok(result);
    }

    /**
     * 공지사항 상세 조회 페이지 (동기)
     * @param noticeId 공지사항 ID
     * @param model 뷰 모델
     * @return notice-detail.jsp
     */
    @GetMapping("/{noticeId}")
    public String getNoticeDetail(@PathVariable Long noticeId, Model model) {
        // 서비스에서 DTO로 변환된 데이터 받기
        NoticeDetailResponse notice = noticeService.getNoticeById(noticeId);

        model.addAttribute("notice", notice);
        return "notice/noticeDetail";
    }
}