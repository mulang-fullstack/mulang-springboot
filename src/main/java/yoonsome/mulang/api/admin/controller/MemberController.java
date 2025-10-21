//package yoonsome.mulang.admin.controller;
//
//import yoonsome.mulang.admin.dto.MemberDTO;
//import yoonsome.mulang.admin.dto.PageDTO;
//import yoonsome.mulang.admin.service.MemberService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.http.ResponseEntity;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * 관리자 회원 관리 컨트롤러
// */
//@Controller
//@RequestMapping("/admin/user")
//@RequiredArgsConstructor
//public class MemberController {
//
//    private final MemberService memberService;
//
//    /**
//     * 회원 목록 페이지
//     */
//    @GetMapping("/memberList")
//    public String memberList(
//            @RequestParam(value = "page", defaultValue = "1") int page,
//            @RequestParam(value = "size", defaultValue = "10") int size,
//            @RequestParam(value = "roles", required = false) String roles,
//            @RequestParam(value = "status", defaultValue = "ALL") String status,
//            @RequestParam(value = "sort", defaultValue = "LATEST") String sort,
//            @RequestParam(value = "keyword", required = false) String keyword,
//            Model model) {
//
//        // 필터 조건 생성
//        Map<String, Object> filters = new HashMap<>();
//        if (roles != null && !roles.isEmpty()) {
//            filters.put("roles", roles.split(","));
//        }
//        if (!"ALL".equals(status)) {
//            filters.put("status", status);
//        }
//        filters.put("sort", sort);
//        if (keyword != null && !keyword.isEmpty()) {
//            filters.put("keyword", keyword);
//        }
//
//        // 페이지 데이터 조회
//        PageDTO<MemberDTO> pageData = memberService.getMemberList(page, size, filters);
//
//        // 모델에 데이터 추가
//        model.addAttribute("members", pageData.getContent());
//        model.addAttribute("currentPage", pageData.getCurrentPage());
//        model.addAttribute("totalPages", pageData.getTotalPages());
//        model.addAttribute("totalCount", pageData.getTotalCount());
//
//        return "admin/userManage/member";
//    }
//
//    /**
//     * 회원 상태 수정 (AJAX)
//     */
//    @PostMapping("/updateStatus")
//    @ResponseBody
//    public ResponseEntity<Map<String, Object>> updateStatus(@RequestBody Map<String, Object> request) {
//        Map<String, Object> response = new HashMap<>();
//
//        try {
//            Long memberId = Long.valueOf(request.get("memberId").toString());
//            String status = request.get("status").toString();
//
//            boolean success = memberService.updateMemberStatus(memberId, status);
//
//            response.put("success", success);
//            if (success) {
//                response.put("message", "상태가 성공적으로 변경되었습니다.");
//            } else {
//                response.put("message", "상태 변경에 실패했습니다.");
//            }
//
//            return ResponseEntity.ok(response);
//
//        } catch (Exception e) {
//            response.put("success", false);
//            response.put("message", "오류가 발생했습니다: " + e.getMessage());
//            return ResponseEntity.status(500).body(response);
//        }
//    }
//
//    /**
//     * 회원 삭제 (AJAX)
//     */
//    @DeleteMapping("/memberDelete")
//    @ResponseBody
//    public ResponseEntity<Map<String, Object>> deleteMember(@RequestParam Long id) {
//        Map<String, Object> response = new HashMap<>();
//
//        try {
//            boolean success = memberService.deleteMember(id);
//
//            response.put("success", success);
//            if (success) {
//                response.put("message", "회원이 성공적으로 삭제되었습니다.");
//            } else {
//                response.put("message", "회원 삭제에 실패했습니다.");
//            }
//
//            return ResponseEntity.ok(response);
//
//        } catch (Exception e) {
//            response.put("success", false);
//            response.put("message", "오류가 발생했습니다: " + e.getMessage());
//            return ResponseEntity.status(500).body(response);
//        }
//    }
//}