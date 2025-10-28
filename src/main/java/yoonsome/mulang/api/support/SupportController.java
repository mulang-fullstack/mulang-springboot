package yoonsome.mulang.api.support;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import yoonsome.mulang.api.admin.system.dto.NoticeListResponse;
import yoonsome.mulang.api.admin.system.dto.NoticeSearchRequest;
import yoonsome.mulang.domain.notice.entity.Notice;
import yoonsome.mulang.domain.notice.service.NoticeService;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/support/notice")
public class SupportController {

    private final NoticeService noticeService;

    /**
     * 공지사항 페이지
     * @return notice.jsp
     */
    @GetMapping
    public String notice() {
        return "notice/notice";
    }

    /**
     * 공지사항 데이터(비동기)
     * @param request
     * @return 공지사항LIST, PAGINATION
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
     * 공지사항 상세 조회 페이지
     * @param noticeId 공지사항 ID
     * @return noticeDetail.jsp
     */
    @GetMapping("/{noticeId}")
    public String getNoticeDetail(@PathVariable Long noticeId, org.springframework.ui.Model model) {
        Notice notice = noticeService.getNoticeById(noticeId);
        model.addAttribute("notice", notice);
        return "noticeDetail";
    }

}