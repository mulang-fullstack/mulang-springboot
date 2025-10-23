package yoonsome.mulang.api.admin.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import yoonsome.mulang.api.admin.user.dto.UserListResponse;
import yoonsome.mulang.api.admin.user.dto.UserSearchRequest;
import yoonsome.mulang.api.admin.user.service.AdminUserService;
import yoonsome.mulang.api.admin.user.service.AdminUserServiceImpl;
import yoonsome.mulang.domain.user.entity.User;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin/user")
@RequiredArgsConstructor
public class AdminUserController {
    private final AdminUserService adminUserService;

    @GetMapping
    public String getUserList(@ModelAttribute UserSearchRequest request, Model model) {
        // DTO 내용 확인용 출력
        System.out.println("=== UserSearchRequest ===");
        System.out.println(request);
        Page<UserListResponse> userPage = adminUserService.getUserList(request);

        // 사이드바 상태 유지
        model.addAttribute("activeMenu","user");
        model.addAttribute("activeSubmenu","user");

        // 데이터
        model.addAttribute("users", userPage.getContent());
        model.addAttribute("page", userPage);
        model.addAttribute("search", request);
        model.addAttribute("role", User.Role.values());

        return "admin/user/userList";
    }

    // 비동기 요청 처리
    @GetMapping("/api")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getUserListApi(@ModelAttribute UserSearchRequest request) {
        // DTO 내용 확인용 출력
        System.out.println("=== UserSearchRequest ===");
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

    @GetMapping("/log")
    public String userLog(Model model){
        model.addAttribute("activeMenu","user");
        model.addAttribute("activeSubmenu","userLog");
        return "admin/user/userLog";
    }
}