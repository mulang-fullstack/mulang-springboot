package yoonsome.mulang.api.admin.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import yoonsome.mulang.api.admin.user.dto.*;
import yoonsome.mulang.api.admin.user.service.AdminUserService;
import yoonsome.mulang.domain.log.entity.UserLog;
import yoonsome.mulang.domain.user.entity.User;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin/user")
@RequiredArgsConstructor
public class AdminUserController {

    private final AdminUserService adminUserService;

    /** 사용자 목록 (동기 JSP) */
    @GetMapping
    public String getUserList(@ModelAttribute UserSearchRequest request, Model model) {
        Page<UserListResponse> userPage = adminUserService.getUserList(request);

        model.addAttribute("activeMenu", "user");
        model.addAttribute("activeSubmenu", "user");

        model.addAttribute("users", userPage.getContent());
        model.addAttribute("currentPage", userPage.getNumber());
        model.addAttribute("totalPages", userPage.getTotalPages());
        model.addAttribute("totalElements", userPage.getTotalElements());
        model.addAttribute("size", userPage.getSize());
        model.addAttribute("search", request);
        model.addAttribute("role", User.Role.values());

        return "admin/user/userList";
    }

    /** 사용자 목록 (비동기 JSON) */
    @GetMapping("/api")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getUserListApi(@ModelAttribute UserSearchRequest request) {
        // DTO 내용 확인용 출력
        System.out.println("============ UserSearchRequest ===========");
        System.out.println(request);
        Page<UserListResponse> userPage = adminUserService.getUserList(request);

        Map<String, Object> response = new HashMap<>();
        response.put("users", userPage.getContent());
        response.put("currentPage", userPage.getNumber());
        response.put("totalPages", userPage.getTotalPages());
        response.put("totalElements", userPage.getTotalElements());
        response.put("size", userPage.getSize());
        return ResponseEntity.ok(response);
    }

    /** 사용자 로그 (동기 JSP) */
    @GetMapping("/log")
    public String getUserLogList(@ModelAttribute UserLogSearchRequest request, Model model) {
        Page<UserLogListResponse> logPage = adminUserService.getUserLogList(request);

        model.addAttribute("activeMenu", "user");
        model.addAttribute("activeSubmenu", "userLog");

        model.addAttribute("logs", logPage.getContent());
        model.addAttribute("currentPage", logPage.getNumber());
        model.addAttribute("totalPages", logPage.getTotalPages());
        model.addAttribute("totalElements", logPage.getTotalElements());
        model.addAttribute("size", logPage.getSize());
        model.addAttribute("search", request);
        model.addAttribute("actions", UserLog.ActionType.values());

        return "admin/user/userLog";
    }

    /** 사용자 로그 (비동기 JSON) */
    @GetMapping("/log/api")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getUserLogListApi(@ModelAttribute UserLogSearchRequest request) {
        System.out.println("==================나를 보거라======================");
        System.out.println(request);
        Page<UserLogListResponse> logPage = adminUserService.getUserLogList(request);

        System.out.println("====================================="+logPage.getContent());
        Map<String, Object> response = new HashMap<>();
        response.put("logs", logPage.getContent());
        response.put("currentPage", logPage.getNumber());
        response.put("totalPages", logPage.getTotalPages());
        response.put("totalElements", logPage.getTotalElements());
        response.put("size", logPage.getSize());
        return ResponseEntity.ok(response);
    }
}
